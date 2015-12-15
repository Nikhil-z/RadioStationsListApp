package com.radiostationslist.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.radiostationslist.R;
import com.radiostationslist.application.LKApplication;
import com.radiostationslist.model.StationModel;

import java.util.ArrayList;

/**
 * Station_ListViewAdapter for ListCardFragment.java
 * <p/>
 * Created by dhananjay on 31/7/15.
 */
public class Station_ListViewAdapter extends BaseAdapter {

    private ArrayList<StationModel> al_StationModel_received;
    private Context mContext;
    private final String LOG_TAG = Station_ListViewAdapter.class.getSimpleName();
    private View.OnClickListener mOnClickListener;
    private String or_symbol_with_spaces;
    //    private LayoutInflater inflater;
//    private DisplayImageOptions mDisplayImageOptions;

    public Station_ListViewAdapter(Context mContext, ArrayList<StationModel> al_StationModel) {
        Log.v(LOG_TAG, "in Station_ListViewAdapter() constructor");
        this.mContext = mContext;
        this.al_StationModel_received = al_StationModel;

        or_symbol_with_spaces = this.mContext.getString(R.string.or_symbol_with_spaces);

//        mDisplayImageOptions = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.drawable.rectangle_white_solid_black_border)
//                .showImageOnFail(R.drawable.rectangle_white_solid_black_border)
//                .showImageOnLoading(R.drawable.rectangle_white_solid_black_border)
//                .cacheOnDisk(true)
//                .cacheInMemory(true)
//                .considerExifParams(true)
//                .build();
    }

    @Override
    public int getCount() {
        if (al_StationModel_received != null) {
            Log.v(LOG_TAG, "in getCount() al_ size:" + al_StationModel_received.size());
            return al_StationModel_received.size();
        } else {
            Log.e(LOG_TAG, "al_StationModel_received is null so getCount() returning 0");
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return al_StationModel_received.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        private TextView txtHeaderBand_list_item_view_stations,
                txtFrequencyAndName_list_item_view_stations,
                txtStationDesc_list_item_view_stations,
                txtStationFormat_list_item_view_stations;
        private View view_blueBelowHeader_list_item_view_stations;
        private ImageView imgLogo_list_item_view_stations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(LOG_TAG, "in getView()");
        ViewHolder mViewHolder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.
                    LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_stations_list, null);

            mViewHolder = new ViewHolder();

            mViewHolder.txtHeaderBand_list_item_view_stations = (TextView) convertView.findViewById(
                    R.id.txtHeaderBand_list_item_view_stations);

            mViewHolder.view_blueBelowHeader_list_item_view_stations = convertView.findViewById(
                    R.id.view_blueBelowHeader_list_item_view_stations);

            mViewHolder.imgLogo_list_item_view_stations = (ImageView) convertView.findViewById(R.id.
                    imgLogo_list_item_view_stations);

            mViewHolder.txtFrequencyAndName_list_item_view_stations = (TextView) convertView.findViewById(
                    R.id.txtFrequencyAndName_list_item_view_stations);

            mViewHolder.txtStationDesc_list_item_view_stations = (TextView) convertView.findViewById(
                    R.id.txtStationDesc_list_item_view_stations);

            mViewHolder.txtStationFormat_list_item_view_stations = (TextView) convertView.findViewById(
                    R.id.txtStationFormat_list_item_view_stations);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.txtFrequencyAndName_list_item_view_stations.setText(al_StationModel_received.
                get(position).getFreq() + or_symbol_with_spaces + al_StationModel_received.get(
                position).getStationName());

        mViewHolder.txtStationDesc_list_item_view_stations.setText(al_StationModel_received.get(
                position).getStationDesc());

        mViewHolder.txtStationFormat_list_item_view_stations.setText(al_StationModel_received.get(
                position).getStationFormat());

        if (al_StationModel_received.get(position).getLogo() == null) {
            Log.v(LOG_TAG, "getLogo() null or empty");

        } else {
            Log.v(LOG_TAG, "showing server image");
            LKApplication.getImageLoader(mContext).displayImage(al_StationModel_received.get(position).
                    getLogo(), mViewHolder.imgLogo_list_item_view_stations);
        }

        if (al_StationModel_received.get(position).getIsHeader() == 0) {
            mViewHolder.txtHeaderBand_list_item_view_stations.setVisibility(View.GONE);
            mViewHolder.view_blueBelowHeader_list_item_view_stations.setVisibility(View.GONE);
        } else if (al_StationModel_received.get(position).getIsHeader() == 1) {
            mViewHolder.txtHeaderBand_list_item_view_stations.setVisibility(View.VISIBLE);
            mViewHolder.view_blueBelowHeader_list_item_view_stations.setVisibility(View.VISIBLE);

            mViewHolder.txtHeaderBand_list_item_view_stations.setText(al_StationModel_received.get(
                    position).getBand());
        }
        return convertView;
    }
}