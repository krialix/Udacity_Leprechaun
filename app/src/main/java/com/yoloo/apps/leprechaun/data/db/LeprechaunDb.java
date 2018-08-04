package com.yoloo.apps.leprechaun.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.yoloo.apps.leprechaun.data.db.dao.BookmarkDao;
import com.yoloo.apps.leprechaun.data.db.dao.ComparisonDao;
import com.yoloo.apps.leprechaun.data.db.model.Bookmark;
import com.yoloo.apps.leprechaun.data.db.model.Comparison;

@TypeConverters({com.yoloo.apps.leprechaun.util.TypeConverters.class})
@Database(
    entities = {Comparison.class, Bookmark.class},
    version = 1,
    exportSchema = false)
public abstract class LeprechaunDb extends RoomDatabase {

  public abstract ComparisonDao comparisonDao();

  public abstract BookmarkDao bookmarkDao();
}
