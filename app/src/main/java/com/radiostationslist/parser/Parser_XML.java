package com.radiostationslist.parser;

import android.content.Context;
import android.util.Log;

import com.radiostationslist.constants.AppConstants;
import com.radiostationslist.model.StationModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by dhananjay on 17/10/15.
 */
public class Parser_XML {

    private final String LOG_TAG = Parser_XML.class.getSimpleName();

    public ArrayList<StationModel> parse_StationData(Context mContext, String ApiResponseInXml)
            throws XmlPullParserException, IOException {
        ArrayList<StationModel> al_StationData = new ArrayList<>();
        ArrayList<StationModel> al_AM_StationData = new ArrayList<>();
        ArrayList<StationModel> al_FM_StationData = new ArrayList<>();
        ArrayList<StationModel> al_InternetRadio_StationData = new ArrayList<>();

        StationModel mStationModel;

        if (mContext != null && ApiResponseInXml != null) {

            /**
             * using XmlPullParser
             */
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser xpp = factory.newPullParser();
//
//            xpp.setInput(new StringReader(ApiResponseInXml));
//            int eventType = xpp.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//
//                if (eventType == XmlPullParser.START_DOCUMENT) {
//
//                    Log.v(LOG_TAG, "Start document");
//
//                } else if (eventType == XmlPullParser.START_TAG) {
//
//                    Log.v(LOG_TAG, "Start tag " + xpp.getName());
//
//                    if (xpp.getName().equals("Item")) {
//                        mStationModel = new StationModel();
//                    } else if (xpp.getName().equals("ItemType")) {
//                        mStationModel.set
//                    }
//
//                } else if (eventType == XmlPullParser.END_TAG) {
//
//                    Log.v(LOG_TAG, "End tag " + xpp.getName());
//
//                    if (xpp.getName().equals("Item")) {
//                        if (mStationModel != null)
//                            al_StationData.add(mStationModel);
//                    }
//
//                } else if (eventType == XmlPullParser.TEXT) {
//
//                    Log.v(LOG_TAG, "Text " + xpp.getText());
//                }
//
//                eventType = xpp.next();
//
//            }
//            Log.v(LOG_TAG, "End document");

            Document doc = getDomElement(ApiResponseInXml); // getting DOM element

            if (doc != null) {
                NodeList nl = doc.getElementsByTagName("Item");
                // looping through all item nodes <item>
                for (int i = 0; i < nl.getLength(); i++) {
//                    // creating new HashMap
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    Element e = (Element) nl.item(i);
//                    // adding each child node to HashMap key => value
//                    map.put(KEY_ID, getValue(e, KEY_ID));
//                    map.put(KEY_NAME, getValue(e, KEY_NAME));
//                    map.put(KEY_COST, "Rs." + getValue(e, KEY_COST));
//                    map.put(KEY_DESC, getValue(e, KEY_DESC));
//
//                    // adding HashList to ArrayList
//                    al_StationData.add(map);

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

                    }
                }
            }
        } else
            Log.e(LOG_TAG, "mContext or ApiResponseInXml null");

        return al_StationData;
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