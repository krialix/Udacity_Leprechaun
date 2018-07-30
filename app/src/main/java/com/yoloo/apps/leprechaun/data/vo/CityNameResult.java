package com.yoloo.apps.leprechaun.data.vo;

import java.util.Objects;

public class CityNameResult {
  private String label;
  private String value;

  private CityNameResult(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CityNameResult that = (CityNameResult) o;
    return Objects.equals(label, that.label) && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value);
  }

  @Override
  public String toString() {
    return "CityNameResult{" + "label='" + label + '\'' + ", value='" + value + '\'' + '}';
  }
}
