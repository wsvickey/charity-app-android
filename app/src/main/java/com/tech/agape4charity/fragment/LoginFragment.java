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

public class LoginFragment extends BaseFragment {

    private OnLoginFragmentListener mListener;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(LoginFragment.this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mListener = (OnLoginFragmentListener) context;
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

    @OnClick(R.id.btnLogin)
    public void login(View view) {

        if(Validator.isEmail(etEmail) && !Validator.isEmpty(etPassword)){
            String email = etEmail.getText().toString().trim().toLowerCase();
            String password = etPassword.getText().toString().trim();

            RequestEvent event = new RequestEvent(email, password, RequestEvent.RequestType.LOGIN);

            EventBus.getDefault().post(event);
            mListener.showLoading();
        }
    }

    @OnClick(R.id.btnSignUp)
    public void signIn(View view) {
        if(mListener != null)mListener.onSignIn();
    }

    @OnClick(R.id.tvFgtPw)
    public void forgotPw(View view){
        if (mListener != null)mListener.onFgtPw();
    }

    public interface OnLoginFragmentListener {
        void onSignIn();
        void showLoading();
        void onFgtPw();
    }
}
