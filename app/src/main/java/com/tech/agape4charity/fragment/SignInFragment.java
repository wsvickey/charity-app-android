package com.tech.agape4charity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tech.agape4charity.BaseFragment;
import com.tech.agape4charity.R;
import com.tech.agape4charity.service.RequestEvent;
import com.tech.agape4charity.utility.Validator;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInFragment extends BaseFragment {

    private OnSignInFragmentListener mListener;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init(SignInFragment.this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignInFragmentListener) {
            mListener = (OnSignInFragmentListener) context;
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

    @OnClick(R.id.btnRegister)
    public void register(View view) {

        if(!Validator.isEmpty(etName)
                && Validator.isEmail(etEmail) && Validator.isValidPhoneNo(etPhone)
                && !Validator.isEmpty(etAddress) && Validator.isValidPassword(etPassword, etRePassword)){
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim().toLowerCase();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            RequestEvent event = new RequestEvent(name, email, phone, address, password, RequestEvent.RequestType.REGISTER);

            EventBus.getDefault().post(event);
        }
    }

    public interface OnSignInFragmentListener {
    }
}
