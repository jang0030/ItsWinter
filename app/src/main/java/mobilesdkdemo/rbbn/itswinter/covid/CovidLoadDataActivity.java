package mobilesdkdemo.rbbn.itswinter.covid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This class is for listing searches when Load menu is selected
 * @author HYUNJU JANG
 * @version 1.00
 */
public class CovidLoadDataActivity extends AppCompatActivity {
    private ArrayList<SavedData> savedDatas = new ArrayList<>( );

    MyListAdapter myAdapter;

    public static final String SAVE_ID = "SAVE_ID";

    /**
     * When this activity starts, this method runs.
     * shows list of saved data
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_load_data);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.c_saved_list));

        ListView myList = findViewById(R.id.c_Load_ListView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        SQLiteDatabase covidDB = openOrCreateDatabase("CovidDB", MODE_PRIVATE, null);

        covidDB.execSQL("CREATE TABLE IF NOT EXISTS COVIDCASE(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, COUNTRY VARCHAR(50), DATE VARCHAR(50), " +
                "PROVICE VARCHAR(50), CASES INT, SAVEID VARCHAR(50));");

        Cursor resultSet = covidDB.rawQuery("Select distinct SAVEID, COUNTRY, DATE from COVIDCASE", null);

        if (resultSet != null) {
            resultSet.moveToFirst();

            while (resultSet.isAfterLast() == false) {
                String saveID = resultSet.getString(0);
                String country = resultSet.getString(1);
                String date = resultSet.getString(2);

                long savedTimeMS = Long.parseLong(saveID);

                SavedData savedData = new SavedData(saveID, country, date);

                savedDatas.add(savedData);

                resultSet.moveToNext();
            }

            myAdapter.notifyDataSetChanged();
        }

        myList.setOnItemLongClickListener((parent,view,pos,id)-> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.c_delete_title))
                    .setMessage(getResources().getString(R.string.c_delete_msg) + " " + pos)
                    .setPositiveButton(getResources().getString(R.string.c_delete_yes),(click,arg)->{
                        // before deleting from elements, delete message from database
                        SavedData savedData = savedDatas.get(pos);
                        deleteSavedData(savedData);

                        savedDatas.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getResources().getString(R.string.c_delete_no),(click,arg)->{})
                    .create().show();
            return true;
        });

        myList.setOnItemClickListener((list, view, pos, id)->{
            SavedData savedData = savedDatas.get(pos);

            //Create a bundle to pass data to the new fragment
            Bundle dataToPass = new Bundle();
            dataToPass.putString(SAVE_ID, savedData.getSavedID());

            Intent nextActivity = new Intent(CovidLoadDataActivity.this, CovidSavedResultActivity.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivity(nextActivity); //make the transition
        });
    }

    /**
     * Deletes the selected saved data
     * @param savedData
     */

    public void deleteSavedData(SavedData savedData) {
        SQLiteDatabase covidDB = openOrCreateDatabase("CovidDB", MODE_PRIVATE, null);

        String param[] = new String[] {savedData.getSavedID()};
        covidDB.execSQL("DELETE FROM COVIDCASE WHERE SAVEID = ?", param);
    }


    /**
     * This class is for data shown in the item in the list
     * @author HYUNJU JANG
     * @version 1.00
     */
    class SavedData {
        String savedID = "";
        String country = "";
        String date = "";

        public SavedData(String savedID, String country, String date) {
            this.savedID = savedID;
            this.country = country;
            this.date = date;
        }

        public String getSavedID() {
            return savedID;
        }

        public String getCountry() {
            return country;
        }

        public String getDate() {
            return date;
        }
    }

    /**
     * This class is utility for the list
     * @author HYUNJU JANG
     * @version 1.00
     */
    class MyListAdapter extends BaseAdapter {

        /**
         * Determines if the list view item is enabled
         * @param position row index
         * @returns boolean true if enabled, false otherwise
         */
        public boolean isEnabled(int position) {
            return true;    /**
             * This class is utility for the list
             * @author HYUNJU JANG
             * @version 1.00
             */

        }

        /**
         * The size of the list
         * @returns int size
         */
        public int getCount() {
            return savedDatas.size();
        }

        /**
         * Returns the data of the list item
         * @param position the row index
         * @returns Object list item
         */
        public Object getItem(int position) {
            return savedDatas.get(position);
        }

        /**
         * Returns the index of the list data
         * @param position the row index
         * @returns long position
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Returns the graphical representation of the list item
         * @param position the row index
         * @param old previous view
         * @param parent view's parent
         * @returns View new view
         */
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            newView = inflater.inflate(R.layout.covid_saved_list_layout, parent, false);

            SavedData savedData = (SavedData) getItem(position);

            String saveID = savedData.getSavedID();
            Long saveIDLong = Long.parseLong(saveID);

            SimpleDateFormat FORMATTER = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );
            Date savedDate = new Date(saveIDLong);

            String timeStr = FORMATTER.format(savedDate);

            TextView txtView1 = newView.findViewById( R.id.c_saveIDGoesHere );
            txtView1.setText("Saved at: " + timeStr);

            String country = savedData.getCountry();
            TextView txtView2 = newView.findViewById( R.id.c_savedCountryGoesHere );
            txtView2.setText("Country: " + country);

            String date = savedData.getDate();
            TextView txtView3 = newView.findViewById( R.id.c_savedDateGoesHere );
            txtView3.setText("Date: " + date);

            return newView;
        }
    }


    public void showResult() {
//        Intent covidLoadData = new Intent(CovidResultActivity.this, CovidLoadDataActivity.class);
//        covidLoadData.putExtra("Country", countryToSave);
//        covidLoadData.putExtra("Date", dateToSave);
//        startActivityForResult(covidLoadData,77);
    }
}