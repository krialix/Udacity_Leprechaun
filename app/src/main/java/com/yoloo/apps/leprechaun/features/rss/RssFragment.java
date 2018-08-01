package com.yoloo.apps.leprechaun.features.rss;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.vo.Result;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RssFragment extends Fragment {

  private static final String TAG = "RssFragment";

  @BindView(R.id.rv_rss)
  RecyclerView rvRss;

  @Inject ViewModelProvider.Factory viewModelFactory;

  private RssAdapter rssAdapter;

  private Unbinder unbinder;

  public RssFragment() {
    // Required empty public constructor
  }

  public static RssFragment newInstance() {
    return new RssFragment();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_rss, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    ((LeprechaunApplication) getActivity().getApplication()).getAppComponent().inject(this);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupRecyclerView();

    RssViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(RssViewModel.class);

    viewModel
        .getRss()
        .observe(
            this,
            result -> {
              if (result instanceof Result.Success) {
                Result.Success<List<String>> success = (Result.Success<List<String>>) result;
                rssAdapter.submitList(success.data());
              } else {
                Result.Failure failure = (Result.Failure) result;
                Log.e(TAG, "onViewCreated: ", failure.error());
              }
            });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void setupRecyclerView() {
    rssAdapter = new RssAdapter();

    rvRss.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    rvRss.setItemAnimator(new DefaultItemAnimator());
    rvRss.setAdapter(rssAdapter);
  }
}
