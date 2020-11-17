package mobilesdkdemo.rbbn.itswinter.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mobilesdkdemo.rbbn.itswinter.R;

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
        actionBar.setTitle("Covid-19 Case Data");
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
                Toast.makeText(CovidHomeActivity.this, "Country field can't be empty.", Toast.LENGTH_LONG).show();
            }
            else if (fromDate.equalsIgnoreCase("")) {
                // show message country field can't be empty
                Toast.makeText(CovidHomeActivity.this, "Date field can't be empty.", Toast.LENGTH_LONG).show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}