package com.tech.agape4charity.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tech.agape4charity.R;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.utility.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Senel Perera on 3/15/2018.
 */

public class OrgRecyclerViewAdapter extends RecyclerView.Adapter<OrgRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "OrgRecyclerViewAdapter";
    private ArrayList<Organization> mValues;
    private List<Organization> mFilteredValues;
    private OrgAdapterCallback mCallback;

    private Boolean isMyCharity = false;
    private Boolean isCalendar = false;

    public OrgRecyclerViewAdapter(ArrayList<Organization> items, OrgAdapterCallback callback) {
        mValues = items;
        mFilteredValues = items;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organization, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.position = holder.getAdapterPosition();
        holder.organization = mValues.get(position);

        holder.tvTitle.setText(holder.organization.getOrgName());
        holder.tvCategory.setText(holder.organization.getCategoryName());
        if(holder.organization.getEventLocation()==null){
            holder.ivLocation.setVisibility(View.GONE);
            holder.tvLocation.setVisibility(View.GONE);
        }else {
            holder.tvLocation.setText(holder.organization.getEventLocation());
        }
        holder.btnStatus.setText(holder.organization.getEventStatus());
        if(holder.organization.getDateTime()!=0) {
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.ivCalendarIcon.setVisibility(View.VISIBLE);
            holder.tvDate.setText(DateTimeHelper.getDateFormatByType(holder.organization.getDateTime(),DateTimeHelper.DateTimeType._DD_MM_YYYY));
            Log.i(TAG, "timestamp : " + holder.organization.getDateTime());
        }
        else {
            holder.tvDate.setVisibility(View.GONE);
            holder.ivCalendarIcon.setVisibility(View.GONE);
        }
//        holder.tvDate.setText(DateTimeHelper.getDateFormatByType(holder.organization.getEventDate()*1000, DateTimeHelper.DateTimeType._DD_MM_YYYY)+"");

        holder.layoutCharity.setVisibility(isMyCharity?View.VISIBLE:View.GONE);
        holder.layoutOrg.setVisibility(isMyCharity?View.GONE:View.VISIBLE);
        holder.btnInfo.setVisibility(isMyCharity?View.GONE:View.VISIBLE);
        holder.btnReminder.setVisibility(!isMyCharity?View.GONE:View.VISIBLE);
        if(isCalendar){holder.btnReminder.setVisibility(View.GONE);}
        if(holder.organization.getEventStatus()!=null && holder.organization.getEventStatus().equals("PENDING")){
            holder.btnStatus.setEnabled(false);
            holder.btnInfo.setVisibility(View.GONE);
        }else{
            holder.btnStatus.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredValues.size();
    }

    public void add(ArrayList<Organization> organizations) {
        mValues.clear();
        if(organizations.size()>0){
            mValues.addAll(organizations);
        }
        else {
            mValues.clear();
        }
        notifyDataSetChanged();
    }

    public void clearAll(){
        mValues.clear();
        notifyDataSetChanged();
    }

    public void updateItem(int position, Organization organization){
        mValues.set(position, organization);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        try {
            mValues.remove(position);
            notifyDataSetChanged();
        }catch (Exception e){

        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        int position = 0;
        Organization organization;

        @BindView(R.id.mainItem) LinearLayout mainItem;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvCategory) TextView tvCategory;
        @BindView(R.id.tvLocation) TextView tvLocation;
        @BindView(R.id.tvDate) TextView tvDate;

        @BindView(R.id.btnMore) Button btnMore;
        @BindView(R.id.btnAddNote) Button btnAddNote;
        @BindView(R.id.btnAddToMyCharity) Button btnAddToMyCharity;
        @BindView(R.id.btnDonate) Button btnDonate;
        @BindView(R.id.btnStatus) Button btnStatus;

        @BindView(R.id.iBtnInfo) ImageView btnInfo;
        @BindView(R.id.iBtnReminder) ImageView btnReminder;
        @BindView(R.id.ivLocation) ImageView ivLocation;
        @BindView(R.id.ivCalendarIcon) ImageView ivCalendarIcon;
        @BindView(R.id.layoutMyList) LinearLayout layoutCharity;
        @BindView(R.id.layoutOrg) LinearLayout layoutOrg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            btnAddNote.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mCallback != null)mCallback.addNote(position, organization);
                }
            });

            btnMore.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mCallback != null)mCallback.moreInfo(position, organization);
                }
            });

            btnAddToMyCharity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.addCharity(position, organization);
                }
            });

            btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.signUp(position, organization);
                }
            });

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.moreInfo(position, organization);
                }
            });

            btnReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallback != null)mCallback.reminder(getAdapterPosition(), organization);
                }
            });

            btnDonate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null)mCallback.signUp(position, organization);
                }
            });
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredValues = mValues;
                } else {
                    ArrayList<Organization> filteredList = new ArrayList<>();
                    for (Organization row : filteredList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOrgName().toLowerCase().contains(charString.toLowerCase()) || row.getOrgName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mFilteredValues = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterResults;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredValues = (ArrayList<Organization>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void setMyCharity(Boolean myCharity) {
        isMyCharity = myCharity;
    }

    public void setCalendar(Boolean calendar) {
        isCalendar = calendar;
    }

    public interface OrgAdapterCallback{
        void onRemove(int position, Organization organization);
        void addNote(int position, Organization organization);
        void moreInfo(int position, Organization organization);
        void addCharity(int position, Organization organization);
        void signUp(int position, Organization organization);
        void reminder(int position, Organization organization);
    }
}