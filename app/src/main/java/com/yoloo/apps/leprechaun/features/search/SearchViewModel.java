package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.repository.SearchRepository;
import com.yoloo.apps.leprechaun.data.vo.Result;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

  private final SearchRepository repository;

  @Inject
  SearchViewModel(SearchRepository repository) {
    this.repository = repository;
  }

  LiveData<Result> searchCityName(String name) {
    return repository.searchCityName(name);
  }
}
