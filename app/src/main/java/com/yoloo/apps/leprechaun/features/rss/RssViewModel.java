package com.yoloo.apps.leprechaun.features.rss;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.yoloo.apps.leprechaun.data.repository.RssRepository;
import com.yoloo.apps.leprechaun.data.vo.Result;

import javax.inject.Inject;

public class RssViewModel extends ViewModel {
  private final RssRepository rssRepository;

  @Inject
  RssViewModel(RssRepository rssRepository) {
    this.rssRepository = rssRepository;
  }

  LiveData<Result> getRss() {
    return rssRepository.getRss();
  }
}
