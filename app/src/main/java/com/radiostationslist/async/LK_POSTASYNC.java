package com.radiostationslist.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.radiostationslist.R;
import com.radiostationslist.application.LKApplication;
import com.radiostationslist.interfaces.OnReceiveServerResponse;
import com.radiostationslist.model.LK_PostModel;
import com.radiostationslist.utility.AppUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * LK_POSTASYNC class is used to execute POST Request By Using this class we
 * can send Data(image, text, multimedia content etc) on Server
 */
public class LK_POSTASYNC extends AsyncTask<String, Void, String> {

    /**
     * LK_POSTASYNC Members Declarations
     */
//    private ProgressDialog newprogress;
    private Context mContext;
    private OnReceiveServerResponse mOnReceiveServerResponse;
    private String str_PostApiName;
    private String str_PostRequestURL;
    private ArrayList<LK_PostModel> arr_PostModels;
    private LKApplication mLKApplication;
    /* public static */
    private boolean isShowProgressDialog;
    private String strNetwork_Error = "Network_Error";
    private String strNo_Internet_Connection = "No_Internet_Connection";
    private String strHttpHostConnectException = "HttpHostConnectException";
    private String strSocketTimeoutException = "SocketTimeoutException";
    private String strUnknownHostException = "UnknownHostException";
    private final String LOG_TAG = "LK_POSTASYNC";
//    private volatile boolean running = true;

    /**
     * Constructor Implementations
     */
    public LK_POSTASYNC(Context context,
                        OnReceiveServerResponse onReceiveServerResponse, String mPostApiName,
                        String mPostRequestURL, ArrayList<LK_PostModel> mPostModelArray,
                        boolean isShowProgressDialog) {

        this.mContext = context;
        this.mOnReceiveServerResponse = onReceiveServerResponse;
        this.str_PostApiName = mPostApiName;
        this.str_PostRequestURL = mPostRequestURL;
        this.arr_PostModels = mPostModelArray;
        mLKApplication = LKApplication.getApplicationInstance();
        this.isShowProgressDialog = isShowProgressDialog;

    }

    /**
     * This Method is called before the execution start
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // ProgressHUD.show(mContext,true);

        if (isShowProgressDialog) {
//            newprogress = new ProgressDialog(mContext);
//            newprogress.setMessage(mContext.getString(R.string.LoadingProgressDialog));
//            newprogress.setCancelable(false);
//            if (!newprogress.isShowing()) {
//                newprogress.show();
//            }
            AppUtil.showProgressDialog(mContext, null);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(LOG_TAG, "in onCancelled() of LK_POSTASYNC");
//        if (newprogress != null) {
//            if (newprogress.isShowing()) {
//                try {
//                    newprogress.dismiss();
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        AppUtil.hideProgressDialog(mContext);

//        running = false;
    }

    /**
     * This Method is called during the execution
     */
    @Override
    protected String doInBackground(String... params) {
        if (!AppUtil.isInternetAvailable(mContext)) {
            Log.d(LOG_TAG, "doInBackground() returning No_Internet_Connection");
            return strNo_Internet_Connection;
            // return;
        } else {
            try {
//                while (running) {
//                /**
//                 * Dj changes for adding languageTAG parameter for each API request
//                 */
//                LMSC_PostModel mSeren_PostModel = new LMSC_PostModel();
//                mSeren_PostModel.setStr_PostParamKey("language");
//                mSeren_PostModel
//                        .setStr_PostParamType(ServerRequestConstants.Key_PostStrValue);
//                mSeren_PostModel
//                        .setObj_PostParamValue(LMSCApplication.tag_language);
//                arr_PostModels.add(mSeren_PostModel);
                /**
                 * Establishing Connection with Server
                 */
                HttpClient mHttpClient = new DefaultHttpClient();
                HttpContext mHttpContext = new BasicHttpContext();
                HttpPost mHttpPost = new HttpPost(str_PostRequestURL);

                /**
                 * Setting up Parameters with the request
                 */
                MultipartEntity mMultipartEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                /**
                 * Process to get Request Parameter Values from ArrayList
                 * ArryList Value str_PostParamMIME Represent the MIME Type of
                 * Data and str_PostParamKey Represent the Request Param Key and
                 * Obj_PostParamValue Represent the their values
                 */

                StringBuilder builder = null;
//                for (int i = 0; i < arr_PostModels.size(); i++) {
//                    if (arr_PostModels
//                            .get(i)
//                            .getStr_PostParamType()
//                            .equalsIgnoreCase(
//                                    (ServerRequestConstants.Key_PostStrValue))) {
//                        mMultipartEntity.addPart(arr_PostModels.get(i)
//                                .getStr_PostParamKey(), new StringBody(
//                                (String) arr_PostModels.get(i)
//                                        .getObj_PostParamValue()));
//                        String param = arr_PostModels.get(i)
//                                .getStr_PostParamKey()
//                                + " = "
//                                + arr_PostModels.get(i).getObj_PostParamValue();
//
//                        if (builder == null) {
//                            builder = new StringBuilder();
//                            builder.append(param);
//                        } else {
//                            builder.append(", " + param);
//                        }
//                    } else if (arr_PostModels
//                            .get(i)
//                            .getStr_PostParamType()
//                            .equalsIgnoreCase(
//                                    (ServerRequestConstants.Key_PostbyteValue))) {
//                        Bitmap thePic = (Bitmap) arr_PostModels.get(i)
//                                .getObj_PostParamValue();
//
//                        byte[] photo = null;
//                        Log.d(LOG_TAG, "Image is selected");
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        thePic.compress(CompressFormat.JPEG, 80, bos);
//                        photo = bos.toByteArray();
//
//                        ByteArrayBody mBody = new ByteArrayBody((byte[]) photo,
//                                "image_" + System.currentTimeMillis() + ".jpg");
//
//                        mMultipartEntity.addPart(arr_PostModels.get(i)
//                                .getStr_PostParamKey(), mBody);
//
//                        String param = arr_PostModels.get(i)
//                                .getStr_PostParamKey()
//                                + " = "
//                                + arr_PostModels.get(i).getObj_PostParamValue();
//
//                        if (builder == null) {
//                            builder = new StringBuilder();
//                            builder.append(param);
//                        } else {
//                            builder.append(", " + param);
//                        }
//
//                    } else if (arr_PostModels
//                            .get(i)
//                            .getStr_PostParamType()
//                            .equalsIgnoreCase(
//                                    (ServerRequestConstants.Key_PostFileValue))) {
//
//                    }
//
//                }
//
//                try {
//                    if (builder != null)
//                        Log.d(LOG_TAG, "post param => " + builder.toString());
//                } catch (NullPointerException e) {
//                    /* generally when no parameters or NULL */
//                    e.printStackTrace();
//                }
                mHttpPost.setEntity(mMultipartEntity);
                HttpResponse mHttpResponse = null;
                try {
                    mHttpResponse = mHttpClient
                            .execute(mHttpPost, mHttpContext);
                } catch (HttpHostConnectException e) {
                    e.printStackTrace();
                    return strHttpHostConnectException;
                } catch (SocketException e/*||SocketTimeoutException e*/) {
                    //03-19 12:22:32.252: W/System.err(30229): java.net.SocketException: recvfrom
                    // failed: ETIMEDOUT (Connection timed out)
                    e.printStackTrace();
                    return strNetwork_Error;
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    return strSocketTimeoutException;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return strUnknownHostException;
                } catch (Exception e) {
                    e.printStackTrace();
                    return strNetwork_Error;
                }

                if (mHttpResponse != null) {

                    HttpEntity mHttpEntity = mHttpResponse.getEntity();
                    InputStream mInputStream = mHttpEntity.getContent();
                    if (mInputStream != null) {
                        String mPostResult = mLKApplication
                                .convertStreamToString(mInputStream);
                        if (mPostResult != null) {

                            Log.d(LOG_TAG, "Response Message : "
                                    + mPostResult);
                            return mPostResult;
                        } else {
                            return null;
                        }
                    } else {
                        return strNetwork_Error;
                    }
                } else {
                    return strNetwork_Error;
                }
//                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG, " JSONException Post ");
            }
            return null;
        }

    }

    /**
     * This Method is called after the execution finish
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // ProgressHUD.hideProgressDialog(mContext);
        if (isShowProgressDialog /*&& newprogress.isShowing()*/) {
//            newprogress.dismiss();
            AppUtil.hideProgressDialog(mContext);
        }
        if (result != null) {
            if (result.equalsIgnoreCase(strNo_Internet_Connection) || (result.equalsIgnoreCase
                    (strNetwork_Error))) {
                AppUtil.showAlert(mContext, mContext.getResources().getString(R.string.
                                NoInternetConnection
                ), null, false, null);

            } else if (result.equals(strHttpHostConnectException) || result.equals(strUnknownHostException)) {
                AppUtil.showAlert(mContext, mContext.getResources().getString(R.string.
                                UnableToConnectToServer
                ), null, false, null);

            } else if (result.equals(strSocketTimeoutException)) {
                AppUtil.showAlert(mContext, mContext.getResources().getString(R.string.
                                RequestTimedOutConnectingToServer
                ), null, false, null);

            } else {
                this.mOnReceiveServerResponse.setOnReceiveResult(str_PostApiName,
                        result);
            }
        } else
            Log.e(LOG_TAG, "result null in onPostExecute()");
    }
}