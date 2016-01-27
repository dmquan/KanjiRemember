package com.supermario.kanjilookandlearn.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.supermario.kanjilookandlearn.R;
import com.supermario.kanjilookandlearn.common.Utils;
import com.supermario.kanjilookandlearn.data.Kanji;
import com.supermario.kanjilookandlearn.database.KanjiProvider;

import java.util.ArrayList;


/**
 * Created by desmond on 31/5/15.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROG = 2;
    private static final int MAX_NUMBER_OF_TYPE = 3;
    private Context context;
    public ArrayList<Kanji> mDataset;
    private RecyclerView.OnScrollListener onScrollListener;
    private IOnItemClickListener onItemClick;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMoreListener;

    public ReviewListAdapter(Context context, ArrayList<Kanji> myDataset, RecyclerView recyclerView) {
        super();
        this.context = context;
        mDataset = myDataset;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                .getLayoutManager();
        this.recyclerView = recyclerView;
        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something
                    if (onLoadMoreListener != null) {
                        loading = true;
                        onLoadMoreListener.onLoadMore();
                    }

                }
            }
        };
        this.recyclerView
                .addOnScrollListener(onScrollListener);
    }

    public void setLoaded() {
        loading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnItemClick(IOnItemClickListener listener) {
        this.onItemClick = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view

        Context context = viewGroup.getContext();
        View view;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_kanji, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        } else {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_progress, viewGroup, false);

            return new ProgressViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {

            ((ViewHolder) holder).kanjiTextView.setText(mDataset.get(position).kanji);
            ((ViewHolder) holder).nameTextView.setText(mDataset.get(position).name);

            // a SpannableStringBuilder containing text to display
            SpannableStringBuilder sb = new SpannableStringBuilder(mDataset.get(position).remember);

            // create a bold StyleSpan to be used on the SpannableStringBuilder
            StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold

            int index = mDataset.get(position).remember.indexOf(mDataset.get(position).meanVietnamese);
            if (index >= 0) {
                // set only the name part of the SpannableStringBuilder to be bold --> 16, 16 + name.length()
                sb.setSpan(b, index, index + mDataset.get(position).meanVietnamese.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            ((ViewHolder) holder).kunyomiTextView.setText(mDataset.get(position).kunyomi);
            ((ViewHolder) holder).onyomiTextView.setText(mDataset.get(position).onyomi);


            ((ViewHolder) holder).rememberTextView.setText(sb);
            ((ViewHolder) holder).pictureImageView.setImageDrawable(Utils.getDrawableFromAssets(context, mDataset.get(position).image));

            if (mDataset.get(position).favorite == 1) {
                ((ViewHolder) holder).favoriteImageView.setImageResource(R.drawable.icon_favorite_active);
            } else {
                ((ViewHolder) holder).favoriteImageView.setImageResource(R.drawable.icon_favorite_inactive);
            }

            ((ViewHolder) holder).favoriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDataset.get(position).favorite == 0) {
                        mDataset.get(position).favorite = 1;
                        ((ViewHolder) holder).favoriteImageView.setImageResource(R.drawable.icon_favorite_active);
                    } else {
                        mDataset.get(position).favorite = 0;
                        ((ViewHolder) holder).favoriteImageView.setImageResource(R.drawable.icon_favorite_inactive);
                    }
                    KanjiProvider.updateFavorite(context, mDataset.get(position));
                }
            });

            ((ViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemClick(position);
                    }
                }
            });


        } else if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public interface IOnItemClickListener {
        public void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mDataset.get(position) != null) {
            return TYPE_ITEM;
        } else {
            return TYPE_PROG;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView kanjiTextView;
        public TextView kunyomiTextView;
        public TextView onyomiTextView;
        public TextView nameTextView;
        public TextView rememberTextView;
        public ImageView favoriteImageView;
        public ImageView pictureImageView;


        public LinearLayout view;


        public ViewHolder(View v) {
            super(v);
            view = (LinearLayout) v;

            kanjiTextView = (TextView) v.findViewById(R.id.kanjiTextView);
            nameTextView = (TextView) v.findViewById(R.id.nameTextView);
            rememberTextView = (TextView) v.findViewById(R.id.rememberTextView);
            kunyomiTextView = (TextView) v.findViewById(R.id.kunyomiTextView);
            onyomiTextView = (TextView) v.findViewById(R.id.onyomiTextView);
            pictureImageView = (ImageView) v.findViewById(R.id.pictureImageView);
            favoriteImageView = (ImageView) v.findViewById(R.id.favoriteImageView);
        }


    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public void clear() {
        recyclerView.removeOnScrollListener(onScrollListener);
        recyclerView = null;
    }

    public void onDestroy() {
        mDataset.clear();
        onItemClick = null;
        context = null;
    }


}

