package com.yoloo.apps.leprechaun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yoloo.apps.leprechaun.features.bookmarks.BookmarkFragment;
import com.yoloo.apps.leprechaun.features.rss.RssFragment;
import com.yoloo.apps.leprechaun.features.search.SearchFragment;
import com.yoloo.apps.leprechaun.features.widget.BookmarksWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.navigation)
  BottomNavigationView navigationView;

  private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
      item -> {
        switch (item.getItemId()) {
          case R.id.navigation_search:
            return loadFragment(SearchFragment.newInstance());
          case R.id.navigation_bookmark:
            return loadFragment(BookmarkFragment.newInstance());
          case R.id.navigation_rss:
            return loadFragment(RssFragment.newInstance());
        }
        return false;
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    loadFragment(SearchFragment.newInstance());

    navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    int source = intent.getIntExtra(BookmarksWidgetProvider.KEY_SOURCE, 0);
    if (source == BookmarksWidgetProvider.SOURCE_WIDGET) {
      loadFragment(BookmarkFragment.newInstance());
      navigationView.setSelectedItemId(R.id.navigation_bookmark);
    }
  }

  private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_container, fragment)
          .commit();
      return true;
    }
    return false;
  }
}
