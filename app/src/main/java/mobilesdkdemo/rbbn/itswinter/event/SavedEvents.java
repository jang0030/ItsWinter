package mobilesdkdemo.rbbn.itswinter.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

public class SavedEvents extends AppCompatActivity {

    ArrayList<Event> savedEvents = new ArrayList<>();
    private ListView savedList;
    private EventListAdapter eventAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        savedList = findViewById(R.id.e_savedList);
        savedList.setAdapter(eventAdapter = new EventListAdapter());

        loadFromDb();

        savedList.setOnItemClickListener((p,b,pos,id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString("name",savedEvents.get(pos).getName());
            dataToPass.putString("startDate",savedEvents.get(pos).getStartDate());
            dataToPass.putString("url",savedEvents.get(pos).getTkUrl());
            dataToPass.putDouble("priceMax",savedEvents.get(pos).getPriceMax());
            dataToPass.putDouble("priceMin",savedEvents.get(pos).getPriceMin());
            dataToPass.putString("promoImage", String.valueOf(savedEvents.get(pos).getPromoImage()));
            dataToPass.putBoolean("saved",savedEvents.get(pos).isSaved());
            dataToPass.putLong("dbId",savedEvents.get(pos).getId());

            Intent goToDetailsPage = new Intent(SavedEvents.this, EventDetails.class);
            goToDetailsPage.putExtras(dataToPass);
            startActivity(goToDetailsPage);
        });

    }

    private void loadFromDb() {
        EventSqlOpener dbOpener = new EventSqlOpener(this);
        db = dbOpener.getReadableDatabase();

        String [] columns = {EventSqlOpener.EVENT_COL_NAME,EventSqlOpener.EVENT_COL_START_DATE,EventSqlOpener.EVENT_COL_TKURL,
                EventSqlOpener.EVENT_COL_PRICE_MIN,EventSqlOpener.EVENT_COL_PRICE_MAX,EventSqlOpener.EVENT_COL_PROMO_IMAGE,EventSqlOpener.EVENT_COL_SAVED,
                EventSqlOpener.EVENT_COL_APIID, EventSqlOpener.EVENT_COL_ID};
        Cursor results = db.query(false, EventSqlOpener.EVENT_TABLE_NAME, columns, null, null, null, null, null, null);

        int nameColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_NAME);
        int dateColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_START_DATE);
        int tkUrlColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_TKURL);
        int minColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_PRICE_MIN);
        int maxColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_PRICE_MAX);
        int imageColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_PROMO_IMAGE);
        int savedColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_SAVED);
        int apiIdColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_APIID);
        int dbIdColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_ID);

        while(results.moveToNext()){
//            TODO: GRAB ALL THE DB INFOR AND ADD IT TO THE LIST
            String name = results.getString(nameColumn);
            String date = results.getString(dateColumn);
            String tkUrl = results.getString(tkUrlColumn);
            Double min = results.getDouble(minColumn);
            Double max = results.getDouble(maxColumn);
            String image = results.getString(imageColumn);
            Boolean saved = results.getInt(savedColumn) > 0;
            String apiId = results.getString(apiIdColumn);
            Long dbId = results.getLong(dbIdColumn);

            savedEvents.add(new Event(name,date,tkUrl,min,max,image,saved,dbId,apiId));
        }
    }


    //    handles adding the name of the event to the ListView
    private class EventListAdapter extends BaseAdapter {

        @Override
        public int getCount() { return savedEvents.size(); }

        @Override
        public Object getItem(int position) { return savedEvents.get(position); }

        @Override
        public long getItemId(int position) { return savedEvents.get(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView =convertView;

            if(newView == null){
                newView = inflater.inflate(R.layout.activity_event_list_view_layout,parent,false);
                TextView tview = newView.findViewById(R.id.e_resultsListText);
                tview.setText(savedEvents.get(position).getName());
            }
            return newView;
        }
    }
}