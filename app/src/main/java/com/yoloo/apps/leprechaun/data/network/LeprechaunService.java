package com.yoloo.apps.leprechaun.data.network;

import com.yoloo.apps.leprechaun.data.vo.CityNameResult;
import com.yoloo.apps.leprechaun.util.Json;
import com.yoloo.apps.leprechaun.util.Jsoup;

import org.jsoup.nodes.Document;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeprechaunService {

  @Json
  @GET("common/CitySearchJson")
  Call<List<CityNameResult>> searchCityName(@Query("term") String term);

  @Jsoup
  @GET("cost-of-living/compare_cities.jsp")
  Call<Document> compare(
      @Query("country1") String country1,
      @Query("country2") String country2,
      @Query("city1") String city1,
      @Query("city2") String city2);

  @Jsoup
  @GET("cost-of-living")
  Call<Document> getRssFeed();
}
