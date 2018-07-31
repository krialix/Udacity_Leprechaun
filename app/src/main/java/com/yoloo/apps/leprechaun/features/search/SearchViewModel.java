package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.repository.SearchRepository;
import com.yoloo.apps.leprechaun.data.vo.Result;

import javax.inject.Inject;

class SearchViewModel extends ViewModel {

  private final SearchRepository repository;

  @Inject
  SearchViewModel(SearchRepository repository) {
    this.repository = repository;
  }

  LiveData<Result> searchFirstCityName(String name) {
    return repository.searchFirstCityName(name);
  }

  LiveData<Result> searchSecondCityName(String name) {
    return repository.searchSecondCityName(name);
  }
}
