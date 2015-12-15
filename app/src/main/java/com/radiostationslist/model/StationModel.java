package com.radiostationslist.model;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by dhananjay on 17/10/15.
 */
public class StationModel {

    private static final String LOG_TAG = StationModel.class.getSimpleName();

    private String StationId = "";
    private String StationName = "";
    private String Band = "";
    private String Freq = "";
    private String StationFormat = "";
    private String StationDesc = "";
    private String Logo = "";

    private int isHeader = 0;

    public String getStationId() {
        return StationId;
    }

    public void setStationId(String stationId) {
        StationId = stationId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }

    public String getFreq() {
        return Freq;
    }

    public void setFreq(String freq) {
        Freq = freq;
    }

    public String getStationFormat() {
        return StationFormat;
    }

    public void setStationFormat(String stationFormat) {
        StationFormat = stationFormat;
    }

    public String getStationDesc() {
        return StationDesc;
    }

    public void setStationDesc(String stationDesc) {
        StationDesc = stationDesc;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public int getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    public static Comparator<StationModel> freq_Comparator = new Comparator<StationModel>() {

        @Override
        public int compare(StationModel lhs, StationModel rhs) {
            Log.v(LOG_TAG, "lhs.getFreq():" + lhs.getFreq() + " rhs.getFreq():" + rhs.getFreq());
            float left, right;

            try {
                left = Float.parseFloat(lhs.getFreq());
            } catch (Exception e) {
                e.printStackTrace();
                left = 0f;
            }

            try {
                right = Float.parseFloat(rhs.getFreq());
            } catch (Exception e) {
                e.printStackTrace();
                right = 0f;
            }

            return ((int) (left - right));
        }
    };

    public static Comparator<StationModel> stationName_Comparator = new Comparator<StationModel>() {
        @Override
        public int compare(StationModel lhs, StationModel rhs) {
            return lhs.getStationName().compareTo(rhs.getStationName());
        }
    };
}