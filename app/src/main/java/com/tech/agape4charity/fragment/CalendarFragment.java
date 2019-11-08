package com.tech.agape4charity.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tech.agape4charity.AppConstant;
import com.tech.agape4charity.BaseFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.SessionManager;
import com.tech.agape4charity.activity.InfoActivity;
import com.tech.agape4charity.activity.NoteActivity;
import com.tech.agape4charity.adapter.OrgRecyclerViewAdapter;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.widget.TextViewNormal;
import com.tech.agape4charity.widget.TextViewTitle;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends BaseFragment implements OrgRecyclerViewAdapter.OrgAdapterCallback {
    private static final String TAG = "CalendarFragment";
//    private List<EventDay> events = new ArrayList<>();
    private  ArrayList<Organization> orgList;
    private OnCalendarFragementListener mListener;
    private ArrayList<Organization> organizations = new ArrayList<>();
    private OrgRecyclerViewAdapter orgAdapter;
    //    private CalendarView calendarView;
    CompactCalendarView calendarView;
    TextViewTitle tvTitle;
    ImageView ivInfo;
    TextViewNormal tvCategory;
    TextViewNormal tvLocation;
    TextViewNormal tvDate;
    TextView tvMonth;
    Button btnAddNote;
    Button btnStatus;
    LinearLayout containerReminder;
    RecyclerView rvCalendarList;
    Organization organization;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    public CalendarFragment() {
        // Required empty public constructor
    }


    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
        RequestEvent event = new RequestEvent(
                SessionManager.getInstance().getUser().getUserId(), AppConstant.CALENDAR, RequestEvent.RequestType.MY_LIST);

        EventBus.getDefault().post(event);

    }

    @Override
    public void onResume() {
        super.onResume();
//        RequestEvent event = new RequestEvent(
//                SessionManager.getInstance().getUser().getUserId(), AppConstant.CALENDAR, RequestEvent.RequestType.MY_LIST);
//
//        EventBus.getDefault().post(event);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setOrganizations(ArrayList<Organization> list) {
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, list.get(i).getCategoryName());
            Event ev = new Event(getResources().getColor(R.color.colorPrimary),list.get(i).getDateTime() * 1000,list.get(i).getDateTime());
            calendarView.addEvent(ev);
        }
        mListener.dismissLoading();
    }

    public void setItem(ArrayList<Organization> list) {
        for (int i = 0; i < list.size(); i++) {
            organization = list.get(i);
            Log.i(TAG,"orgtostring :" + organization.toString());
//            tvTitle.setText(list.get(i).getOrgName());
//            tvCategory.setText(list.get(i).getCategoryName());
//            tvLocation.setText(list.get(i).getEventLocation());
//            Log.i(TAG,"timestamp : " + list.get(i).getDateTime());
////            tvDate.setText(Helper.getDate(list.get(i).getDateTime()));
//            tvDate.setText(DateTimeHelper.getDateFormatByType(list.get(i).getDateTime(),DateTimeHelper.DateTimeType._DD_MM_YYYY));
//            btnStatus.setText(list.get(i).getEventStatus());
            orgAdapter.add(list);
        }
        mListener.dismissLoading();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        rvCalendarList = view.findViewById(R.id.rvCalendarList);
        calendarView = view.findViewById(R.id.compactcalendar_view);
        tvMonth = view.findViewById(R.id.tvMonth);
        calendarView.setUseThreeLetterAbbreviation(true);
        rvCalendarList.setLayoutManager(new LinearLayoutManager(getContext()));
        orgAdapter = new OrgRecyclerViewAdapter(organizations,this);
        orgAdapter.setMyCharity(true);
        orgAdapter.setCalendar(true);
        rvCalendarList.setAdapter(orgAdapter);

//        Event ev1 = new Event(Color.BLACK,1529126222000L, "Charity sample");
//        calendarView.addEvent(ev1);
        tvMonth.setText(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getContext();
                Log.i(TAG, dateClicked.toString());
                List<Event> events = calendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                if(events.size()!=0){
                    RequestEvent event = new RequestEvent(SessionManager.getInstance().getUser().getUserId(),Long.valueOf(events.get(0).getData().toString()), RequestEvent.RequestType.GET_REMINDER);
                    EventBus.getDefault().post(event);
                } else {
                    orgAdapter.clearAll();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                Log.i(TAG, firstDayOfNewMonth.toString());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalendarFragementListener) {
            mListener = (OnCalendarFragementListener) context;
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

    @Override
    public void onRemove(int position, Organization organization) {
        mListener.showLoading();
        RequestEvent event = new RequestEvent(organization.getEventId(), RequestEvent.RequestType.ADD_TO_MY_LIST);
        EventBus.getDefault().post(event);
        organizations.remove(position);
        orgAdapter.notifyDataSetChanged();
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

    }

    @Override
    public void reminder(int position, Organization organization) {

    }

    public interface OnCalendarFragementListener {
        void showLoading();
        void dismissLoading();
    }
}
