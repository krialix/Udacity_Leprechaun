package com.yoloo.apps.leprechaun.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.yoloo.apps.leprechaun.data.network.LeprechaunService;
import com.yoloo.apps.leprechaun.data.vo.CityNameResult;
import com.yoloo.apps.leprechaun.data.vo.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchRepository {
  private final SearchAsyncTask asyncTask;

  @Inject
  public SearchRepository(SearchAsyncTask asyncTask) {
    this.asyncTask = asyncTask;
  }

  public LiveData<Result> searchFirstCityName(String name) {
    MutableLiveData<Result> liveData = new MutableLiveData<>();
    asyncTask.setLiveData(liveData);
    asyncTask.execute(name);
    return liveData;
  }

  public LiveData<Result> searchSecondCityName(String name) {
    MutableLiveData<Result> liveData = new MutableLiveData<>();
    asyncTask.setLiveData(liveData);
    asyncTask.execute(name);
    return liveData;
  }

  public LiveData<Result> compareCities(String city1, String city2) {
    return new MutableLiveData<>();
  }

  static class SearchAsyncTask extends AsyncTask<String, Void, Result> {
    private final LeprechaunService leprechaunService;
    private MutableLiveData<Result> liveData;

    @Inject
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
}
