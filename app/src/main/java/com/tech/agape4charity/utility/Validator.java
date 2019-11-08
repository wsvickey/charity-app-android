package com.tech.agape4charity.utility;

import android.util.Patterns;
import android.widget.EditText;

import com.tech.agape4charity.R;

/**
 * Created by Charitha Ratnayake on 11/18/16.
 */

public class Validator {

    public static Boolean isEmpty(EditText editText) {
        String text = editText.getText().toString();

        if (!text.isEmpty()) {
            return false;
        } else {
            setError(editText, editText.getContext().getString(R.string.error_this_is_req));
            return true;
        }
    }

    public static Boolean isEmail(EditText editText) {
        if(isEmpty(editText))return false;

        String text = editText.getText().toString();

        if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return true;
        } else {
            setError(editText, editText.getContext().getString(R.string.error_this_is_req));
            return false;
        }
    }

    public static boolean isValidPhoneNo(EditText editText) {
        if(isEmpty(editText))return false;

        String text = editText.getText().toString();

        if (Patterns.PHONE.matcher(text).matches()) {
            return true;
        } else {
            setError(editText, editText.getContext().getString(R.string.error_invalid_phone));
            return false;
        }
    }

    public static Boolean isValidPassword(EditText editText1, EditText editText2) {
        if(isEmpty(editText1))return false;
        if(isEmpty(editText2))return false;

        String text1 = editText1.getText().toString();
        String text2 = editText2.getText().toString();

        if (text1.equals(text2)) {
            return true;
        } else {
            setError(editText2, editText2.getContext().getString(R.string.error_not_valid_password));
            return false;
        }
    }

    private static void setError(EditText editText, String message) {
        if (editText == null || message == null) return;

        editText.setError(message);
    }
}
