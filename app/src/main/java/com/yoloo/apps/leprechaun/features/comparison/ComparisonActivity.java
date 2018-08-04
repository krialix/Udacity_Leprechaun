package com.yoloo.apps.leprechaun.features.comparison;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComparisonActivity extends AppCompatActivity {

  private static final String TAG = "ComparisonActivity";

  private static final String KEY_COUNTRY_1 = "COUNTRY_1";
  private static final String KEY_COUNTRY_2 = "COUNTRY_2";
  private static final String KEY_CITY_1 = "CITY_1";
  private static final String KEY_CITY_2 = "CITY_2";

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.rv_comparison)
  RecyclerView rvComparison;

  @BindView(R.id.pb_comparison)
  ProgressBar progressBar;

  @BindView(R.id.tv_comparison_info)
  TextView tvInfo;

  @BindView(R.id.tv_comparison_cities)
  TextView tvCities;

  @BindView(R.id.expandable_layout)
  ExpandableLayout expandableLayout;

  @BindView(R.id.tv_comparison_toggle_info)
  TextView tvInfoToggle;

  @BindView(R.id.ib_bookmark)
  ImageButton ibBookmark;

  @Inject ViewModelProvider.Factory viewModelFactory;

  @Inject ComparisonAdapter adapter;

  public static void start(
      Context context, String country1, String country2, String city1, String city2) {
    Intent starter = new Intent(context, ComparisonActivity.class);
    starter.putExtra(KEY_COUNTRY_1, country1);
    starter.putExtra(KEY_COUNTRY_2, country2);
    starter.putExtra(KEY_CITY_1, city1);
    starter.putExtra(KEY_CITY_2, city2);
    context.startActivity(starter);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comparison);
    ButterKnife.bind(this);
    ((LeprechaunApplication) getApplication()).getAppComponent().inject(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    setContentVisible(false);

    setupRecyclerView();

    Intent intent = getIntent();
    String country1 = getOrDefault(intent.getStringExtra(KEY_COUNTRY_1), "Turkey");
    String country2 = getOrDefault(intent.getStringExtra(KEY_COUNTRY_2), "Turkey");
    String city1 = getOrDefault(intent.getStringExtra(KEY_CITY_1), "Izmir");
    String city2 = getOrDefault(intent.getStringExtra(KEY_CITY_2), "Ankara");

    tvCities.setText(city1 + " vs " + city2);

    tvInfoToggle.setOnClickListener(v -> expandableLayout.toggle(true));

    ComparisonViewModel viewModel =
        ViewModelProviders.of(this, viewModelFactory).get(ComparisonViewModel.class);

    viewModel
        .compareCities(country1, country2, city1, city2)
        .observe(
            this,
            comparison -> {
              if (comparison != null) {
                Log.i(TAG, "result: ");
                adapter.submitList(comparison.getRows());
                tvInfo.setText(comparison.getInfo());
                setContentVisible(true);
              }
            });

    ibBookmark.setOnClickListener(
        v -> {
          String id = country1 + "_" + city1 + "_" + country2 + "_" + city2;
          if (ibBookmark.isSelected()) {
            ibBookmark.setSelected(false);
            viewModel.unBookmark(id);
          } else {
            ibBookmark.setSelected(true);
            viewModel.bookmark(id);
          }
        });
  }

  private void setContentVisible(boolean visible) {
    rvComparison.setVisibility(visible ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(visible ? View.GONE : View.VISIBLE);
  }

  private void setupRecyclerView() {
    rvComparison.setLayoutManager(new LinearLayoutManager(this));
    rvComparison.setItemAnimator(new DefaultItemAnimator());
    rvComparison.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    rvComparison.setAdapter(adapter);
  }

  private String getOrDefault(String actual, String defaultValue) {
    return TextUtils.isEmpty(actual) ? defaultValue : actual;
  }
}
