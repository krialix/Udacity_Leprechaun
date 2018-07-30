package com.yoloo.apps.leprechaun.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.yoloo.apps.leprechaun.data.network.LeprechaunService;
import com.yoloo.apps.leprechaun.data.vo.Result;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RssRepository {
  private final RssAsyncTask asyncTask;
  private final LiveData<Result> liveData;

  @Inject
  public RssRepository(RssAsyncTask asyncTask, LiveData<Result> liveData) {
    this.asyncTask = asyncTask;
    this.liveData = liveData;
  }

  public LiveData<Result> getRss() {
    asyncTask.execute();
    return liveData;
  }

  static final class RssAsyncTask extends AsyncTask<Void, Void, Result> {
    private final LeprechaunService service;
    private final MutableLiveData<Result> liveData;

    @Inject
    RssAsyncTask(LeprechaunService service, MutableLiveData<Result> liveData) {
      this.service = service;
      this.liveData = liveData;
    }

    @Override
    protected Result doInBackground(Void... voids) {
      try {
        Document body = service.getRssFeed().execute().body();
        if (body == null) {
          return Result.Failure.create(new NullPointerException("body can not be null"));
        }
        Element ulElement = body.getElementsByClass("nice_items").first();
        Elements liElements = ulElement.select("li");
        List<String> contents = new ArrayList<>(liElements.size());

        for (Element li : liElements) {
          contents.add(li.text());
        }

        return Result.Success.create(contents);
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
