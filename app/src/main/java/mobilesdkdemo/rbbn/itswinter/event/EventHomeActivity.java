package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventHomeActivity extends AppCompatActivity {

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



            }catch(Exception e){}

            return "Done";
        }

        protected void onProgressUpdate(Integer... value) {

        }

        protected void onPostExecute(String s){

        }
    }
}