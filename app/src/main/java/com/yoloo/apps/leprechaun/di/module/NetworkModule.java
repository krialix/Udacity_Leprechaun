package com.yoloo.apps.leprechaun.di.module;

import com.yoloo.apps.leprechaun.data.network.LeprechaunService;
import com.yoloo.apps.leprechaun.util.LeprechaunConverterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

  private static final String BASE_URL = "https://www.numbeo.com/";

  @Singleton
  @Provides
  public static OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder().build();
  }

  @Singleton
  @Provides
  public static LeprechaunService leprechaunService(OkHttpClient client) {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(LeprechaunConverterFactory.create())
        .build()
        .create(LeprechaunService.class);
  }
}
