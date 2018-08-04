package com.yoloo.apps.leprechaun.di.module;

import android.arch.persistence.room.Room;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.data.db.LeprechaunDb;
import com.yoloo.apps.leprechaun.data.db.dao.BookmarkDao;
import com.yoloo.apps.leprechaun.data.db.dao.ComparisonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

  @Singleton
  @Provides
  public static LeprechaunDb leprechaunDb(LeprechaunApplication application) {
    return Room.databaseBuilder(application, LeprechaunDb.class, "leprechaun.db")
        .fallbackToDestructiveMigration()
        .build();
  }

  @Singleton
  @Provides
  public static ComparisonDao comparisonDao(LeprechaunDb db) {
    return db.comparisonDao();
  }

  @Singleton
  @Provides
  public static BookmarkDao bookmarkDao(LeprechaunDb db) {
    return db.bookmarkDao();
  }
}
