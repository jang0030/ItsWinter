package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

import static mobilesdkdemo.rbbn.itswinter.event.EventSqlOpener.EVENT_COL_ID;
import static mobilesdkdemo.rbbn.itswinter.event.EventSqlOpener.EVENT_TABLE_NAME;

public class SavedEvents extends AppCompatActivity {

    /**SavedEvents.java
     * Zackery Brennan
     * 040952243
     *
     * Creates a list of saved Events, when clicked sends to EventDetails.java
     * */


    private ArrayList<Event> savedEvents = new ArrayList<>();
    private ListView savedList;
    private EventListAdapter eventAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);

        savedList = findViewById(R.id.e_savedList);
        savedList.setAdapter(eventAdapter = new EventListAdapter());

        savedEvents.clear();
        eventAdapter.notifyDataSetChanged();

        loadFromDb();

//        adds all the Event data to a bundle and send to EventDetails.java
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
            dataToPass.putString("apiId",savedEvents.get(pos).getApiId());

            Intent goToDetailsPage = new Intent(SavedEvents.this, EventDetails.class);
            goToDetailsPage.putExtras(dataToPass);
            startActivity(goToDetailsPage);

            savedEvents.clear();
            eventAdapter.notifyDataSetChanged();
        });

//        allows the user to unsave events from the list by holding the element
        savedList.setOnItemLongClickListener((p,b,pos,id)->{
            Event event = savedEvents.get(pos);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle(getString(R.string.e_alertTitleTrueString))
                        .setMessage(getString(R.string.e_alertMessageTrueString))
                        .setPositiveButton(getString(R.string.e_yesString), (click, args) -> {
                            event.setSaved(false);
                            EventResults.e_removeFav(event.getApiId());
                            removeFromDb(event);
                            savedEvents.remove(event);
                            eventAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton(getString(R.string.e_noString), (click, args)->{ return; })
                        .create().show();
            return true;
        });
    }

//    removes the event from the database
    private void removeFromDb(Event event){
        db.delete(EVENT_TABLE_NAME,EVENT_COL_ID+"=?",new String[]{Long.toString(event.getId())});
    }


//    goes through the database and adds events to the ArrayList
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                SavedEvents.this.finish();
                break;
            case R.id.e_eventHomeMenu:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle(getString(R.string.e_homeHelpMessageTitle))
                        .setMessage(getString(R.string.e_ResultsHelpMessageBody))
                        .setPositiveButton((R.string.e_yesString),(click,args)->{})
                        .create().show();
                break;
            case R.id.e_developerInfoMenu:
                AlertDialog.Builder alertDialogInfo = new AlertDialog.Builder(this);
                alertDialogInfo.setTitle(getString(R.string.e_developerInfo))
                        .setMessage("SavedEvents.java \nVersion Number: 1.0 \nAuthor: Zackery Brennan")
                        .setPositiveButton((R.string.e_yesString),(click,args)->{})
                        .create().show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_home_menu, menu);
        return true;
    }
}