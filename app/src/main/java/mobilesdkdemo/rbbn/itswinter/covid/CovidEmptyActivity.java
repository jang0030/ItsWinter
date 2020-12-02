package mobilesdkdemo.rbbn.itswinter.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This class is for showing fragment
 * when search result item is clicked on
 * @author HYUNJU JANG
 * @version 1.00
 */
public class CovidEmptyActivity extends AppCompatActivity {

    /**
     * When this activity starts, this method runs.
     * passes data to CovidDetailsFragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_empty);

        Bundle dataToPass = getIntent().getExtras();

        //This is copied directly from FragmentExample.java lines 47-54
        CovidDetailsFragment dFragment = new CovidDetailsFragment();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.covidFragmentLocation, dFragment)
                .commit();
    }
}