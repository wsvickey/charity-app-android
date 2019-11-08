package com.tech.agape4charity.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.agape4charity.BaseFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.adapter.CatRecyclerViewAdapter;
import com.tech.agape4charity.adapter.OrgRecyclerViewAdapter;
import com.tech.agape4charity.object.Category;
import com.tech.agape4charity.object.Organization;
import com.tech.agape4charity.service.RequestEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CategoriesFragment extends BaseFragment implements CatRecyclerViewAdapter.CatAdapterCallback, OrgRecyclerViewAdapter.OrgAdapterCallback {
    private OnCatFragmentListener mListener;
    private int mColumnCount = 1;
    private CatRecyclerViewAdapter catAdapter;
    private OrgRecyclerViewAdapter orgAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Organization> organizations = new ArrayList<>();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        init(this, view);

        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            catAdapter = new CatRecyclerViewAdapter(categories, this);
            orgAdapter = new OrgRecyclerViewAdapter(organizations, this);
            recyclerView.setAdapter(catAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showLoading();
        RequestEvent event = new RequestEvent(RequestEvent.RequestType.GET_CATEGORIES);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCatFragmentListener) {
            mListener = (OnCatFragmentListener) context;
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

    public void setCategories(ArrayList<Category> list) {
        catAdapter.add(list);
        mListener.dismissLoading();
    }

    public void setOrganizations(ArrayList<Organization> list){
        orgAdapter.add(list);
    }

    @Override
    public void onItemClick(int position, Category category) {
        if(mListener != null)mListener.onItemClick(category);
    }

    @Override
    public void onRemove(int position, Organization organization) {

    }

    @Override
    public void addNote(int position, Organization organization) {

    }

    @Override
    public void moreInfo(int position, Organization organization) {

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

    public interface OnCatFragmentListener {
        void onItemClick(Category category);
        void dismissLoading();
        void showLoading();
    }
}
