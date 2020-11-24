package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventDetails extends AppCompatActivity {

    //TODO: get save status from db, fill it in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView eventName = findViewById(R.id.e_eventName);
        TextView eventStartDate = findViewById(R.id.e_eventStartDate);
        TextView eventMinPrice = findViewById(R.id.e_eventMinPrice);
        TextView eventMaxPrice = findViewById(R.id.e_eventMaxPrice);

        Button eventGoToSiteBtn = findViewById(R.id.e_goToSiteBtn);
        CheckBox saveCb = findViewById(R.id.e_saveCheckBox);

        ImageView eventPromoImage = findViewById(R.id.e_promoImage);

        Bundle dataToPass = getIntent().getExtras();
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
        saveCb.setOnClickListener((a)->{
            //TODO: Check if checked (added already)
            //TODO: add to db if not checked, remove if is
        });

    }
}