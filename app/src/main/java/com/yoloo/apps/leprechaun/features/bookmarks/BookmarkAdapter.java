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

import butterknife.BindView;
import butterknife.ButterKnife;

class BookmarkAdapter extends ListAdapter<String, BookmarkAdapter.BookmarkViewHolder> {

  private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(String oldItem, String newItem) {
          return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(String oldItem, String newItem) {
          return oldItem.equals(newItem);
        }
      };

  BookmarkAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
    return new BookmarkViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
    holder.bindTo(getItem(position));
  }

  static final class BookmarkViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_bookmark_content)
    TextView tvContent;

    BookmarkViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindTo(String content) {
      tvContent.setText(content);
    }
  }
}
