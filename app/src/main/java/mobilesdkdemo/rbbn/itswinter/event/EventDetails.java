package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

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
                ContentValues cValues = new ContentValues();
                cValues.put(EventSqlOpener.EVENT_COL_NAME, dataToPass.getString("name") );
                cValues.put(EventSqlOpener.EVENT_COL_START_DATE,dataToPass.getString("startDate"));
                cValues.put(EventSqlOpener.EVENT_COL_PRICE_MIN, String.valueOf(dataToPass.getDouble("priceMin")));
                cValues.put(EventSqlOpener.EVENT_COL_PRICE_MAX, String.valueOf(dataToPass.getDouble("priceMax")));
                cValues.put(EventSqlOpener.EVENT_COL_TKURL, dataToPass.getString("url"));
                //TODO: Figure out how to store image in db
                //store image link and grab it from URL in this screen? Would also reduce inital search speed
//                cValues.put(EventSqlOpener.EVENT_COL_PROMO_IMAGE, );
                cValues.put(EventSqlOpener.EVENT_COL_SAVED, true);
                id = db.insert(EventSqlOpener.EVENT_TABLE_NAME,null, cValues);

//                Snackbar.make(saveCb, this.getString(R.string.e_revertString),Snackbar.LENGTH_INDEFINITE)
//                        .setAction(this.getString(R.string.e_removeString), click->{
//                            saveCb.setChecked(false);
//                            db.delete(EventSqlOpener.EVENT_TABLE_NAME, "id=?", new String[]{Long.toString(id)});
//                        }).show();
            }else{
//                new AlertDialog.Builder(this)
//                        .setTitle("Sorry")
//                        .setMessage("At this time, events can only unsaved using the snack bar after saving it, or from the saved screen")
//                        .setPositiveButton("yes",null)
//                        .show();
//                saveCb.setChecked(true);
            }
        });

    }
}