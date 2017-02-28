package com.example.android.myapplication.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gowth on 1/3/2017.
 */

public class NetWorkUtils {


    public static URL getnewsUrl(String order) {

        URL url = null;
        try {
            switch (order) {
                case "sport":
                    url = new URL("https://newsapi.org/v1/sources?category=sport");
                    break;
                case "technology":
                    url = new URL("https://newsapi.org/v1/sources?category=technology");
                    break;
                case "general":
                    url = new URL("https://newsapi.org/v1/sources?category=general");
                    break;
                case "business":
                    url = new URL("https://newsapi.org/v1/sources?category=business");
                    break;
                case "entertainment":
                    url = new URL("https://newsapi.org/v1/sources?category=entertainment");
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //   Log.v(TAG, "Built URI " + url);

        return url;

    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
