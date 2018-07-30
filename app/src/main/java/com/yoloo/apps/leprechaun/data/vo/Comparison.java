package com.yoloo.apps.leprechaun.data.vo;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Comparison {
  abstract String name();

  abstract String firstCityPrice();

  abstract String secondCityPrice();

  abstract String diffPercent();

  public static Builder builder() {
    return new AutoValue_Comparison.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder name(String name);

    public abstract Builder firstCityPrice(String firstCityPrice);

    public abstract Builder secondCityPrice(String secondCityPrice);

    public abstract Builder diffPercent(String diffPercent);

    public abstract Comparison build();
  }
}
