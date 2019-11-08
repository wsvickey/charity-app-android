package com.tech.agape4charity.adapter;

import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tech.agape4charity.R;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.utility.DateTimeHelper;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SmasH on 6/6/2018.
 */

public class TimelineViewAdapter extends RecyclerView.Adapter<TimelineViewAdapter.ViewHolder> {
    private static final String TAG = "TimelineViewAdapter";
    private ArrayList<Organization> mValues;
    private TimelineAdapterCallback mCallback;
    ShareLinkContent content;

    public TimelineViewAdapter(ArrayList<Organization> items, TimelineAdapterCallback callback){
        mValues = items;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimelineViewAdapter.ViewHolder holder, int position) {
        holder.fbShare.setVisibility(View.GONE);
        holder.position = holder.getAdapterPosition();
        holder.organization = mValues.get(position);
        holder.tvCharityName.setText(holder.organization.getOrgName());
        Log.i(TAG,"Date :" +holder.organization.getDateTime());
        holder.tvCharityDate.setText(DateTimeHelper.getDateFormatByType(holder.organization.getDateTime(), DateTimeHelper.DateTimeType._DD_MM_YYYY)+"");
        content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(holder.organization.getOrgWeb().toString()))
                .setQuote(holder.organization.getOrgName())
                .build();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(ArrayList<Organization> timelineArrayList) {
        mValues.clear();
        mValues.addAll(timelineArrayList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int position = 0;
        Organization organization;

        @BindView(R.id.charityName)
        TextView tvCharityName;
        @BindView(R.id.charityDate)
        TextView tvCharityDate;
        @BindView(R.id.fb_share_button)
        ShareButton fbShare;
        @BindView(R.id.btn_share)
        Button btnShare;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.onItemClick(position, organization);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.onBtnShare(position, organization);
                }
            });

            fbShare.setShareContent(content);

            fbShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.onFbShare(position, organization,fbShare);
                }
            });
        }
    }

    public interface TimelineAdapterCallback{
        void onItemClick(int position, Organization organization);
        void onFbShare(int position, Organization organization, ShareButton fbShare);
        void onBtnShare(int position, Organization organization);
    }
}
