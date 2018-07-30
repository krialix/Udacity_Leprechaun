package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.repository.SearchRepository;
import com.yoloo.apps.leprechaun.data.vo.CityNameResult;

import java.util.List;

import javax.inject.Inject;

class SearchViewModel extends ViewModel {

  private final SearchRepository repository;

  @Inject
  SearchViewModel(SearchRepository repository) {
    this.repository = repository;
  }

  LiveData<List<CityNameResult>> searchFirstCityName(String name) {
    return repository.searchFirstCityName(name);
  }

  LiveData<List<CityNameResult>> searchSecondCityName(String name) {
    return repository.searchSecondCityName(name);
  }
}
