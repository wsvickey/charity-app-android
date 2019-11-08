package com.tech.agape4charity;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class BaseFragment extends Fragment {

    protected Context context;
    protected Unbinder unbinder;

    protected void init(Fragment fragment, View view) {
        context = view.getContext();
        unbinder = ButterKnife.bind(fragment, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null)unbinder.unbind();
    }

}
