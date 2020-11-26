package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventDetails extends AppCompatActivity {


    private Bundle dataToPass;

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

        ImageView eventPromoImage = findViewById(R.id.e_promoImage);


        dataToPass = getIntent().getExtras();
        eventName.setText(dataToPass.getString("name"));
        eventStartDate.setText(dataToPass.getString("startDate"));
        eventMinPrice.setText("Min price: "+String.valueOf(dataToPass.getDouble("priceMin")));
        eventMaxPrice.setText("Max price: "+String.valueOf(dataToPass.getDouble("priceMax")));
        eventGoToSiteBtn.setText(dataToPass.getString("url"));
        Bitmap promoImage = dataToPass.getParcelable("promoImage");
        eventPromoImage.setImageBitmap(promoImage);



        eventGoToSiteBtn.setOnClickListener((a)->{
            //TODO: alert dialogue
            //TODO: open browser with link
        });
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