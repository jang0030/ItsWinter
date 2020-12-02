package mobilesdkdemo.rbbn.itswinter.covid;

import androidx.appcompat.app.ActionBar;
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
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This class is for showing saved result.
 * @author HYUNJU JANG
 * @version 1.00
 */

public class CovidSavedResultActivity extends AppCompatActivity {

    /**
     * country for the saved data
     */
    String countryStr = "";

    /**
     * date for the saved data
     */
    String dateStr = "";

    /**
     * province and cases for the saved data
     */
    List<Message> elements = new ArrayList<Message>();

    /**
     * Adapter for the list
     */
    MyListAdapter myAdapter;

    /**
     * When this activity starts, this method runs.
     * shows the list of covid data that was saved
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_saved_result);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.c_saved_result));

        Intent fromMain = getIntent();

        String saveID = fromMain.getStringExtra(CovidLoadDataActivity.SAVE_ID);

        ListView myList = findViewById(R.id.c_saved_ListView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        SQLiteDatabase covidDB = openOrCreateDatabase("CovidDB", MODE_PRIVATE, null);

        String param[] = new String[] {saveID};
        Cursor resultSet = covidDB.rawQuery("Select COUNTRY, DATE, PROVICE, CASES from COVIDCASE where SAVEID = ?", param);

        if (resultSet != null) {
            resultSet.moveToFirst();

            while (resultSet.isAfterLast() == false) {
                String country = resultSet.getString(0);
                String date = resultSet.getString(1);

                countryStr = country;
                dateStr = date;

                String province = resultSet.getString(2);
                int cases = resultSet.getInt(3);

                Message msg = new Message(province, cases);
                elements.add(msg);

                resultSet.moveToNext();
            }

            myAdapter.notifyDataSetChanged();

            TextView txtView1 = findViewById( R.id.c_country_saved_result );
            txtView1.setText(countryStr);

            TextView txtView2 = findViewById( R.id.c_date_saved_result );
            txtView2.setText(dateStr);
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
            return elements.size();
        }

        /**
         * Returns the data of the list item
         * @param position the row index
         * @returns Object list item
         */
        public Object getItem(int position) {
            return elements.get(position);
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
         * @return View new view
         */
        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            newView = inflater.inflate(R.layout.covid_list_layout, parent, false);

            Message msg = (Message) getItem(position);
            String prov = msg.getProvince();
            int num = msg.getCases();

            TextView txtView = newView.findViewById( R.id.c_provinceGoesHere );
            txtView.setText(prov);

            TextView caseView = newView.findViewById( R.id.c_caseNumGoesHere );
            caseView.setText("" + num);

            return newView;
        }
    }

    /**
     * This class is content of the list
     * @author HYUNJU JANG
     * @version 1.00
     */
    public class Message {
        String province = "";
        Integer cases;

        public Message(String province, Integer cases) {
            this.province = province;
            this.cases = cases;
        }

        public String getProvince() {
            return province;
        }

        public Integer getCases() {
            return cases;
        }
    }

}