package com.yoloo.apps.leprechaun.util;

import com.yoloo.apps.leprechaun.data.db.model.Comparison;

public final class ComparisonUtil {
  private ComparisonUtil() {}

  public static String formatComparison(Comparison comparison) {
    return convertIdToTitle(comparison.getId());
  }

  public static String convertIdToTitle(String id) {
    String[] split = id.split("_");
    return split[1] + " - " + split[3];
  }
}
