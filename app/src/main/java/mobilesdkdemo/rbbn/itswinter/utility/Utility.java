package mobilesdkdemo.rbbn.itswinter.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTimeFromToday(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        // LocalDateTime.from(date.toInstant()).plusDays(day);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        date = c.getTime();
        return dateFormat.format(date);
    }

    public static String getCurrentMonth(){
        return  getCurrentDateTime().substring(0,7);
    }

    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

    public static Intent getWebViewIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public static void showSnackbar(View v,String str){
        Snackbar.make(v, str, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static void createAndShowDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("title");
        builder.setMessage("message");
        builder.setPositiveButton("yes", (DialogInterface.OnClickListener) context);
        builder.setNeutralButton("cancel", (DialogInterface.OnClickListener) context);
        builder.setNegativeButton("no", (DialogInterface.OnClickListener) context);
        builder.create().show();
    }
}
