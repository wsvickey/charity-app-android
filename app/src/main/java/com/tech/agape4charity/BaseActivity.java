package com.tech.agape4charity;

import android.app.Dialog;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class BaseActivity extends AppCompatActivity {
    public  Dialog mProgress;

    protected Fragment startFragment(int id, Fragment fragment, boolean isBackStackEnable){
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();

        ft.replace(id, fragment);

        if(isBackStackEnable)
            ft.addToBackStack(null);

        ft.commit();

        return fragment;
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public  void showWaiting(Context context) {

        if (mProgress == null) {
            mProgress = new Dialog(context, R.style.Progressbar);
            mProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgress.setContentView(R.layout.custom_progress_spinner);
            mProgress.setCancelable(false);
        }

        if (mProgress.isShowing() == false) {
            mProgress.show();
        }
    }

    public  void dismissWaiting() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

}
