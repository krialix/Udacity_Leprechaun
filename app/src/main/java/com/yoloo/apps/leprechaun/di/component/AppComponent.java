package com.yoloo.apps.leprechaun.di.component;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.di.module.DatabaseModule;
import com.yoloo.apps.leprechaun.di.module.NetworkModule;
import com.yoloo.apps.leprechaun.di.module.ViewModelModule;
import com.yoloo.apps.leprechaun.features.bookmarks.BookmarkFragment;
import com.yoloo.apps.leprechaun.features.comparison.ComparisonActivity;
import com.yoloo.apps.leprechaun.features.rss.RssFragment;
import com.yoloo.apps.leprechaun.features.search.SearchFragment;
import com.yoloo.apps.leprechaun.features.widget.BookmarksWidgetProvider;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {DatabaseModule.class, NetworkModule.class, ViewModelModule.class})
public interface AppComponent {

  void inject(SearchFragment fragment);

  void inject(BookmarkFragment fragment);

  void inject(RssFragment fragment);

  void inject(ComparisonActivity activity);

  void inject(BookmarksWidgetProvider bookmarksWidgetProvider);

  @Component.Builder
  public interface Builder {
    @BindsInstance
    Builder application(LeprechaunApplication application);

    AppComponent build();
  }
}
