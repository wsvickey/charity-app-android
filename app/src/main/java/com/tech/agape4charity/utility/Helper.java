package com.tech.agape4charity.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Charitha Ratnayake on 6/6/2018.
 */

public class Helper {

    private static final String TAG = "Helper";

    public static long convertToUnixTimestamp() {

        return Calendar.getInstance().getTimeInMillis() / 1000L;
    }

    public static void sendEmail(Context context, String userEmail, String organitzationEmail, String subject) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{organitzationEmail});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,"There are no email client installed on your device.",Toast.LENGTH_SHORT).show();
        }
    }
    public static void navigateWeb(Context context, String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://"+url));
        context.startActivity(i);
    }

    public static void openDialer(Context context, String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static String getDate(long time) {
        // convert seconds to milliseconds
//        Date date = new java.util.Date(time*1000L);
        Timestamp stamp = new Timestamp(time*1000L);
        Date date = new Date(stamp.getTime());
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
// give a timezone reference for formatting (see comment at the bottom)
        String formattedDate = sdf.format(date);
        Log.d(TAG,"message" + formattedDate );
        return formattedDate;
    }
}
