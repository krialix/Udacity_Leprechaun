package com.yoloo.apps.leprechaun.features.bookmarks;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.yoloo.apps.leprechaun.LeprechaunApplication;
import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.features.comparison.ComparisonActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookmarkFragment extends Fragment {

  private static final String TAG = "BookmarkFragment";

  @BindView(R.id.rv_bookmark)
  RecyclerView rvBookmark;

  @BindView(R.id.pb_loading)
  ProgressBar progressBar;

  @Inject ViewModelProvider.Factory viewModelFactory;

  private BookmarkViewModel viewModel;

  private BookmarkAdapter bookmarkAdapter;

  private Unbinder unbinder;

  public BookmarkFragment() {
    // Required empty public constructor
  }

  public static BookmarkFragment newInstance() {
    return new BookmarkFragment();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
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

    setContentVisible(false);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookmarkViewModel.class);

    viewModel
        .getBookmarks()
        .observe(
            this,
            list -> {
              bookmarkAdapter.submitList(list);
              setContentVisible(true);
            });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void setupRecyclerView() {
    bookmarkAdapter = new BookmarkAdapter();

    rvBookmark.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    rvBookmark.setItemAnimator(new DefaultItemAnimator());
    rvBookmark.setAdapter(bookmarkAdapter);

    bookmarkAdapter.setOnBookmarkRemoveListener(
        id -> {
          viewModel.unBookmark(id);

          viewModel
              .getBookmarks()
              .observe(
                  this,
                  list -> {
                    bookmarkAdapter.submitList(list);
                    setContentVisible(true);
                  });
        });

    bookmarkAdapter.setOnBookmarkClickListener(
        comparison -> {
          String[] split = comparison.getId().split("_");
          ComparisonActivity.start(getContext(), split[0], split[2], split[1], split[3]);
        });
  }

  private void setContentVisible(boolean visible) {
    rvBookmark.setVisibility(visible ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(visible ? View.GONE : View.VISIBLE);
  }
}
