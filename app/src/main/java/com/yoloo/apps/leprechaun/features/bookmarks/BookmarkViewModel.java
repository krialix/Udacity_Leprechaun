package com.yoloo.apps.leprechaun.features.bookmarks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.db.model.Comparison;
import com.yoloo.apps.leprechaun.data.repository.SearchRepository;

import java.util.List;

import javax.inject.Inject;

public class BookmarkViewModel extends ViewModel {

  private final SearchRepository searchRepository;

  @Inject
  BookmarkViewModel(SearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  LiveData<List<Comparison>> getBookmarks() {
    return searchRepository.listBookmarks();
  }

  void unBookmark(String id) {
    searchRepository.unBookmark(id);
  }
}
