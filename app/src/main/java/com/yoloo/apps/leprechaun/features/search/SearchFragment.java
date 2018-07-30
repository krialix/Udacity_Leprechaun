package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoloo.apps.leprechaun.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class SearchFragment extends Fragment {

  private static final String TAG = "SearchFragment";

  @BindView(R.id.til_search_first_city)
  TextInputLayout tilFirstCity;

  @BindView(R.id.til_search_second_city)
  TextInputLayout tilSecondCity;

  private SearchViewModel viewModel;

  private Unbinder unbinder;

  public SearchFragment() {
    // Required empty public constructor
  }

  public static SearchFragment newInstance() {
    return new SearchFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnTextChanged(
      value = R.id.et_search_first_city,
      callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
  void afterFirstCityNameInput(Editable editable) {
    viewModel
        .searchFirstCityName(editable.toString())
        .observe(this, results -> Log.i(TAG, "afterFirstCityNameInput: " + results.size()));
  }

  @OnTextChanged(
      value = R.id.et_search_second_city,
      callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
  void afterSecondCityNameInput(Editable editable) {
    viewModel
        .searchSecondCityName(editable.toString())
        .observe(this, results -> Log.i(TAG, "afterSecondCityNameInput: " + results.size()));
  }

  @OnClick(R.id.btn_search_compare)
  void searchCity() {
    String firstCity = tilFirstCity.getEditText().getText().toString();
    String secondCity = tilSecondCity.getEditText().getText().toString();

    tilFirstCity.setError(TextUtils.isEmpty(firstCity) ? "You must enter a city name" : null);
    tilSecondCity.setError(TextUtils.isEmpty(secondCity) ? "You must enter a city name" : null);
  }
}
