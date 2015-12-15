package com.radiostationslist.utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.radiostationslist.R;

/**
 * Created by dhananjay on 17/10/15.
 */
public class AppUtil {

    public static String LOG_TAG = AppUtil.class.getSimpleName();
    public static ProgressDialog mProgressDialog;
    public static Dialog mDialog;

    /**
     * method to check if internet is connected or not
     *
     * @param mContext Context
     * @return true if connected else false
     */
    public static boolean isInternetAvailable(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * static method to SHOW progress dialog for the App.
     * <p/>
     * use Utility.hideProgressDialog(mContext)to hide this dialog.
     *
     * @param mContext                   Context
     * @param strMessageOnProgressDialog String Message On Progress Dialog
     */
    public static void showProgressDialog(Context mContext, String strMessageOnProgressDialog) {
        Log.v(LOG_TAG, "showProgressDialog()");
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                return;
            }
        }
        else
            mProgressDialog = new ProgressDialog(mContext);
        if (strMessageOnProgressDialog != null) {
            mProgressDialog.setMessage(strMessageOnProgressDialog);
        } else {
            try {
                mProgressDialog.setMessage(mContext.getString(R.string.LoadingProgressDialog));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mProgressDialog.setCancelable(false);
        try {
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();// Window$BadTokenException

            mProgressDialog = null;
            mProgressDialog = new ProgressDialog(mContext);
            if (strMessageOnProgressDialog != null) {
                mProgressDialog.setMessage(strMessageOnProgressDialog);
            } else {
                try {
                    mProgressDialog.setMessage(mContext.getString(R.string.LoadingProgressDialog));
                } catch (Exception ex) {
                    e.printStackTrace();
                }
            }
            mProgressDialog.setCancelable(false);
            try {
                mProgressDialog.show();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    /**
     * static method to HIDE progress dialog for the App.
     *
     * @param mContext Context
     */
    public static void hideProgressDialog(Context mContext) {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show Alert Dialog
     *
     * @param mContext         Context
     * @param strAlertMessage  String Alert Message to display
     * @param strBtnText       String Button Text to display
     * @param isCancellable    boolean Alert should hide on back press or not
     * @param mOnClickListener OnClickListener to handle click of the button on Alert
     */
    public static void showAlert(final Context mContext, String strAlertMessage, String strBtnText,
                                 boolean isCancellable, View.OnClickListener mOnClickListener) {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
        mDialog = new Dialog(mContext, R.style.Dialog_No_Border);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.alert_with_header);
        mDialog.setCancelable(isCancellable);
        TextView txtAlertText_no_header = (TextView) mDialog.findViewById(R.id.txtAlertText_no_header);
        Button btnAlert = (Button) mDialog.findViewById(R.id.btnAlert_no_header);

        if (mOnClickListener != null) {
            btnAlert.setOnClickListener(mOnClickListener);
        } else {
            btnAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }
        if (strAlertMessage != null) {
            txtAlertText_no_header.setText(strAlertMessage);

        }
        if (strBtnText != null) {
            btnAlert.setText(strBtnText);
        }
        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}