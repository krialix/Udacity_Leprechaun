package com.yoloo.apps.leprechaun.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yoloo.apps.leprechaun.data.db.model.Comparison;

import java.util.List;

@Dao
public interface ComparisonDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Comparison comparison);

  @Insert
  void insertAll(List<Comparison> comparisons);

  @Query("DELETE FROM Comparison WHERE id = :id")
  void delete(String id);

  @Query("DELETE FROM Comparison")
  void deleteAll();

  @Query("SELECT * FROM Comparison WHERE id = :id LIMIT 1")
  LiveData<Comparison> get(String id);

  @Query("SELECT * FROM Comparison")
  List<Comparison> getAllComparisons();
}
