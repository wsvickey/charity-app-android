package com.tech.agape4charity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.agape4charity.AppConstant;
import com.tech.agape4charity.BaseFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.adapter.TimelineViewAdapter;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment implements TimelineViewAdapter.TimelineAdapterCallback {

    private OnProfileFragmentListener mListener;
    private int mColumnCount = 1;
    private TimelineViewAdapter adapter;
    private ArrayList<Organization> organizations = new ArrayList<>();
    private boolean isEmptyTimeline = false;
    ShareDialog shareDialog;

    @BindView(R.id.tvEmptyItem)
    TextView tvEmptyItem;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance()
    {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        View rView = view.findViewById(R.id.rvCharityList);
        TextView tvName = view.findViewById(R.id.profileName);
        TextView tvEmail = view.findViewById(R.id.profileEmail);
        TextView tvEmptyTimeline = view.findViewById(R.id.tvEmptyTimeline);
        init(this, view);
        tvName.setText(SessionManager.getInstance().getUser().getUsername());
        tvEmail.setText(SessionManager.getInstance().getUser().getEmail());

        if(rView instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) rView;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new TimelineViewAdapter(organizations, this);
            recyclerView.setAdapter(adapter);
        }
        if(isEmptyTimeline){
            tvEmptyTimeline.setVisibility(View.VISIBLE);
            rView.setVisibility(View.GONE);
        }else {
            tvEmptyTimeline.setVisibility(View.GONE);
            rView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        RequestEvent event = new RequestEvent(
                SessionManager.getInstance().getUser().getUserId(), AppConstant.PROFILE, RequestEvent.RequestType.MY_LIST);

        EventBus.getDefault().post(event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileFragmentListener) {
            mListener = (OnProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setOrganizations(ArrayList<Organization> list) {
        adapter.add(list);
        mListener.dismissLoading();
        if(list.isEmpty()){
            tvEmptyItem.setVisibility(View.VISIBLE);
            isEmptyTimeline = true;
        }else
            tvEmptyItem.setVisibility(View.GONE);{
            isEmptyTimeline = false;
        }
    }


    @Override
    public void onItemClick(int position, Organization organization) {

    }

    @Override
    public void onFbShare(int position, Organization organization, ShareButton fbShare) {

    }


    @Override
    public void onBtnShare(int position, Organization organization) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, organization.getOrgWeb());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @OnClick(R.id.btn_more)
    public void onBtnMore(View view){
        mListener.onMoreClick();
    }

    public interface OnProfileFragmentListener {
        void dismissLoading();
        void onMoreClick();
    }
}
