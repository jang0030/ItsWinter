package mobilesdkdemo.rbbn.itswinter.event;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventDetails extends AppCompatActivity {

    //TODO: get save status from db, fill it in

    private Bundle dataToPass;
    private SQLiteDatabase db;
    private Long id;
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

        saveCb.setOnCheckedChangeListener((a,b)->{

            if(saveCb.isChecked()){
                EventSqlOpener dbOpener = new EventSqlOpener(this);
                db = dbOpener.getWritableDatabase();

                String name = dataToPass.getString("name");
                String date = dataToPass.getString("startDate");
                Double min = dataToPass.getDouble("priceMin");
                Double max = dataToPass.getDouble("priceMax");
                String url = dataToPass.getString("url");

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
                id = db.insert(EventSqlOpener.EVENT_TABLE_NAME,null, cValues);



            }else{
                saveCb.setChecked(false);
            }
        });

    }
}