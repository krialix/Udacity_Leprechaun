package com.yoloo.apps.leprechaun.util;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.yoloo.apps.leprechaun.data.vo.Row;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

public final class TypeConverters {

  private static final Moshi MOSHI = new Moshi.Builder().build();

  private TypeConverters() {}

  @TypeConverter
  public static List<Row> jsonToList(String data) {
    if (TextUtils.isEmpty(data)) {
      return Collections.emptyList();
    }

    ParameterizedType type = Types.newParameterizedType(List.class, Row.class);
    JsonAdapter<List<Row>> adapter = MOSHI.adapter(type);
    try {
      return adapter.fromJson(data);
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }

  @TypeConverter
  public static String listToJson(List<Row> rows) {
    ParameterizedType type = Types.newParameterizedType(List.class, Row.class);
    JsonAdapter<List<Row>> adapter = MOSHI.adapter(type);
    return adapter.toJson(rows);
  }
}
