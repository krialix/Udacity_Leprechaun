package com.yoloo.apps.leprechaun;

import android.app.Application;

import com.yoloo.apps.leprechaun.di.component.AppComponent;
import com.yoloo.apps.leprechaun.di.component.DaggerAppComponent;
import com.yoloo.apps.leprechaun.di.module.NetworkModule;

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
    return DaggerAppComponent.builder().networkModule(new NetworkModule()).build();
  }
}
