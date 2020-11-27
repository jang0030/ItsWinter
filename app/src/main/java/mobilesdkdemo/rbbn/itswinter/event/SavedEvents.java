package mobilesdkdemo.rbbn.itswinter.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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


        EventSqlOpener dbOpener = new EventSqlOpener(this);
        db = dbOpener.getReadableDatabase();

        String [] columns = {EventSqlOpener.EVENT_COL_NAME};
        Cursor results = db.query(false, EventSqlOpener.EVENT_TABLE_NAME, columns, null, null, null, null, null, null);

        int nameColumn = results.getColumnIndex(EventSqlOpener.EVENT_COL_NAME);

        while(results.moveToNext()){
//            TODO: GRAB ALL THE DB INFOR AND ADD IT TO THE LIST
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