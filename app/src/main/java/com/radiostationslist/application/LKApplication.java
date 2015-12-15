package com.radiostationslist.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.radiostationslist.R;
import com.radiostationslist.model.LK_PostModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * LKApplication.java is Lynking Application's delegate class
 * <p/>
 * Created by dhananjay on 7/7/15.
 */
public class LKApplication extends Application {

    public static LKApplication LKApplicationInstance = null;

    public static ImageLoader mImageloader = null;
    private static String LOG_TAG = LKApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * initialize ImageLoader
         */
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(config);
        LKApplicationInstance = this;
        getImageLoader(this);
    }

    /**
     * Method to initialize an object
     *
     * @return application object
     */
    public static LKApplication getApplicationInstance(/*Context mContext*/) {

        if (LKApplicationInstance == null) {
            LKApplicationInstance = new LKApplication();
        }

        return LKApplicationInstance;
    }


    /**
     * Returns singleton class instance
     */
    public static ImageLoader getImageLoader(Context mContext) {
        if (mImageloader == null) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    // added following 3 lines
                    .showImageForEmptyUri(R.mipmap.internet_radio_now_playing_radio_art)
                    .showImageOnFail(R.mipmap.internet_radio_now_playing_radio_art)
                    .showImageOnLoading(R.mipmap.internet_radio_now_playing_radio_art)
                    .considerExifParams(true)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                    .defaultDisplayImageOptions(defaultOptions)
                    .build();
            ImageLoader.getInstance().init(config);

            mImageloader = ImageLoader.getInstance();

        }

        return mImageloader;
    }

    /**
     * Method to Convert InputStream to String
     *
     * @param is InputStream
     * @return string
     * @throws Exception
     */

    public String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    /**
     * Method to add model class object in ArrayList
     *
     * @param mArrPostParam ArrayList<LK_PostModel>
     * @param key           String
     * @param Value         String
     * @param ParamType     String
     * @return ArrayList
     */
    public ArrayList<LK_PostModel> LK_setPostParams(ArrayList<LK_PostModel> mArrPostParam, String
            key, String Value, String ParamType) {

        LK_PostModel mLK_PostModel = new LK_PostModel();

        mLK_PostModel.setStr_PostParamKey(key);

        mLK_PostModel.setObj_PostParamValue(Value);

        mLK_PostModel.setStr_PostParamType(ParamType);

        mArrPostParam.add(mLK_PostModel);

        return mArrPostParam;
    }

    /**
     * Method to add model class object in ArrayList
     *
     * @param mArrPostParam ArrayList<LK_PostModel>
     * @param key           String
     * @param Value         Integer
     * @param ParamType     String
     * @return ArrayList
     */
    public ArrayList<LK_PostModel> LK_setPostParams(ArrayList<LK_PostModel> mArrPostParam, String
            key, Integer Value, String ParamType) {

        LK_PostModel mLK_PostModel = new LK_PostModel();

        mLK_PostModel.setStr_PostParamKey(key);

        mLK_PostModel.setObj_PostParamValue(Value);

        mLK_PostModel.setStr_PostParamType(ParamType);

        mArrPostParam.add(mLK_PostModel);

        return mArrPostParam;
    }

    /**
     * Method to add model class object in ArrayList
     *
     * @param mArrPostParam ArrayList<LK_PostModel>
     * @param key           String
     * @param Value         Bitmap
     * @param ParamType     String
     * @return ArrayList
     */
    public ArrayList<LK_PostModel> LK_setPostParams(ArrayList<LK_PostModel> mArrPostParam, String
            key, Bitmap Value, String ParamType) {

        LK_PostModel mLK_PostModel = new LK_PostModel();

        mLK_PostModel.setStr_PostParamKey(key);

        mLK_PostModel.setObj_PostParamValue(Value);

        mLK_PostModel.setStr_PostParamType(ParamType);

        mArrPostParam.add(mLK_PostModel);

        return mArrPostParam;
    }
}