package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.otaliastudios.autocomplete.RecyclerViewPresenter;
import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.vo.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class SearchFragment extends Fragment {

  private static final String TAG = "SearchFragment";

  @BindView(R.id.til_search_first_city)
  TextInputLayout tilFirstCity;

  @BindView(R.id.et_search_first_city)
  EditText etFirstCity;

  @BindView(R.id.til_search_second_city)
  TextInputLayout tilSecondCity;

  @BindView(R.id.et_search_second_city)
  EditText etSecondCity;

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
  public void onAttach(Context context) {
    super.onAttach(context);
    ((LeprechaunApplication) getActivity().getApplication()).getAppComponent().inject(this);
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
        .observe(
            this,
            result -> {
              if (result instanceof Result.Success) {
                Result.Success<List<String>> success = (Result.Success<List<String>>) result;
                Log.i(TAG, "afterFirstCityNameInput: " + success.data());
                /*Autocomplete.on(etFirstCity)
                .with(new Autocomplete.SimplePolicy())
                .with()*/
              } else {
                Result.Failure failure = (Result.Failure) result;
                Log.e(TAG, "afterFirstCityNameInput: ", failure.error());
              }
            });
  }

  @OnTextChanged(
      value = R.id.et_search_second_city,
      callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
  void afterSecondCityNameInput(Editable editable) {
    viewModel
        .searchSecondCityName(editable.toString())
        .observe(
            this,
            result -> {
              if (result instanceof Result.Success) {
                Result.Success<List<String>> success = (Result.Success<List<String>>) result;
                Log.i(TAG, "afterSecondCityNameInput: " + success.data());
              } else {
                Result.Failure failure = (Result.Failure) result;
                Log.e(TAG, "afterSecondCityNameInput: ", failure.error());
              }
            });
  }

  @OnClick(R.id.btn_search_compare)
  void searchCity() {
    String firstCity = tilFirstCity.getEditText().getText().toString();
    String secondCity = tilSecondCity.getEditText().getText().toString();

    tilFirstCity.setError(TextUtils.isEmpty(firstCity) ? "You must enter a city name" : null);
    tilSecondCity.setError(TextUtils.isEmpty(secondCity) ? "You must enter a city name" : null);
  }

  private static class NamePresenter extends RecyclerViewPresenter<String> {

    public NamePresenter(Context context) {
      super(context);
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
      return null;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {}
  }
}
