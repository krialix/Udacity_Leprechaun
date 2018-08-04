package com.yoloo.apps.leprechaun.data.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.yoloo.apps.leprechaun.data.vo.Row;

import java.util.List;
import java.util.Objects;

@Entity
public class Comparison {

  @NonNull @PrimaryKey private String id;

  private String info;

  private List<Row> rows;

  public Comparison(@NonNull String id, String info, List<Row> rows) {
    this.id = id;
    this.info = info;
    this.rows = rows;
  }

  private Comparison(Builder builder) {
    id = builder.id;
    info = builder.info;
    rows = builder.rows;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @NonNull
  public String getId() {
    return id;
  }

  public String getInfo() {
    return info;
  }

  public List<Row> getRows() {
    return rows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comparison that = (Comparison) o;
    return Objects.equals(id, that.id)
        && Objects.equals(info, that.info)
        && Objects.equals(rows, that.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, info, rows);
  }

  public static final class Builder {
    private String id;
    private String info;
    private List<Row> rows;

    private Builder() {}

    public Builder id(String val) {
      id = val;
      return this;
    }

    public Builder info(String val) {
      info = val;
      return this;
    }

    public Builder rows(List<Row> val) {
      rows = val;
      return this;
    }

    public Comparison build() {
      return new Comparison(this);
    }
  }
}
