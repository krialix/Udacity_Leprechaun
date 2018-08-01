package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;
import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

  @Inject ViewModelProvider.Factory viewModelFactory;

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
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Autocomplete.<String>on(etFirstCity)
        .with(new ColorDrawable(Color.WHITE))
        .with(6f)
        .with(new Autocomplete.SimplePolicy())
        .with(new CityAutoCompletePresenter(getContext(), viewModel, this))
        .with(
            new AutocompleteCallback<String>() {
              @Override
              public boolean onPopupItemClicked(Editable editable, String item) {
                editable.clear();
                editable.append(item);
                return true;
              }

              @Override
              public void onPopupVisibilityChanged(boolean shown) {}
            })
        .build();

    Autocomplete.<String>on(etSecondCity)
        .with(new ColorDrawable(Color.WHITE))
        .with(6f)
        .with(new Autocomplete.SimplePolicy())
        .with(new CityAutoCompletePresenter(getContext(), viewModel, this))
        .with(
            new AutocompleteCallback<String>() {
              @Override
              public boolean onPopupItemClicked(Editable editable, String item) {
                editable.clear();
                editable.append(item);
                return true;
              }

              @Override
              public void onPopupVisibilityChanged(boolean shown) {}
            })
        .build();
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

  @OnClick(R.id.btn_search_compare)
  void searchCity() {
    String firstCity = tilFirstCity.getEditText().getText().toString();
    String secondCity = tilSecondCity.getEditText().getText().toString();

    tilFirstCity.setError(TextUtils.isEmpty(firstCity) ? "You must enter a city name" : null);
    tilSecondCity.setError(TextUtils.isEmpty(secondCity) ? "You must enter a city name" : null);
  }
}
