package com.yoloo.apps.leprechaun.features.comparison;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.db.model.Comparison;
import com.yoloo.apps.leprechaun.data.repository.SearchRepository;

import javax.inject.Inject;

public class ComparisonViewModel extends ViewModel {
  private final SearchRepository searchRepository;

  @Inject
  ComparisonViewModel(SearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  LiveData<Comparison> compareCities(String country1, String country2, String city1, String city2) {
    return searchRepository.compareCities(country1, country2, city1, city2);
  }

  void bookmark(String id) {
    searchRepository.bookmark(id);
  }

  void unBookmark(String id) {
    searchRepository.unBookmark(id);
  }
}
