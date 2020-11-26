package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventDetails extends AppCompatActivity {


    private Bundle dataToPass;
    private Bitmap promoImage;
    private ImageView eventPromoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);


        TextView eventName = findViewById(R.id.e_eventName);
        TextView eventStartDate = findViewById(R.id.e_eventStartDate);
        TextView eventMinPrice = findViewById(R.id.e_eventMinPrice);
        TextView eventMaxPrice = findViewById(R.id.e_eventMaxPrice);

        Button eventGoToSiteBtn = findViewById(R.id.e_goToSiteBtn);

        eventPromoImage = findViewById(R.id.e_promoImage);


        dataToPass = getIntent().getExtras();
        eventName.setText(dataToPass.getString("name"));
        eventStartDate.setText(dataToPass.getString("startDate"));
        eventMinPrice.setText("Min price: "+String.valueOf(dataToPass.getDouble("priceMin")));
        eventMaxPrice.setText("Max price: "+String.valueOf(dataToPass.getDouble("priceMax")));
        eventGoToSiteBtn.setText(dataToPass.getString("url"));

        ImageQuery query = new ImageQuery();
        query.execute(dataToPass.getString("promoImage"));



        eventGoToSiteBtn.setOnClickListener((a)->{
            //TODO: alert dialogue
            //TODO: open browser with link
        });
    }

    private class ImageQuery extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    promoImage = null;
                    if (responseCode == 200) {
                        promoImage = BitmapFactory.decodeStream(connection.getInputStream());
                    }

//                builds the bitmap object
                    FileOutputStream outputStream = openFileOutput(promoImage + ".png", Context.MODE_PRIVATE);
                    promoImage.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();

            }catch(Exception e){
                Log.e("API: Error caught:", String.valueOf(e));
            }
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            eventPromoImage.setImageBitmap(promoImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                EventDetails.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}