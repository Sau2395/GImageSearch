package com.saurabh.gimagesearch;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;

class ConnectionCheck {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = cm.getActiveNetworkInfo() != null;

        if (!connected) {
            new AlertDialog.Builder(context)
                    .setTitle("NO INTERNET CONNECTION")
                    .setMessage("Oops !!! It seems that you are not connected to the Internet.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return connected;
    }
}