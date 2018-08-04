package com.yoloo.apps.leprechaun.features.comparison;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.vo.CityValue;
import com.yoloo.apps.leprechaun.data.vo.Row;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

class ComparisonAdapter extends ListAdapter<Row, ComparisonAdapter.RowViewHolder> {

  private static final String TAG = "ComparisonAdapter";

  private static final DiffUtil.ItemCallback<Row> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Row>() {
        @Override
        public boolean areItemsTheSame(Row oldItem, Row newItem) {
          return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(Row oldItem, Row newItem) {
          return oldItem.equals(newItem);
        }
      };

  private final Set<Integer> expandedPositions = new HashSet<>();

  @Inject
  ComparisonAdapter() {
    super(DIFF_CALLBACK);
    setHasStableIds(false);
  }

  @NonNull
  @Override
  public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    return new RowViewHolder(view, expandedPositions, this);
  }

  @Override
  public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
    holder.bindTo(getItem(position));
  }

  @Override
  public int getItemViewType(int position) {
    Row row = getItem(position);

    if (row.isHighlighted()) {
      return R.layout.item_comparison_title;
    }

    return R.layout.item_comparison_normal;
  }

  @Override
  public long getItemId(int position) {
    Row row = getItem(position);
    // Log.i(TAG, "getItemId(): pos:" + position + " name: " + row.getName());
    return row.getName().hashCode();
  }

  static final class RowViewHolder extends RecyclerView.ViewHolder {
    private static final OvershootInterpolator INTERPOLATOR = new OvershootInterpolator();
    private static final int ROW_COLOR = Color.parseColor("#eee6ff");

    private final Set<Integer> expandedPositions;
    private final ComparisonAdapter adapter;

    @BindView(R.id.tv_comparison_title)
    TextView tvTitle;

    @Nullable
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @Nullable
    @BindView(R.id.tv_comparison_city1)
    TextView tvCity1;

    @Nullable
    @BindView(R.id.tv_comparison_city2)
    TextView tvCity2;

    @Nullable
    @BindView(R.id.tv_comparison_difference)
    TextView tvDifference;

    RowViewHolder(View itemView, Set<Integer> expandedPositions, ComparisonAdapter adapter) {
      super(itemView);
      this.expandedPositions = expandedPositions;
      this.adapter = adapter;
      ButterKnife.bind(this, itemView);

      if (expandableLayout != null) {
        expandableLayout.setInterpolator(INTERPOLATOR);
      }
    }

    void bindTo(Row row) {
      if (!row.isHighlighted()) {
        if (getAdapterPosition() % 2 == 0) {
          tvTitle.setBackgroundColor(ROW_COLOR);
        } else {
          tvTitle.setBackgroundColor(Color.WHITE);
        }
      }

      tvTitle.setText(row.getName());

      if (expandableLayout != null) {
        boolean expanded = expandedPositions.contains(getAdapterPosition());
        expandableLayout.setExpanded(expanded);

        tvTitle.setOnClickListener(
            v -> {
              if (expanded) {
                expandableLayout.collapse();
                expandedPositions.remove(getAdapterPosition());
              } else {
                expandableLayout.expand();
                expandedPositions.add(getAdapterPosition());
              }

              adapter.notifyItemChanged(getAdapterPosition());
            });

        CityValue city1 = row.getCity1();
        tvCity1.setText(city1.getCityName() + "\n" + city1.getDefaultCurrency());

        CityValue city2 = row.getCity2();
        tvCity2.setText(city2.getCityName() + "\n" + city2.getDefaultCurrency());
        tvDifference.setText(row.getDifference());
      }
    }
  }
}
