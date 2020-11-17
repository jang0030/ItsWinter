package mobilesdkdemo.rbbn.itswinter.utility;

import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

public class JsonUtils {

    private static final String TAG="JsonUtils";
    public static <T> ArrayList<T> getArrayListbyUrl(Class<T> target, String urlPath, String firstObjectKey)
    {
        ArrayList<T> list=new ArrayList<>();
        try {
            try {
                URL url = new URL(urlPath);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject json = new JSONObject(sb.toString());
                JSONArray jsonArray = json.getJSONArray(firstObjectKey);


                T tObject = null;
                tObject=createT(target);
                for (int i = 0; i < jsonArray.length(); i++) {
                    tObject = new Gson().fromJson(jsonArray.get(i).toString(), target);
                    list.add(tObject);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            Log.d(TAG, "convertJsonStringToArrayList: "+e.getMessage());
        }

        return list;
    }

    private static <T> T createT(Class<T> forClass) {
        // instantiate via reflection / use constructor or whatsoever
        T tObject = null;
        try {
            tObject = forClass.newInstance();
        } catch (IllegalAccessException e) {
            Log.d("IllegalAccessException", e.getMessage());
        } catch (InstantiationException e) {
            Log.d("InstantiationException", e.getMessage());
        }
        // if not using constuctor args  fill up
        //
        // return new pojo filled object
        return tObject;
    }

}
