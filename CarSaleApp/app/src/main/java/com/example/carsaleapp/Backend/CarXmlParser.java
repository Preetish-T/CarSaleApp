package com.example.carsaleapp.Backend;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// @author: Preetish Thirumalai u7157098

public class CarXmlParser {

    private static final String ns = null;

    public List<Car> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readCars(parser);
        } finally {
            in.close();
        }
    }

    private List<Car> readCars(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Car> cars = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "cars");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("car")) {
                cars.add(readCar(parser));
            } else {
                skip(parser);
            }
        }
        return cars;
    }

    private Car readCar(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "car");

        int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
        String make = null, model = null;
        int price = 0;
        int year = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "make":
                    make = readText(parser);
                    break;
                case "model":
                    model = readText(parser);
                    break;
                case "price":
                    price = Integer.parseInt(readText(parser));
                    break;
                case "year":
                    year = Integer.parseInt(readText(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        Log.e("TAG","ID: "+id+" Make: "+make);
        Log.e("TAG","XML readed");
        return new Car(id, make, model, price, year);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}


