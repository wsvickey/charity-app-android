package com.tech.agape4charity.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.agape4charity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public  class MoreSettingsDialogFragment extends DialogFragment {

    private OnMoreSettingsDialogFragmentListener mListener;

    public MoreSettingsDialogFragment() {
    }

    public static MoreSettingsDialogFragment newInstance() {
        MoreSettingsDialogFragment fragment = new MoreSettingsDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_settings, container, false);

        ButterKnife.bind(this, view);



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoreSettingsDialogFragmentListener) {
            mListener = (OnMoreSettingsDialogFragmentListener) context;
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

    @OnClick(R.id.btnChangePassword)
    public void onChangePassword(View view){
        mListener.onChangePassword();
    }

    @OnClick(R.id.btnLogout)
    public void onLogout(View view){
        mListener.onLogout();
    }

    public interface OnMoreSettingsDialogFragmentListener {
        void onChangePassword();
        void onLogout();
    }
}
