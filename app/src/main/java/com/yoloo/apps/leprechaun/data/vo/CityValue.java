package com.yoloo.apps.leprechaun.data.vo;

import java.util.Objects;

public class CityValue {

  private String cityName;

  private String defaultCurrency;

  private String alternativeCurrency;

  private CityValue() {}

  private CityValue(Builder builder) {
    cityName = builder.cityName;
    defaultCurrency = builder.defaultCurrency;
    alternativeCurrency = builder.alternativeCurrency;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getCityName() {
    return cityName;
  }

  public String getDefaultCurrency() {
    return defaultCurrency;
  }

  public String getAlternativeCurrency() {
    return alternativeCurrency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CityValue cityValue = (CityValue) o;
    return Objects.equals(cityName, cityValue.cityName)
        && Objects.equals(defaultCurrency, cityValue.defaultCurrency)
        && Objects.equals(alternativeCurrency, cityValue.alternativeCurrency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cityName, defaultCurrency, alternativeCurrency);
  }

  @Override
  public String toString() {
    return "CityValue{"
        + "cityName='"
        + cityName
        + '\''
        + ", defaultCurrency='"
        + defaultCurrency
        + '\''
        + ", alternativeCurrency='"
        + alternativeCurrency
        + '\''
        + '}';
  }

  public static final class Builder {
    private String cityName;
    private String defaultCurrency;
    private String alternativeCurrency;

    private Builder() {}

    public Builder cityName(String val) {
      cityName = val;
      return this;
    }

    public Builder defaultCurrency(String val) {
      defaultCurrency = val;
      return this;
    }

    public Builder alternativeCurrency(String val) {
      alternativeCurrency = val;
      return this;
    }

    public CityValue build() {
      return new CityValue(this);
    }
  }
}
