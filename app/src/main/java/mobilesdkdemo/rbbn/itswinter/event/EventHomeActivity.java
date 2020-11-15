package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventHomeActivity extends AppCompatActivity {

    private ArrayList<Event> eventList = new ArrayList();
    private String name,startDate,tkUrl;
    private double minPrice,maxPrice;
    private Bitmap promoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button searchButton = findViewById(R.id.e_searchButton);
        searchButton.setOnClickListener(x->{
           search();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                EventHomeActivity.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(){
        EditText editText = findViewById(R.id.e_searchBar);
        String searchTerm = editText.getText().toString();

        EventQuery query = new EventQuery();
        String apiKey = "KiOshiJsVO1WxmGWXYxpwy4Yxd7Cu6r1";
        /*test link
            https://app.ticketmaster.com/discovery/v2/events.json?apikey=KiOshiJsVO1WxmGWXYxpwy4Yxd7Cu6r1&city=ottawa&radius=100
        */
        query.execute("https://app.ticketmaster.com/discovery/v2/events.json?apikey="+apiKey+"&city="+searchTerm+"&radius=100");

    }

    private class EventQuery extends AsyncTask<String,Integer,String> {

        protected String doInBackground(String...args) {
            try{
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                // convert string to JSON: Look at slide 27:
                JSONObject events = new JSONObject(result);

//                loops through all events using the value from JSON file
                for(int i =0; i < events.getJSONObject("page").getInt("size"); i++){

//                    stores the JSONObject so don't have to call the huge line every time we want a value
                    JSONObject object = events.getJSONObject("_embedded").getJSONObject("events").getJSONObject(String.valueOf(i));

//                    gets name, start date and Ticket Master URL
                     name = object.getString("name");
                     startDate = object.getJSONObject("start").getString("localDate");
                     tkUrl = object.getString("url");

//                    finds max and min prices
//                    in testing, no event had more then one type of ticket, but it is set up for if there is more then one
                    JSONArray priceArray = object.getJSONArray("priceRanges");
                    int priceArrayLength = object.getJSONArray("priceRanges").length();
                    maxPrice = 0;
                    minPrice = 0;

//                    checks if there is more then one ticket type
                    if(priceArrayLength > 1){
                        double current;
                        for (int x = 0; x < priceArrayLength; x++) {
                            current = priceArray.getJSONObject(x).getDouble("min");
                            if (minPrice > current) {
                                minPrice = current;
                            }
                            current = priceArray.getJSONObject(x).getDouble("max");
                            if (maxPrice > current) {
                                maxPrice = current;
                            }
                        }
                    }else{
                        minPrice = priceArray.getJSONObject(0).getDouble("min");
                        maxPrice = priceArray.getJSONObject(0).getDouble("max");
                    }


//                    gets promo image
//                    while there can be multiple promo images, only one is required
                    URL promoImageUrl = new URL(object.getJSONArray("images").getJSONObject(0).getString("url"));
                    HttpURLConnection connection = (HttpURLConnection) promoImageUrl.openConnection();
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    Bitmap promoImgae = null;
                    if (responseCode == 200) {
                        promoImgae = BitmapFactory.decodeStream(connection.getInputStream());
                    }
                    publishProgress(100);

//                builds the bitmap object
                    FileOutputStream outputStream = openFileOutput(promoImgae + ".png", Context.MODE_PRIVATE);
                    promoImgae.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    
                    eventList.add(new Event(name,startDate,tkUrl,minPrice,maxPrice,promoImgae));
                }//end of loop through events


            }catch(Exception e){}

            return "Done";
        }

        protected void onProgressUpdate(Integer... value) {

        }

        protected void onPostExecute(String s){

        }
    }
}