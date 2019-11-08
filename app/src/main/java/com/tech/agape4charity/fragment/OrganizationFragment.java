package com.tech.agape4charity.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.agape4charity.AppConstant;
import com.tech.agape4charity.BaseFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.activity.InfoActivity;
import com.tech.agape4charity.activity.NoteActivity;
import com.tech.agape4charity.adapter.OrgRecyclerViewAdapter;
import com.tech.agape4charity.dialog.DatePicker;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.utility.Helper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrganizationFragment extends BaseFragment implements OrgRecyclerViewAdapter.OrgAdapterCallback, DatePicker.DatePickerCallback {
    private static final String TAG = "OrganizationFragment";
    private OnMyListFragmentListener mListener;
    private int mColumnCount = 1;
    private OrgRecyclerViewAdapter adapter;
    private ArrayList<Organization> organizations = new ArrayList<>();
    private String userEmail;
    private String organitzationEmail;
    private String subject;
    public Organization org;
    public int mPosition;

    public OrganizationFragment() {
        // Required empty public constructor
    }

    public static OrganizationFragment newInstance() {
        OrganizationFragment fragment = new OrganizationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        init(this, view);

        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new OrgRecyclerViewAdapter(organizations, this);
            adapter.setMyCharity(true);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showLoading();
        RequestEvent event = new RequestEvent(
                SessionManager.getInstance().getUser().getUserId(), AppConstant.MYLIST, RequestEvent.RequestType.MY_LIST);

        EventBus.getDefault().post(event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyListFragmentListener) {
            mListener = (OnMyListFragmentListener) context;
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
    }

    @Override
    public void onRemove(int position, Organization organization) {
        mListener.showLoading();
        RequestEvent event = new RequestEvent(organization.getEventId(), RequestEvent.RequestType.ADD_TO_MY_LIST);
        EventBus.getDefault().post(event);
        organizations.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNote(int position, Organization organization) {
        NoteActivity.startActivity(getActivity(), organization);
    }

    @Override
    public void moreInfo(int position, Organization organization) {
        organization.setScreen("myList");
        InfoActivity.startActivity(getActivity(), organization,"myList");

    }

    @Override
    public void addCharity(int position, Organization organization) {
    }

    @Override
    public void signUp(int position, Organization organization) {
        userEmail = SessionManager.getInstance().getUser().getEmail();
        organitzationEmail = organization.getOrgEmail();
        subject = organization.getOrgName();
        Helper.sendEmail(getActivity(),userEmail,organitzationEmail,subject);
    }

    @Override
    public void reminder(int position, Organization organization) {
        Log.i(TAG,"REMINDER_POSITION :" + position);
        DatePicker datePicker = new DatePicker(this);
        datePicker.show(getFragmentManager(),"date picker");
        org = organization;
        mPosition = position;
    }

    @Override
    public void onDataSet(int i, int i1, int i2) {
        Calendar calendar = new GregorianCalendar(i, i1, i2);
        calendar.getTimeInMillis();
        Log.i(TAG, "Time in millis: " + calendar.getTimeInMillis());
        mListener.updateDate(calendar.getTimeInMillis(),org);
        if(calendar.getTimeInMillis()>System.currentTimeMillis()){
            org.setDateTime(calendar.getTimeInMillis()/1000);
            org.setEventStatus("PENDING");
            adapter.updateItem(mPosition,org);
        }else {
            adapter.removeItem(mPosition);
        }
    }

    public interface OnMyListFragmentListener {
        void updateDate(long date, Organization organization);
        void showLoading();
        void dismissLoading();
    }
}
