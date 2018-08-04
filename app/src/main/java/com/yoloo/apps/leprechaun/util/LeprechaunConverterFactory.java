package com.yoloo.apps.leprechaun.util;

import android.support.annotation.Nullable;

import com.github.slashrootv200.retrofithtmlconverter.HtmlConverterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class LeprechaunConverterFactory extends Converter.Factory {
  private static final String BASE_URL = "https://www.numbeo.com/";

  private final Converter.Factory json = MoshiConverterFactory.create();
  private final Converter.Factory jsoup = HtmlConverterFactory.create(BASE_URL);

  private LeprechaunConverterFactory() {}

  public static LeprechaunConverterFactory create() {
    return new LeprechaunConverterFactory();
  }

  @Nullable
  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType() == Json.class) {
        return json.responseBodyConverter(type, annotations, retrofit);
      }
      if (annotation.annotationType() == Jsoup.class) {
        return jsoup.responseBodyConverter(type, annotations, retrofit);
      }
    }

    return super.responseBodyConverter(type, annotations, retrofit);
  }
}
