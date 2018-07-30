package com.yoloo.apps.leprechaun.data.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.yoloo.apps.leprechaun.data.vo.Comparison;

@Entity
public class SearchResult {

  @PrimaryKey private String id;

  private String name;

  private Comparison comparison;
}
