package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventDetails extends AppCompatActivity {

    /**EventDetails.java
     * Zackery Brennan
     * 040952243
     *
     * This activity will show all the event details, and allow the user to save/unsave the event
     * and go to the Ticket Master website
     * */

    private Bundle dataToPass;
    private Bitmap promoImage;
    private ImageView eventPromoImage;
    private CheckBox eventSaveCb;
    private boolean saved;
    private String apiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        gets reference to elements in the view
        TextView eventName = findViewById(R.id.e_eventName);
        TextView eventStartDate = findViewById(R.id.e_eventStartDate);
        TextView eventMinPrice = findViewById(R.id.e_eventMinPrice);
        TextView eventMaxPrice = findViewById(R.id.e_eventMaxPrice);
        Button eventGoToSiteBtn = findViewById(R.id.e_goToSiteBtn);
        eventSaveCb = findViewById(R.id.e_saveCheckBox);
        eventPromoImage = findViewById(R.id.e_promoImage);

//        gets the data sent from EventDetails.java and sets the elements to the correct values
        dataToPass = getIntent().getExtras();
        eventName.setText(dataToPass.getString("name"));
        eventStartDate.setText(dataToPass.getString("startDate"));
        eventMinPrice.setText("Min price: "+String.valueOf(dataToPass.getDouble("priceMin")));
        eventMaxPrice.setText("Max price: "+String.valueOf(dataToPass.getDouble("priceMax")));
        eventGoToSiteBtn.setText(getString(R.string.e_goToTicketMasterWebsiteString));
//        calls for the promo image of the event to be retrieved and set
        ImageQuery query = new ImageQuery();
        query.execute(dataToPass.getString("promoImage"));
        saved = dataToPass.getBoolean("saved");

        setCheckBox();


//        used to save/unsave the event
        eventSaveCb.setOnCheckedChangeListener((a,args)->{
            apiId = dataToPass.getString("apiId");
//            checks if the event is saved and changes it depending on the switch state
            if(saved){
                EventResults.e_removeFav(apiId);
                Snackbar.make(eventSaveCb, getString(R.string.e_snackBarTrueString), Snackbar.LENGTH_SHORT).show();
            }else{
                EventResults.e_addFav(apiId);
                Snackbar.make(eventSaveCb, getString(R.string.e_snackBarFalseString), Snackbar.LENGTH_SHORT).show();
            }
            saved = !saved;
            setCheckBox();
        });

//        will create an alert and ask to open the browser, taking the user to the Ticket Master page for this event
        eventGoToSiteBtn.setOnClickListener((a)->{
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle(getString(R.string.e_goToSiteAlertTitle))
                    .setMessage(getString(R.string.e_goToSiteAlertMessage))
                    .setPositiveButton(getString(R.string.e_yesString),(click, args)->{
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataToPass.getString("url")));
                        startActivity(browserIntent);
                    })
                    .setNegativeButton(getString(R.string.e_noString),(click,args)->{return;})
                    .create().show();
        });
    }

    private void setCheckBox(){
        if(saved){
            eventSaveCb.setChecked(true);
            eventSaveCb.setHint(R.string.e_saveCbHintTrue);
        }else{
            eventSaveCb.setChecked(false);
            eventSaveCb.setHint(R.string.e_saveCbHintFalse);
        }
    }



    private class ImageQuery extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... args) {
//            gets the promo image
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