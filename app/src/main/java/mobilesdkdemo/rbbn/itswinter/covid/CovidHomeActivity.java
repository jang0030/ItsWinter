package mobilesdkdemo.rbbn.itswinter.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mobilesdkdemo.rbbn.itswinter.MainActivity;
import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.AudioHomeActivity;
import mobilesdkdemo.rbbn.itswinter.audio.MyAlbumActivity;

/**
 * This class is the main page of Covid-19 Case Data
 * @author HYUNJU JANG
 * @version 1.00
 */

public class CovidHomeActivity extends AppCompatActivity {
    /**
     * Text Field: Country
     */
    EditText countryEditText;

    /**
     * Text Field: Date (From date)
     */
    EditText dateEditText;

    /**
     * To Date, 1 day after the selected from date
     */
    String toDate = "";

    /**
     * When the Covid home page starts, this method runs.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_home);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.c_home_title));
        actionBar.setDisplayHomeAsUpEnabled(true);

        countryEditText = findViewById(R.id.c_country_editTxt);
        dateEditText = findViewById(R.id.c_date_editTxt);

        dateEditText.setInputType(InputType.TYPE_NULL);

        DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final Calendar cal = Calendar.getInstance();

                final Date today = cal.getTime();

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);

                Date date = cal.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                toDate = format1.format(date);
                dateEditText.setText( toDate );
            }
        };

        dateEditText.setFocusable(false);
        dateEditText.setKeyListener(null);

        dateEditText.setOnClickListener(v-> {
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            // date picker dialog
            DatePickerDialog picker = new DatePickerDialog(CovidHomeActivity.this, datepickerListener
                    , year, month, day);

            picker.show();
        });

        Button searchButton = findViewById(R.id.c_search_button);

        searchButton.setOnClickListener(v-> {
            String country = countryEditText.getText().toString().trim();
            String fromDate = dateEditText.getText().toString().trim();

            if (country.equalsIgnoreCase("")) {
                // show message country field can't be empty
                Toast.makeText(CovidHomeActivity.this, getResources().getString(R.string.c_country_empty), Toast.LENGTH_LONG).show();
            }
            else if (fromDate.equalsIgnoreCase("")) {
                // show message country field can't be empty
                Toast.makeText(CovidHomeActivity.this, getResources().getString(R.string.c_date_empty), Toast.LENGTH_LONG).show();
            }
            else {
                final Calendar cal = Calendar.getInstance();
                final Date today = cal.getTime();

                // Call the web page, get information, and show it in ListView in new Activity
                // New Activity has progress bar
                Intent covidSearch = new Intent(CovidHomeActivity.this, CovidResultActivity.class);
                covidSearch.putExtra("Country", country);
                covidSearch.putExtra("FromDate", fromDate);
                covidSearch.putExtra("ToDate", toDate);
                startActivityForResult(covidSearch,77);
            }
        });
    }

    /**
     * Runs when option menu chosen
     * @param item selected menu item
     * @returns boolean
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                CovidHomeActivity.this.finish();
                break;
            case R.id.covid_home_help:
                String msg = getResources().getString(R.string.c_home_help);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.help))
                        .setMessage(msg)
                        .setPositiveButton("OK",(click,arg)->{})
                        .create().show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add covid home menu for Help
     * @param menu menu where menu item is to be added
     * @returns boolean true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.covid_home, menu);
        return true;
    }

}