package mobilesdkdemo.rbbn.itswinter.event;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

import static mobilesdkdemo.rbbn.itswinter.event.EventSqlOpener.EVENT_COL_ID;
import static mobilesdkdemo.rbbn.itswinter.event.EventSqlOpener.EVENT_TABLE_NAME;

public class EventResults extends AppCompatActivity {

    private ArrayList<Event> eventList = new ArrayList();
    private String name,startDate,tkUrl;
    private double minPrice,maxPrice;
    private ListView resultList;
    private EventListAdapter eventAdapter;
    private ProgressBar progressBar;

    private SQLiteDatabase db;
    private EventSqlOpener dbOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_results);

        dbOpener = new EventSqlOpener(this);
        db = dbOpener.getWritableDatabase();

        resultList = findViewById(R.id.e_searchReturns);
        resultList.setAdapter(eventAdapter = new EventListAdapter());

        Intent searchTerms = getIntent();

        String city = searchTerms.getStringExtra("city").toLowerCase();
        search(city,searchTerms.getStringExtra("radius"));


        resultList.setOnItemClickListener((p,b,pos,id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString("name",eventList.get(pos).getName());
            dataToPass.putString("startDate",eventList.get(pos).getStartDate());
            dataToPass.putString("url",eventList.get(pos).getTkUrl());
            dataToPass.putDouble("priceMax",eventList.get(pos).getPriceMax());
            dataToPass.putDouble("priceMin",eventList.get(pos).getPriceMin());
            dataToPass.putParcelable("promoImage",eventList.get(pos).getPromoImage());
            dataToPass.putBoolean("saved",eventList.get(pos).isSaved());
            dataToPass.putLong("dbId",eventList.get(pos).getId());

            Intent goToDetailsPage = new Intent(EventResults.this, EventDetails.class);
            goToDetailsPage.putExtras(dataToPass);
            startActivity(goToDetailsPage);
        });

        resultList.setOnItemLongClickListener((p,b,pos,id)->{
            Event event = eventList.get(pos);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            if(event.isSaved()) {
                alertBuilder.setTitle(getString(R.string.e_alertTitleTrueString))
                        .setMessage(getString(R.string.e_alertMessageTrueString))
                        .setPositiveButton(getString(R.string.e_yesString), (click, args) -> {
                            event.setSaved(false);
                            removeFromDb(event);
                        })
                        .setNegativeButton(getString(R.string.e_noString), (click, args)->{ return; })
                        .create().show();
            }else{
                alertBuilder.setTitle(getString(R.string.e_alertTitleFalseString))
                        .setMessage(getString(R.string.e_alertMessageFalseString))
                        .setPositiveButton(getString(R.string.e_yesString), (click, args) -> {
                            event.setSaved(true);
                            saveToDb(event);
                        })
                        .setNegativeButton(getString(R.string.e_noString), (click, args)->{ return; })
                        .create().show();
            }
            return true;
        });

    }

    private void saveToDb(Event event){

        String name = event.getName();
        String date = event.getStartDate();
        Double min = event.getPriceMin();
        Double max = event.getPriceMax();
        String url = event.getTkUrl();

        ContentValues cValues = new ContentValues();
        cValues.put(EventSqlOpener.EVENT_COL_NAME, name );
        cValues.put(EventSqlOpener.EVENT_COL_START_DATE, date);
        cValues.put(EventSqlOpener.EVENT_COL_PRICE_MIN, String.valueOf(min));
        cValues.put(EventSqlOpener.EVENT_COL_PRICE_MAX, String.valueOf(max));
        cValues.put(EventSqlOpener.EVENT_COL_TKURL, url);
        //TODO: Figure out how to store image in db
        //store image link and grab it from URL in this screen? Would also reduce inital search speed
//                cValues.put(EventSqlOpener.EVENT_COL_PROMO_IMAGE, );
        cValues.put(EventSqlOpener.EVENT_COL_SAVED, true);
        Long id = db.insert(EVENT_TABLE_NAME,null, cValues);
        event.setId(id);
    }

    private void removeFromDb(Event event){
        db.delete(EVENT_TABLE_NAME,EVENT_COL_ID+"=?",new String[]{Long.toString(event.getId())});
    }



    //    launches EventQuery
    private void search(String citySearchTerm, String radiusSearchTerm){
        EventQuery query = new EventQuery();
        String apiKey = "KiOshiJsVO1WxmGWXYxpwy4Yxd7Cu6r1";
        /*test link
            https://app.ticketmaster.com/discovery/v2/events.json?apikey=KiOshiJsVO1WxmGWXYxpwy4Yxd7Cu6r1&city=ottawa&radius=100
        */
        query.execute("https://app.ticketmaster.com/discovery/v2/events.json?apikey="+apiKey+"&city="+citySearchTerm+"&"+radiusSearchTerm+"");
    }

    private class EventQuery extends AsyncTask<String,Integer,String> {

        protected String doInBackground(String...args) {
            try{
                publishProgress(10);
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                publishProgress(25);
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
                    JSONObject object = events.getJSONObject("_embedded").getJSONArray("events").getJSONObject(i);

//                    gets name, start date and Ticket Master URL
                    name = object.getString("name");
                    startDate = object.getJSONObject("dates").getJSONObject("start").getString("localDate");
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

//                builds the bitmap object
                    FileOutputStream outputStream = openFileOutput(promoImgae + ".png", Context.MODE_PRIVATE);
                    promoImgae.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    eventList.add(new Event(name,startDate,tkUrl,minPrice,maxPrice,promoImgae,false));
                }//end of loop through events


            }catch(Exception e){
                Log.e("API: Error caught:", String.valueOf(e));
            }


            publishProgress(100);
            return "Done";
        }

        protected void onProgressUpdate(Integer... value) {
            progressBar = findViewById(R.id.e_progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        protected void onPostExecute(String s){
            progressBar.setVisibility(View.INVISIBLE);
            eventAdapter.notifyDataSetChanged();
        }
    }

    //    handles adding the name of the event to the ListView
    private class EventListAdapter extends BaseAdapter {

        @Override
        public int getCount() { return eventList.size(); }

        @Override
        public Object getItem(int position) { return eventList.get(position); }

        @Override
        public long getItemId(int position) { return eventList.get(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView =convertView;

            if(newView == null){
                newView = inflater.inflate(R.layout.activity_event_list_view_layout,parent,false);
                TextView tview = newView.findViewById(R.id.e_resultsListText);
                tview.setText(eventList.get(position).getName());
            }
            return newView;
        }
    }
}