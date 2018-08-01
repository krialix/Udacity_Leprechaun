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
  private final LeprechaunService leprechaunService;

  @Inject
  public SearchRepository(LeprechaunService leprechaunService) {
    this.leprechaunService = leprechaunService;
  }

  public LiveData<Result> searchCityName(String name) {
    SearchAsyncTask asyncTask = new SearchAsyncTask(leprechaunService);
    MutableLiveData<Result> liveData = new MutableLiveData<>();
    asyncTask.setLiveData(liveData);
    asyncTask.execute(name);
    return liveData;
  }

  public LiveData<Result> compareCities(String city1, String city2) {
    return new MutableLiveData<>();
  }

  @Singleton
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
