package com.yoloo.apps.leprechaun.di.component;

import com.yoloo.apps.leprechaun.di.module.DatabaseModule;
import com.yoloo.apps.leprechaun.di.module.NetworkModule;
import com.yoloo.apps.leprechaun.di.module.ViewModelModule;
import com.yoloo.apps.leprechaun.features.bookmarks.BookmarkFragment;
import com.yoloo.apps.leprechaun.features.rss.RssFragment;
import com.yoloo.apps.leprechaun.features.search.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DatabaseModule.class, NetworkModule.class, ViewModelModule.class})
public interface AppComponent {

  void inject(SearchFragment fragment);

  void inject(BookmarkFragment fragment);

  void inject(RssFragment fragment);
}
