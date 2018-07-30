package com.yoloo.apps.leprechaun.features.bookmarks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.repository.RssRepository;
import com.yoloo.apps.leprechaun.data.vo.Result;

import java.util.List;

import javax.inject.Inject;

class BookmarkViewModel extends ViewModel {

  private final RssRepository rssRepository;

  @Inject
  BookmarkViewModel(RssRepository rssRepository) {
    this.rssRepository = rssRepository;
  }

  LiveData<Result> getBookmarks() {
    return rssRepository.getRss();
  }
}
