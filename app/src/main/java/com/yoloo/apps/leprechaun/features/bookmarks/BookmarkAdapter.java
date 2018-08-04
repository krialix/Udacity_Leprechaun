package com.yoloo.apps.leprechaun.features.bookmarks;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoloo.apps.leprechaun.R;
import com.yoloo.apps.leprechaun.data.db.model.Comparison;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BookmarkAdapter extends ListAdapter<Comparison, BookmarkAdapter.BookmarkViewHolder> {

  private static final DiffUtil.ItemCallback<Comparison> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Comparison>() {
        @Override
        public boolean areItemsTheSame(Comparison oldItem, Comparison newItem) {
          return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(Comparison oldItem, Comparison newItem) {
          return oldItem.equals(newItem);
        }
      };

  private OnBookmarkRemoveListener onBookmarkRemoveListener;
  private OnBookmarkClickListener onBookmarkClickListener;

  BookmarkAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
    return new BookmarkViewHolder(view, onBookmarkRemoveListener, onBookmarkClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
    holder.bindTo(getItem(position));
  }

  public void setOnBookmarkRemoveListener(OnBookmarkRemoveListener onBookmarkRemoveListener) {
    this.onBookmarkRemoveListener = onBookmarkRemoveListener;
  }

  public void setOnBookmarkClickListener(OnBookmarkClickListener onBookmarkClickListener) {
    this.onBookmarkClickListener = onBookmarkClickListener;
  }

  interface OnBookmarkRemoveListener {
    void onRemove(String id);
  }

  interface OnBookmarkClickListener {
    void onClick(Comparison comparison);
  }

  static final class BookmarkViewHolder extends RecyclerView.ViewHolder {

    private final OnBookmarkRemoveListener onBookmarkRemoveListener;
    private final OnBookmarkClickListener onBookmarkClickListener;

    @BindView(R.id.tv_bookmark_content)
    TextView tvContent;

    private Comparison comparison;

    BookmarkViewHolder(
        View itemView,
        OnBookmarkRemoveListener onBookmarkRemoveListener,
        OnBookmarkClickListener onBookmarkClickListener) {
      super(itemView);
      this.onBookmarkRemoveListener = onBookmarkRemoveListener;
      this.onBookmarkClickListener = onBookmarkClickListener;
      ButterKnife.bind(this, itemView);
    }

    void bindTo(Comparison comparison) {
      this.comparison = comparison;
      String id = comparison.getId();
      tvContent.setText(convertIdToTitle(id));
    }

    @OnClick(R.id.ib_bookmark_remove)
    void remove() {
      onBookmarkRemoveListener.onRemove(comparison.getId());
    }

    @OnClick(R.id.vg_bookmark)
    void onClick() {
      onBookmarkClickListener.onClick(comparison);
    }

    private String convertIdToTitle(String id) {
      String[] split = id.split("_");
      return split[1] + " - " + split[3];
    }
  }
}
