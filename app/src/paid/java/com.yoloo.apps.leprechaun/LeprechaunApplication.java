package com.yoloo.apps.leprechaun;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.yoloo.apps.leprechaun.di.component.AppComponent;
import com.yoloo.apps.leprechaun.di.component.DaggerAppComponent;

public class LeprechaunApplication extends Application {
  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = createAppComponent();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  private AppComponent createAppComponent() {
    return DaggerAppComponent.builder().application(this).build();
  }
}
