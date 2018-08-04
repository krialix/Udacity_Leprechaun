package com.yoloo.apps.leprechaun.data.vo;

import java.util.Objects;

public class Row {
  private boolean highlighted;
  private CityValue city1;
  private CityValue city2;
  private String difference;
  private String name;

  Row() {}

  private Row(Builder builder) {
    highlighted = builder.highlighted;
    city1 = builder.city1;
    city2 = builder.city2;
    difference = builder.difference;
    name = builder.name;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Row row = (Row) o;
    return highlighted == row.highlighted
        && Objects.equals(city1, row.city1)
        && Objects.equals(city2, row.city2)
        && Objects.equals(difference, row.difference)
        && Objects.equals(name, row.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(highlighted, city1, city2, difference, name);
  }

  @Override
  public String toString() {
    return "Row{"
        + "highlighted="
        + highlighted
        + ", city1="
        + city1
        + ", city2="
        + city2
        + ", difference='"
        + difference
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }

  public boolean isHighlighted() {
    return highlighted;
  }

  public CityValue getCity1() {
    return city1;
  }

  public CityValue getCity2() {
    return city2;
  }

  public String getDifference() {
    return difference;
  }

  public String getName() {
    return name;
  }

  public static final class Builder {
    private boolean highlighted;
    private CityValue city1;
    private CityValue city2;
    private String difference;
    private String name;

    private Builder() {}

    public Builder highlighted(boolean val) {
      highlighted = val;
      return this;
    }

    public Builder city1(CityValue val) {
      city1 = val;
      return this;
    }

    public Builder city2(CityValue val) {
      city2 = val;
      return this;
    }

    public Builder difference(String val) {
      difference = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Row build() {
      return new Row(this);
    }
  }
}
