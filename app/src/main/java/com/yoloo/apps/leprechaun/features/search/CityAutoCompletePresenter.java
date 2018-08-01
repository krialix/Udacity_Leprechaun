package com.yoloo.apps.leprechaun.features.search;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otaliastudios.autocomplete.RecyclerViewPresenter;
import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.vo.Result;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class CityAutoCompletePresenter extends RecyclerViewPresenter<String> {

  private static final String TAG = "CityAutoCompletePresent";

  private static final int AUTOCOMPLETE_DELAY_MS = 350;

  private final Handler autoCompleteHandler = new Handler();

  private final SearchViewModel viewModel;
  private final LifecycleOwner lifecycleOwner;

  private Adapter adapter;

  CityAutoCompletePresenter(
      Context context, SearchViewModel viewModel, LifecycleOwner lifecycleOwner) {
    super(context);
    this.viewModel = viewModel;
    this.lifecycleOwner = lifecycleOwner;
  }

  @Override
  protected RecyclerView.Adapter instantiateAdapter() {
    adapter = new Adapter();
    return adapter;
  }

  @Override
  protected void onQuery(@Nullable CharSequence query) {
    Log.i(TAG, "onQuery: " + query);

    if (TextUtils.isEmpty(query)) {
      adapter.setData(Collections.emptyList());
    } else {
      autoCompleteHandler.removeMessages(0);
      autoCompleteHandler.postDelayed(
          () ->
              viewModel
                  .searchCityName(query.toString().toLowerCase())
                  .observe(
                      lifecycleOwner,
                      result -> {
                        if (result instanceof Result.Success) {
                          Result.Success<List<String>> success =
                              (Result.Success<List<String>>) result;
                          Log.i(TAG, "searchCityName: " + success.data());
                          adapter.setData(success.data());
                          adapter.notifyDataSetChanged();
                        } else {
                          Result.Failure failure = (Result.Failure) result;
                          Log.e(TAG, "searchCityName: ", failure.error());
                        }
                      }),
          AUTOCOMPLETE_DELAY_MS);
    }
  }

  static final class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_item_city)
    TextView tvCityName;

    Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindTo(String cityName) {
      tvCityName.setText(cityName);
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
      tvCityName.setOnClickListener(
          v -> {
            v.setTag(tvCityName.getText());
            onClickListener.onClick(v);
          });
    }
  }

  final class Adapter extends RecyclerView.Adapter<Holder> {

    private List<String> data;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new Holder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
      if (isEmpty()) {
        holder.bindTo("No city name found!");
      } else {
        holder.bindTo(data.get(position));
        holder.setOnClickListener(v -> dispatchClick((String) v.getTag()));
      }
    }

    @Override
    public int getItemCount() {
      return isEmpty() ? 1 : data.size();
    }

    public void setData(List<String> data) {
      this.data = data;
    }

    private boolean isEmpty() {
      return data == null || data.isEmpty();
    }
  }
}
