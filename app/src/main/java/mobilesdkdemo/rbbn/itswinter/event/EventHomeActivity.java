package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.covid.CovidHomeActivity;

public class EventHomeActivity extends AppCompatActivity {
    /**EventHomeActivity.java
     * Zackery Brennan
     * 040952243
     *
     * This is the main activity for the Ticket Master event search
     * It's function is to allow the used to go their saved events, and search for events
     *
     * e_ will be a common prefix used to identify something as part of the Ticket Master search
     * */

//    TODO: fragment functionality

    private SharedPreferences prefs;
    private EditText city;
    private EditText radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        gets reference to elements in the view
        Button searchButton = findViewById(R.id.e_searchButton);
        Button savedButton = findViewById(R.id.e_savedButton);
        city = findViewById(R.id.e_citySearch);
        radius = findViewById(R.id.e_radiusSearch);

//        gets and sets last entered search terms from SharedPreferences
        prefs = getSharedPreferences("e_searchPrefs", Context.MODE_PRIVATE);
        city.setText(prefs.getString("city",""));
        radius.setText(prefs.getString("radius",""));

        searchButton.setOnClickListener(x->{
            String radiusString = radius.getText().toString();

//            adds search terms to bundle to be sent to EventResults.java for searching
            Bundle dataToPass = new Bundle();
            dataToPass.putString("city", city.getText().toString());
            dataToPass.putString("radius", radiusString);

            if(Integer.parseInt(radiusString) < 100){
                Toast.makeText(this,this.getString(R.string.e_radiusErrorString),Toast.LENGTH_LONG).show();
            }else{
                    Intent goToResultsScreen = new Intent(EventHomeActivity.this, EventResults.class);
                    goToResultsScreen.putExtras(dataToPass);
                    EventHomeActivity.this.startActivity(goToResultsScreen);
//                }
            }
        });

//        sends user to SavedEvents.java on click
        savedButton.setOnClickListener(x->{
            Intent goToSavedScreen = new Intent(EventHomeActivity.this, SavedEvents.class);
            EventHomeActivity.this.startActivity(goToSavedScreen);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

//        adds search terms to SharedPreferences
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("city",city.getText().toString());
        edit.putString("radius",radius.getText().toString());
        edit.commit();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                EventHomeActivity.this.finish();
                break;
            case R.id.e_eventHomeMenu:
                AlertDialog.Builder alertDialogHelp = new AlertDialog.Builder(this);
                alertDialogHelp.setTitle(getString(R.string.e_homeHelpMessageTitle))
                        .setMessage(getString(R.string.e_homeHelpMessageBody))
                        .setPositiveButton((R.string.e_yesString),(click,args)->{})
                        .create().show();
                break;
            case R.id.e_developerInfoMenu:
                AlertDialog.Builder alertDialogInfo = new AlertDialog.Builder(this);
                alertDialogInfo.setTitle(getString(R.string.e_developerInfo))
                        .setMessage("EventHomeActivity.java \nVersion Number: 1.0 \nAuthor: Zackery Brennan")
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