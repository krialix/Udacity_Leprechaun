package com.yoloo.apps.leprechaun;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.yoloo.apps.leprechaun.di.component.AppComponent;
import com.yoloo.apps.leprechaun.di.component.DaggerAppComponent;

public class LeprechaunApplication extends Application {
  private static final String ADMOB_TEST_ID = "ca-app-pub-3940256099942544~3347511713";

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = createAppComponent();
    MobileAds.initialize(this, ADMOB_TEST_ID);
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  private AppComponent createAppComponent() {
    return DaggerAppComponent.builder().application(this).build();
  }
}
