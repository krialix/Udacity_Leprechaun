package com.yoloo.apps.leprechaun.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.yoloo.apps.leprechaun.data.db.dao.BookmarkDao;
import com.yoloo.apps.leprechaun.data.db.dao.ComparisonDao;
import com.yoloo.apps.leprechaun.data.db.model.Bookmark;
import com.yoloo.apps.leprechaun.data.db.model.Comparison;
import com.yoloo.apps.leprechaun.data.network.LeprechaunService;
import com.yoloo.apps.leprechaun.data.vo.CityNameResult;
import com.yoloo.apps.leprechaun.data.vo.CityValue;
import com.yoloo.apps.leprechaun.data.vo.Result;
import com.yoloo.apps.leprechaun.data.vo.Row;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchRepository {
  private static final String TAG = "SearchRepository";

  private final LeprechaunService leprechaunService;
  private final ComparisonDao comparisonDao;
  private final BookmarkDao bookmarkDao;

  @Inject
  public SearchRepository(
      LeprechaunService leprechaunService, ComparisonDao comparisonDao, BookmarkDao bookmarkDao) {
    this.leprechaunService = leprechaunService;
    this.comparisonDao = comparisonDao;
    this.bookmarkDao = bookmarkDao;
  }

  public LiveData<Result> searchCityName(String name) {
    SearchAsyncTask asyncTask = new SearchAsyncTask(leprechaunService);
    MutableLiveData<Result> liveData = new MutableLiveData<>();
    asyncTask.setLiveData(liveData);
    asyncTask.execute(name);
    return liveData;
  }

  public LiveData<Comparison> compareCities(
      String country1, String country2, String city1, String city2) {
    CompareAsyncTask asyncTask = new CompareAsyncTask(leprechaunService, comparisonDao);
    asyncTask.execute(country1, country2, city1, city2);
    String id = country1 + "_" + city1 + "_" + country2 + "_" + city2;
    return comparisonDao.get(id);
  }

  public LiveData<List<Comparison>> listBookmarks() {
    ListBookmarkedAsyncTask asyncTask = new ListBookmarkedAsyncTask(bookmarkDao);
    MutableLiveData<List<Comparison>> liveData = new MutableLiveData<>();
    asyncTask.setLiveData(liveData);
    asyncTask.execute();
    return liveData;
  }

  public void deleteComparison(String id) {
    DeleteBookmarkAsyncTask deleteBookmarkAsyncTask = new DeleteBookmarkAsyncTask(comparisonDao);
    deleteBookmarkAsyncTask.execute(id);
  }

  public void bookmark(String id) {
    new BookmarkBookmarkAsyncTask(bookmarkDao).execute(id);
  }

  public void unBookmark(String id) {
    new UnBookmarkBookmarkAsyncTask(bookmarkDao).execute(id);
  }

  // tasks

  static class SearchAsyncTask extends AsyncTask<String, Void, Result> {
    private final LeprechaunService leprechaunService;
    private MutableLiveData<Result> liveData;

    SearchAsyncTask(LeprechaunService leprechaunService) {
      this.leprechaunService = leprechaunService;
    }

    void setLiveData(MutableLiveData<Result> liveData) {
      this.liveData = liveData;
    }

    @Override
    protected Result doInBackground(String... strings) {
      try {
        List<CityNameResult> results =
            leprechaunService.searchCityName(strings[0]).execute().body();

        if (results == null) {
          return Result.Success.create(Collections.<String>emptyList());
        }

        List<String> cities = new ArrayList<>(results.size());
        for (CityNameResult result : results) {
          cities.add(result.getLabel());
        }

        return Result.Success.create(cities);
      } catch (IOException e) {
        return Result.Failure.create(e);
      }
    }

    @Override
    protected void onPostExecute(Result result) {
      super.onPostExecute(result);
      liveData.setValue(result);
    }
  }

  static class CompareAsyncTask extends AsyncTask<String, Void, Void> {
    private final LeprechaunService leprechaunService;
    private final ComparisonDao comparisonDao;

    CompareAsyncTask(LeprechaunService leprechaunService, ComparisonDao comparisonDao) {
      this.leprechaunService = leprechaunService;
      this.comparisonDao = comparisonDao;
    }

    @Override
    protected Void doInBackground(String... strings) {
      try {
        String country1 = strings[0];
        String country2 = strings[1];
        String city1 = strings[2];
        String city2 = strings[3];

        Document doc = leprechaunService.compare(country1, country2, city1, city2).execute().body();

        List<Row> rows = new ArrayList<>();

        Elements tables = doc.select("div.innerWidth > table");
        Element firstTable = tables.first();

        String info = extractInfoFromTable(firstTable);

        Element secondTable = tables.last();
        Elements trs = secondTable.select("tbody > tr");

        // all trs
        int size = trs.size();
        for (int i = 0; i < size; i++) {
          // skip first <tr>
          if (i == 0) {
            continue;
          }

          Element tr = trs.get(i);
          Row row = convertToRow(tr.children(), city1, city2);
          rows.add(row);
        }

        // remove unrelated rows
        rows = rows.subList(0, rows.size() - 4);

        String id = country1 + "_" + city1 + "_" + country2 + "_" + city2;

        Comparison comparison = Comparison.newBuilder().id(id).info(info).rows(rows).build();

        comparisonDao.insert(comparison);

        return null;
      } catch (IOException e) {
        return null;
      }
    }

    private String extractInfoFromTable(Element firstTable) {
      StringBuilder sb = new StringBuilder();
      Elements trs = firstTable.select("tbody > tr");

      int size = trs.size();
      for (int i = 0; i < size; i++) {
        // skip first <tr>
        if (i == 0) {
          continue;
        }

        Element tr = trs.get(i);
        sb.append(tr.text()).append("\n\n");
      }
      return sb.toString();
    }

    private Row convertToRow(Elements trChildren, String city1, String city2) {
      Element el1 = trChildren.get(0);
      Element el2 = trChildren.get(1);
      Element el3 = trChildren.get(2);
      Element el4 = trChildren.get(3);

      if (el1.nodeName().equals("th")) {
        return Row.newBuilder().name(el1.text()).highlighted(true).build();
      }

      CityValue cityValue1 =
          CityValue.newBuilder().cityName(city1).defaultCurrency(el2.text()).build();

      CityValue cityValue2 =
          CityValue.newBuilder().cityName(city2).defaultCurrency(el3.text()).build();

      return Row.newBuilder()
          .name(el1.text())
          .city1(cityValue1)
          .city2(cityValue2)
          .difference(el4.text())
          .highlighted(false)
          .build();
    }
  }

  static class ListBookmarkedAsyncTask extends AsyncTask<Void, Void, Result> {
    private final BookmarkDao bookmarkDao;

    private MutableLiveData<List<Comparison>> liveData;

    ListBookmarkedAsyncTask(BookmarkDao bookmarkDao) {
      this.bookmarkDao = bookmarkDao;
    }

    void setLiveData(MutableLiveData<List<Comparison>> liveData) {
      this.liveData = liveData;
    }

    @Override
    protected Result doInBackground(Void... params) {
      List<Comparison> comparisons = bookmarkDao.getBookmarks();
      if (comparisons == null) {
        comparisons = Collections.emptyList();
      }
      liveData.postValue(comparisons);
      return null;
    }
  }

  static class DeleteBookmarkAsyncTask extends AsyncTask<String, Void, Void> {
    private final ComparisonDao comparisonDao;

    DeleteBookmarkAsyncTask(ComparisonDao comparisonDao) {
      this.comparisonDao = comparisonDao;
    }

    @Override
    protected Void doInBackground(String... ids) {
      comparisonDao.delete(ids[0]);
      return null;
    }
  }

  static class BookmarkBookmarkAsyncTask extends AsyncTask<String, Void, Void> {
    private final BookmarkDao bookmarkDao;

    BookmarkBookmarkAsyncTask(BookmarkDao bookmarkDao) {
      this.bookmarkDao = bookmarkDao;
    }

    @Override
    protected Void doInBackground(String... ids) {
      Bookmark bookmark = new Bookmark(ids[0]);
      bookmarkDao.insert(bookmark);
      return null;
    }
  }

  static class UnBookmarkBookmarkAsyncTask extends AsyncTask<String, Void, Void> {
    private final BookmarkDao bookmarkDao;

    UnBookmarkBookmarkAsyncTask(BookmarkDao bookmarkDao) {
      this.bookmarkDao = bookmarkDao;
    }

    @Override
    protected Void doInBackground(String... ids) {
      bookmarkDao.delete(ids[0]);
      return null;
    }
  }
}
