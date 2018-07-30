package com.yoloo.apps.leprechaun.data.vo;

import com.google.auto.value.AutoValue;

public interface Result {

  @AutoValue
  abstract class Success<T> implements Result {
    public abstract T data();

    public static <T> Success<T> create(T data) {
      return new AutoValue_Result_Success<>(data);
    }
  }

  @AutoValue
  abstract class Failure implements Result {
    public abstract Throwable error();

    public static Failure create(Throwable error) {
      return new AutoValue_Result_Failure(error);
    }
  }
}
