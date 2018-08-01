package com.yoloo.apps.leprechaun.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.yoloo.apps.leprechaun.di.LeprechaunViewModelFactory;
import com.yoloo.apps.leprechaun.di.ViewModelKey;
import com.yoloo.apps.leprechaun.features.bookmarks.BookmarkViewModel;
import com.yoloo.apps.leprechaun.features.rss.RssViewModel;
import com.yoloo.apps.leprechaun.features.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel.class)
  abstract ViewModel bindSearchViewModel(SearchViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(BookmarkViewModel.class)
  abstract ViewModel bindBookmarkViewModel(BookmarkViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(RssViewModel.class)
  abstract ViewModel bindRssViewModel(RssViewModel viewModel);

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(LeprechaunViewModelFactory factory);
}
