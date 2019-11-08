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
import com.tech.agape4charity.adapter.OrgRecyclerViewAdapter;
import com.tech.agape4charity.object.Organization;

import java.util.ArrayList;


public class SearchResultFragment extends BaseFragment implements OrgRecyclerViewAdapter.OrgAdapterCallback {
    private SearchResultFragment.OnSearchResultFragmentListener mListener;
    private int mColumnCount = 1;
    private OrgRecyclerViewAdapter orgAdapter;
    private ArrayList<Organization> organizations = new ArrayList<>();

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        init(this, view);

        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            orgAdapter = new OrgRecyclerViewAdapter(organizations, this);
            recyclerView.setAdapter(orgAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchResultFragmentListener) {
            mListener = (OnSearchResultFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchResultFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setOrganizations(ArrayList<Organization> list){
        orgAdapter = new OrgRecyclerViewAdapter(organizations, this);
        orgAdapter.add(list);
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

    public interface OnSearchResultFragmentListener {
    }
}
