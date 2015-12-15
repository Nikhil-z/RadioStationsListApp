package com.radiostationslist.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.radiostationslist.R;
import com.radiostationslist.adapter.Station_ListViewAdapter;
import com.radiostationslist.async.LK_POSTASYNC;
import com.radiostationslist.constants.AppConstants;
import com.radiostationslist.interfaces.OnReceiveServerResponse;
import com.radiostationslist.model.StationModel;
import com.radiostationslist.utility.AppUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity implements OnReceiveServerResponse {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private ListView listView_RadioStationsList;
    private Station_ListViewAdapter mStation_ListViewAdapter;

    private ArrayList<StationModel> al_StationModel = new ArrayList<>();
    private ArrayList<StationModel> al_AM_StationData = new ArrayList<>();
    private ArrayList<StationModel> al_FM_StationData = new ArrayList<>();
    private ArrayList<StationModel> al_InternetRadio_StationData = new ArrayList<>();

    private String strUrlToRequest =
            "http://phorus.vtuner.com/setupapp/phorus/asp/browsexml/navXML.asp?gofile=LocationLevelFourCityUS-North%20America-New%20York-ExtraDir-1-Inner-14&bkLvl=9237&mac=a8f58cd9758b710c43a7a63762e755947f83f0ad9194aa294bbaee55e0509e02&dlang=eng&fver=1.4.4.2299%20(20150604)&hw=CAP:%201.4.0.075%20MCU:%201.032%20BT:%200.002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView_RadioStationsList = (ListView) findViewById(R.id.listView_RadioStationsList);

        Log.d(LOG_TAG, "in onCreate()");

        requestAsync_stationData();
    }

    private void requestAsync_stationData() {
        Log.v(LOG_TAG, "in requestAsyncList_card()");

        OnReceiveServerResponse mOnReceiveServerResponse = this;

        LK_POSTASYNC mLkPostasync = new LK_POSTASYNC(MainActivity.this, mOnReceiveServerResponse,
                AppConstants.API_StationData, strUrlToRequest, null, true);
        mLkPostasync.execute();
    }

    @Override
    public void setOnReceiveResult(String ApiName, String ApiResult) {
        if (ApiName.equals(AppConstants.API_StationData)) {

            if (ApiResult != null) {

                try {
                    parse_StationData(MainActivity.this, ApiResult);

                    sortArrayThenSetAdapter();

                } catch (Exception e) {
                    e.printStackTrace();
                    AppUtil.showAlert(MainActivity.this, getString(R.string.OopsSomethingWentWrong),
                            null, true, null);
                }

            } else
                Log.e(LOG_TAG, "ApiResult null");

        }
    }

    private void sortArrayThenSetAdapter() {
        if (al_FM_StationData != null) {
            Log.v(LOG_TAG, "comparing FM");
            Collections.sort(al_FM_StationData, StationModel.freq_Comparator);

            if (al_FM_StationData.size() > 0)
                al_FM_StationData.get(0).setIsHeader(1);
        }

        if (al_AM_StationData != null) {
            Log.v(LOG_TAG, "comparing AM");
            Collections.sort(al_AM_StationData, StationModel.freq_Comparator);

            if (al_AM_StationData.size() > 0)
                al_AM_StationData.get(0).setIsHeader(1);
        }


        if (al_InternetRadio_StationData != null) {
            Log.v(LOG_TAG, "comparing InternetRadio");
            Collections.sort(al_InternetRadio_StationData, StationModel.stationName_Comparator);

            if (al_InternetRadio_StationData.size() > 0)
                al_InternetRadio_StationData.get(0).setIsHeader(1);
        }

        if (al_StationModel != null) {
            if (al_AM_StationData != null)
                al_StationModel.addAll(al_AM_StationData);

            if (al_FM_StationData != null)
                al_StationModel.addAll(al_FM_StationData);

            if (al_InternetRadio_StationData != null)
                al_StationModel.addAll(al_InternetRadio_StationData);

            Log.v(LOG_TAG, "after adding al_StationModel.size():" + al_StationModel.size());

            setListViewAdapter();
        }
    }

    private void setListViewAdapter() {

        if (al_StationModel != null)
            mStation_ListViewAdapter = new Station_ListViewAdapter(MainActivity.this, al_StationModel);

        if (mStation_ListViewAdapter != null)
            listView_RadioStationsList.setAdapter(mStation_ListViewAdapter);
    }

    private void parse_StationData(Context mContext, String ApiResponseInXml) {

        StationModel mStationModel;

        if (mContext != null && ApiResponseInXml != null) {

            Document doc = getDomElement(ApiResponseInXml);

            if (doc != null) {
                NodeList nl = doc.getElementsByTagName("Item");

                for (int i = 0; i < nl.getLength(); i++) {

                    mStationModel = new StationModel();

                    Element mElement = (Element) nl.item(i);

                    mStationModel.setStationId(getValue(mElement, AppConstants.StationId));
                    mStationModel.setStationName(getValue(mElement, AppConstants.StationName));
                    mStationModel.setBand(getValue(mElement, AppConstants.Band));
                    mStationModel.setFreq(getValue(mElement, AppConstants.Freq));
                    mStationModel.setStationFormat(getValue(mElement, AppConstants.StationFormat));
                    mStationModel.setStationDesc(getValue(mElement, AppConstants.StationDesc));
                    mStationModel.setLogo(getValue(mElement, AppConstants.Logo));

                    Log.v(LOG_TAG, "just before al_StationData.add(mStationModel); i:" + i);

                    if (mStationModel.getBand().equals(AppConstants.AM)) {

                        al_AM_StationData.add(mStationModel);

                    } else if (mStationModel.getBand().equals(AppConstants.FM)) {

                        al_FM_StationData.add(mStationModel);

                    } else if (mStationModel.getBand().equals(AppConstants.InternetRadio)) {

                        al_InternetRadio_StationData.add(mStationModel);

                    } else
                        Log.e(LOG_TAG, "No match for Band:" + mStationModel.getBand());
                }
            } else
                AppUtil.showAlert(MainActivity.this, getString(R.string.OopsSomethingWentWrong),
                        null, true, null);
        } else
            Log.e(LOG_TAG, "mContext or ApiResponseInXml null");

    }

    public static Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}