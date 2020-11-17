package mobilesdkdemo.rbbn.itswinter.covid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This class is the search result page
 * @author HYUNJU JANG
 * @version 1.00
 */
public class CovidResultActivity extends AppCompatActivity {
    int counter = 0;
    CovidQuery req = null;
    String result = null;

    /**
     * Progress Bar
     */
    ProgressBar progress;

    /**
     * Adapter for the list
     */
    MyListAdapter myAdapter;

    /**
     * Holds the data shown in the list
     */
    private ArrayList<Message> elements = new ArrayList<>( );
    private ArrayList<Message> elementsOriginal = new ArrayList<>( );

    /**
     * Holds the detailed data to show up when the
     * list item is long-clicked
     */
    private Map<Integer, String> detailElements = new HashMap<>( );

    /**
     * When the result page starts, this method runs.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_result);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Covid Search Result");

        Switch swc = findViewById(R.id.c_onOff);
        //   swc.setOnCheckedChangeListner(new CompoundButton.OnCheckedChangeListener() {
        //       public void onCheckedChanged (CompoundButton ib,boolean isChecked ){
        swc.setOnCheckedChangeListener((cb,isChecked) -> {
            if (isChecked) {

                Collections.sort(elements);

                myAdapter.notifyDataSetChanged();

                Snackbar.make(swc, "The sort is now on.", BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Undo", click -> {
                            cb.setChecked(!isChecked);
                        }).show();
            } else {
                elements.clear();

                for (int i = 0; i < elementsOriginal.size(); i++) {
                    elements.add(elementsOriginal.get(i));
                }

                myAdapter.notifyDataSetChanged();

                Snackbar.make(swc, "The sort is now off.", BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Undo", click -> cb.setChecked(!isChecked)).show();
            }
        });

        Intent fromMain = getIntent();

        //covidSearch.putExtra("Country", country);
        //covidSearch.putExtra("FromDate", fromDate);
        //covidSearch.putExtra("ToDate", toDate);

        String country = fromMain.getStringExtra("Country");
        String fromDate = fromMain.getStringExtra("FromDate");
        String toDate = fromMain.getStringExtra("ToDate");

        fromDate = fromDate + "T00:00:00Z";
        toDate = toDate + "T23:59:59Z";

        ListView myList = findViewById(R.id.c_ListView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        myList.setOnItemLongClickListener((parent,view,pos,id)->{
            Integer mID = elements.get(pos).getId();
            String detail = detailElements.get(mID);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Details")
                    .setMessage(detail)
                    .setPositiveButton("OK",(click,arg)->{})
                    .create().show();
            return true;
        });

        progress = findViewById(R.id.c_progressBar);

        req = new CovidQuery(); //creates a background thread
//        req.execute("https://api.covid19api.com/country/CANADA/status/confirmed/live?from=2020-10-14T00:00:00Z&to=2020-10-15T00:00:00Z");
        req.execute("https://api.covid19api.com/country/" + country +
                "/status/confirmed/live?from=" + fromDate +
                "&to=" + toDate);

    }

    /**
     * This class is for connecting to the web page and getting the result
     * @author HYUNJU JANG
     * @version 1.00
     */

     private class CovidQuery extends AsyncTask<String, Integer, String> {

        /**
         * connects to web page, gets results, and saves them to elements
         * and detailElements
         * @param args URL string
         * @returns confirmation
         */
        protected String doInBackground(String... args) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                int progressVal = 0;

                publishProgress(progressVal);

                // String URL = URLEncoder.encode(args[0],"UTF-8");
                //String encoded = args[0] + URLEncoder.encode(args[1], "UTF-8");

                URL url = new URL(args[0]);

                //open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                result = sb.toString(); //result is the whole string

                if (result != null && !result.trim().equalsIgnoreCase("[]")) {
                    JSONArray array = new JSONArray(result);

                    int progressStep = 100 / array.length();

                    if ( progressStep == 0 ) {
                        progressStep = 1;
                    }

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject covidReport = array.getJSONObject(i);

                        String province = covidReport.getString("Province");
                        String city = covidReport.getString("City");
                        String lat = covidReport.getString("Lat");
                        String lon = covidReport.getString("Lon");

                        int cases = covidReport.getInt("Cases");

                        if (cases > 0) {
                            counter++;
                            Message msg = new Message(counter, province, cases);

                            elements.add(msg);
                            elementsOriginal.add(msg);

                            String detailed = "Latitude = " + lat + "\nLongitude: " + lon;

                            detailElements.put(msg.getId(), detailed);
                        }

                        progressVal = progressVal + progressStep;

                        if (progressVal < 100) {
                            publishProgress(progressVal);
                        }
                    }

                    //get the double associated with "value"
                    //                   double uvRating = covidReport.getDouble("value");

                    //Log.i("WeatherForecast", "WF: The uv is now: " + uvRating);

                    //UV = "" + uvRating;
                }
                reader.close();

                urlConnection.disconnect();

                publishProgress(100);
            } catch (Exception e) {
                Log.e("WeatherForecast", "WF: Error = " + e.getMessage());
            }
            finally {
                try {
                    reader.close();
                }
                catch (Exception e) {
                }

                try {
                    urlConnection.disconnect();
                }
                catch (Exception e) {
                }
            }
            return "Done";
        }

        /**
         * Called when progress bar is updated
         * sets progress bar to be visible
         * @param value progress bar value
         */
        public void onProgressUpdate(Integer ... value)
        {
            //Log.i("Calling back for data", "Integer= " +args[0]);
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);

        }

        /**
         * Called after the doInBackground() completes,
         * Sets the progress bar invisible, and updates the list
         * @param fromDoInBackground the confirmation message
         */
        public void onPostExecute(String fromDoInBackground)
        {
            //Log.i("HTTP", fromDoInBackground);
            progress.setVisibility(View.INVISIBLE);
            myAdapter.notifyDataSetChanged();

            if (result != null &&
                    result.trim().equalsIgnoreCase("[]")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CovidResultActivity.this);
                alertDialogBuilder.setTitle("Empty Data returned")
                        .setMessage("Please select another day.")
                        .setPositiveButton("OK", (click, arg) -> {
                        })
                        .create().show();
            }
        }
    }

    /**
     * This class is utility for the list
     * @author HYUNJU JANG
     * @version 1.00
     */
    class MyListAdapter extends BaseAdapter {

        /**
         * Determines if the list view item is enabled
         * @param position row index
         * @returns boolean true if enabled, false otherwise
         */
        public boolean isEnabled(int position) {
            return true;    /**
     * This class is utility for the list
     * @author HYUNJU JANG
     * @version 1.00
     */

        }

        /**
         * The size of the list
         * @returns int size
         */
        public int getCount() {
            return elements.size();
        }

        /**
         * Returns the data of the list item
         * @param position the row index
         * @returns Object list item
         */
        public Object getItem(int position) {
            return elements.get(position);
        }

        /**
         * Returns the index of the list data
         * @param position the row index
         * @returns long position
         */
        public long getItemId(int position) {
            return ((Message) getItem(position)).getId();
        }

        /**
         * Returns the graphical representation of the list item
         * @param position the row index
         * @param old previous view
         * @param parent view's parent
         * @returns View new view
         */
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            newView = inflater.inflate(R.layout.covid_list_layout, parent, false);

            Message msg = (Message) getItem(position);
            String prov = msg.getProvince();
            int num = msg.getCases();

            TextView txtView = newView.findViewById( R.id.c_provinceGoesHere );
            txtView.setText(prov);

            TextView caseView = newView.findViewById( R.id.c_caseNumGoesHere );
            caseView.setText("" + num);

            return newView;
        }
    }

    /**
     * This class is content of the list
     * @author HYUNJU JANG
     * @version 1.00
     */
    public class Message implements Comparable<Message> {
        String province = "";
        Integer cases;
        Integer id;

        /**
         * Constructor:
         * @param mid ID
         * @param prov province name
         * @param num case number
         */
        public Message(int mid, String prov, int num) {
            setId(mid);
            setProvince(prov);
            setCases(num);
        }

        /**
         * Returns the message ID
         * @returns Integer ID
         */
        public Integer getId() {
            return id;
        }

        /**
         * set ID
         * @param id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * get province name
         * @returns province
         */
        public String getProvince() {
            return province;
        }

        /**
         * Set province name
         * @param province
         */
        public void setProvince(String province) {
            this.province = province;
        }

        /**
         * Get covid cases in the province
         * @returns cases
         */
        public Integer getCases() {
            return cases;
        }

        /**
         * Set cases
         * @param cases
         */
        public void setCases(int cases) {
            this.cases = cases;
        }

        /**
         * Comparator implmentation for sorting
         * @returns int -1, 0, or 1
         */
        public int compareTo(Message simpson) {
            return this.getCases().compareTo(simpson.getCases());
        }
    }
}