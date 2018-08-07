package com.yoloo.apps.leprechaun.features.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.MainActivity;
import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.db.model.Comparison;
import com.yoloo.apps.leprechaun.data.repository.SearchRepository;
import com.yoloo.apps.leprechaun.util.ComparisonUtil;

import java.util.List;

import javax.inject.Inject;

public class BookmarksWidgetProvider extends AppWidgetProvider {
  public static final String KEY_SOURCE = "KEY_SOURCE";
  public static final int SOURCE_WIDGET = 100;

  private static final String TAG = "BookmarksWidgetProvider";

  @Inject SearchRepository searchRepository;

  static void updateAppWidget(
      Context context,
      AppWidgetManager appWidgetManager,
      int appWidgetId,
      List<Comparison> comparisons) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(KEY_SOURCE, SOURCE_WIDGET);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bookmarks_widget);

    views.setOnClickPendingIntent(R.id.ll_widget_container, pendingIntent);
    views.removeAllViews(R.id.ll_widget_container);

    for (Comparison comparison : comparisons) {
      RemoteViews lineView =
          new RemoteViews(context.getPackageName(), R.layout.item_bookmark_widget);

      String line = ComparisonUtil.formatComparison(comparison);
      Log.i(TAG, "updateAppWidget: " + line);
      lineView.setTextViewText(R.id.tv_widget_bookmark_content, line);
      views.addView(R.id.ll_widget_container, lineView);
    }

    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    ((LeprechaunApplication) context.getApplicationContext()).getAppComponent().inject(this);
    searchRepository
        .listBookmarks()
        .observeForever(
            comparisons -> {
              if (comparisons != null) {
                for (int appWidgetId : appWidgetIds) {
                  updateAppWidget(context, appWidgetManager, appWidgetId, comparisons);
                }
              }
            });
  }
}
