package com.tech.agape4charity.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.agape4charity.R;
import com.tech.agape4charity.object.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Charitha Ratnayake on 3/15/2018.
 */

public class CatRecyclerViewAdapter extends RecyclerView.Adapter<CatRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Category> mValues;
    private CatAdapterCallback mCallback;

    public CatRecyclerViewAdapter(ArrayList<Category> items, CatAdapterCallback callback) {
        mValues = items;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.position = holder.getAdapterPosition();
        holder.category = mValues.get(position);

        holder.tvTitle.setText(holder.category.getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(ArrayList<Category> categories) {
        mValues.clear();
        mValues.addAll(categories);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int position = 0;
        Category category;

        @BindView(R.id.tvCategory) TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.onItemClick(position, category);
                }
            });
        }
    }

    public interface CatAdapterCallback{
        void onItemClick(int position, Category organization);
    }
}