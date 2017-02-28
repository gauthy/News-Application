package com.example.android.myapplication.Utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gowth on 2/23/2017.
 */

public class NewsJson {
    public static String[] getSimpleNewsStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        String[] newsdetailData = null;

        final String LIST = "sources";

        int k = 0;
        JSONObject newsJson = new JSONObject(movieJsonStr);


        JSONArray newsArray = newsJson.getJSONArray(LIST);

        newsdetailData = new String[newsArray.length() * 10];


        for (int i = 0; i < newsArray.length(); i++) {

            JSONObject singlesource = newsArray.getJSONObject(i);

            String source = singlesource.getString("id");

            String[] values = getreviews(source);
            if (values.length > 0) {
                for (int j = 0; j < values.length; j++) {
                    newsdetailData[k] = values[j];
                    k++;
                }
            }

        }

        return newsdetailData;

    }

    public static String[] getSimpleNewsArticlesFromJson(String newsJsonStr)
            throws JSONException {
        String[] detailData = null;

        final String LIST = "articles";


        JSONObject newsJson = new JSONObject(newsJsonStr);


        JSONArray newsArray = newsJson.getJSONArray(LIST);
        detailData = new String[newsArray.length()];

        if (newsArray.length() > 0) {

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject singlesource = newsArray.getJSONObject(i);


                String title = singlesource.getString("title");
                String author = singlesource.getString("author");
                String urltoimage = singlesource.getString("urlToImage");
                String link = singlesource.getString("url");
                String description = singlesource.getString("description");
                detailData[i] = urltoimage + ">" + title + ">" + author + ">" + link + ">" + description;


            }
        }

        return detailData;

    }

    private static String[] getreviews(String id) {


        URL url = null;
        try {

            url = new URL("https://newsapi.org/v1/articles?source=" + id + "&apiKey=2a041cc1ee0a4ecb94c11c95261caef1");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String[] SimpleJsondata = null;
        String Jsonresponse = null;
        try {
            Jsonresponse = NetWorkUtils.getResponseFromHttpUrl(url);

            SimpleJsondata = getSimpleNewsArticlesFromJson(Jsonresponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SimpleJsondata;
    }


}
