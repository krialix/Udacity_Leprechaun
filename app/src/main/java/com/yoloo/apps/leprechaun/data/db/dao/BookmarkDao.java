package com.yoloo.apps.leprechaun.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yoloo.apps.leprechaun.data.db.model.Bookmark;
import com.yoloo.apps.leprechaun.data.db.model.Comparison;

import java.util.List;

@Dao
public interface BookmarkDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Bookmark bookmark);

  @Query("DELETE FROM Bookmark WHERE comparisonId = :comparisonId")
  void delete(String comparisonId);

  @Query(
      "SELECT Comparison.* FROM Comparison INNER JOIN Bookmark ON Comparison.id = Bookmark.comparisonId")
  List<Comparison> getBookmarks();
}
