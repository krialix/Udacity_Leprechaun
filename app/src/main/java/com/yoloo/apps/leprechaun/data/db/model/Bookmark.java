package com.yoloo.apps.leprechaun.data.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity(
    indices = @Index("comparisonId"),
    foreignKeys =
        @ForeignKey(
            entity = Comparison.class,
            parentColumns = "id",
            childColumns = "comparisonId",
            onDelete = ForeignKey.CASCADE))
public class Bookmark {

  @PrimaryKey(autoGenerate = true)
  private int id;

  private String comparisonId;

  @Ignore
  public Bookmark(String comparisonId) {
    this.comparisonId = comparisonId;
  }

  public Bookmark(int id, String comparisonId) {
    this.id = id;
    this.comparisonId = comparisonId;
  }

  public int getId() {
    return id;
  }

  public String getComparisonId() {
    return comparisonId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bookmark bookmark = (Bookmark) o;
    return id == bookmark.id && Objects.equals(comparisonId, bookmark.comparisonId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, comparisonId);
  }
}
