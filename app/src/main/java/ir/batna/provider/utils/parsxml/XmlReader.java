package ir.batna.provider.utils.parsxml;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.batna.provider.R;

/**
 * Created by Dev_am1 on 5/6/2020
 */

public class XmlReader {
    private String url3 = "";//change if app has 3 addresses.

    List<String> addressList = new ArrayList<>();
    private static final String TAG = "XmlReader";
    private boolean status = true;
    private boolean inEntry = false;
    private String textValue = "";

    public XmlReader(Context context) {
        getResult(context);
    }

    private List<String> getResult(Context context) {
        XmlResourceParser xpp = context.getResources().getXml(R.xml.config);

        try {
            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
//                        Log.d(TAG, "parse: Starting tag for " + tagName);
                        if ("riot".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            addressList.add(tagName);
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        textValue = xpp.getText();
                        break;
                    }
                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if (inEntry) {
                            if ("address".equalsIgnoreCase(tagName)) {
                                addressList.add(textValue);
                            }
                        }

                        break;
                    default:
                        //nothing left to do.
                }
                eventType = xpp.next();
            }
        } catch (
                XmlPullParserException e) {
            Log.d(TAG, "IOException In calling xml pull parser" + e.getStackTrace());
        } catch (
                IOException e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
        Log.d(TAG, "212 addressList size is" + addressList.size());
        Log.d(TAG, "212 app name " + addressList.get(0));
        Log.d(TAG, "212 address 1 " + addressList.get(1));
        return (addressList);
    }

    public List<String> passData() {
        return new ArrayList<>(addressList);
    }
}


