package com.yoloo.apps.leprechaun.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.yoloo.apps.leprechaun.data.network.LeprechaunService;
import com.yoloo.apps.leprechaun.data.vo.CityNameResult;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class SearchRepository {

  private final LeprechaunService leprechaunService;
  private final SearchAsyncTask asyncTask;
  private final MutableLiveData<List<CityNameResult>> cityNameResultsLiveData;

  @Inject
  public SearchRepository(LeprechaunService leprechaunService) {
    this.leprechaunService = leprechaunService;
    this.cityNameResultsLiveData = new MutableLiveData<>();
    this.asyncTask = new SearchAsyncTask(this.leprechaunService, this.cityNameResultsLiveData);
  }

  public LiveData<List<CityNameResult>> searchFirstCityName(String name) {
    asyncTask.execute(name);
    return cityNameResultsLiveData;
  }

  public LiveData<List<CityNameResult>> searchSecondCityName(String name) {
    asyncTask.execute(name);
    return cityNameResultsLiveData;
  }

  public LiveData<Boolean> compareCities(String city1, String city2) {
    return null;
  }

  static class SearchAsyncTask extends AsyncTask<String, Void, List<CityNameResult>> {

    private final LeprechaunService leprechaunService;
    private final MutableLiveData<List<CityNameResult>> listLiveData;

    private SearchAsyncTask(
        LeprechaunService leprechaunService, MutableLiveData<List<CityNameResult>> listLiveData) {
      this.leprechaunService = leprechaunService;
      this.listLiveData = listLiveData;
    }

    @Override
    protected List<CityNameResult> doInBackground(String... strings) {
      try {
        Response<List<CityNameResult>> response =
            leprechaunService.searchCityName(strings[0]).execute();
        return response.body();
      } catch (IOException e) {
        e.printStackTrace();
        return Collections.emptyList();
      }
    }

    @Override
    protected void onPostExecute(List<CityNameResult> cityNameResults) {
      listLiveData.setValue(cityNameResults);
    }
  }
}
