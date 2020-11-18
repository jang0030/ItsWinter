package mobilesdkdemo.rbbn.itswinter.event;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

public class eventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView eventName = findViewById(R.id.e_eventName);
        TextView eventStartDate = findViewById(R.id.e_eventStartDate);
        TextView eventMinPrice = findViewById(R.id.e_eventMinPrice);
        TextView eventMaxPrice = findViewById(R.id.e_eventMaxPrice);
        Button eventGoToSite = findViewById(R.id.e_goToSiteBtn);
        ImageView eventPromoImage = findViewById(R.id.e_promoImage);
        Button eventGoToSiteBtn = findViewById(R.id.e_goToSiteBtn);

        Bundle dataToPass = getIntent().getExtras();
        eventName.setText(dataToPass.getString("name"));
        eventStartDate.setText(dataToPass.getString("startDate"));
        eventMinPrice.setText("Min price: "+String.valueOf(dataToPass.getDouble("priceMin")));
        eventMaxPrice.setText("Max price: "+String.valueOf(dataToPass.getDouble("priceMax")));
        eventGoToSite.setText(dataToPass.getString("url"));
        Bitmap promoImage = dataToPass.getParcelable("promoImage");
        eventPromoImage.setImageBitmap(promoImage);


    }
}