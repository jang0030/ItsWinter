package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventHomeActivity extends AppCompatActivity {


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

        boolean isTablet = findViewById(R.id.e_fragFrame) != null;

        Button searchButton = findViewById(R.id.e_searchButton);
        Button savedButton = findViewById(R.id.e_savedButton);

        city = findViewById(R.id.e_citySearch);
        radius = findViewById(R.id.e_radiusSearch);
        prefs = getSharedPreferences("e_searchPrefs", Context.MODE_PRIVATE);

        city.setText(prefs.getString("city",""));
        radius.setText(prefs.getString("radius",""));

        searchButton.setOnClickListener(x->{
            String radiusString = radius.getText().toString();
            Bundle dataToPass = new Bundle();

            dataToPass.putString("city", city.getText().toString());
            dataToPass.putString("radius", radiusString);

            if(Integer.parseInt(radiusString) < 100){
                Toast.makeText(this,this.getString(R.string.e_radiusErrorString),Toast.LENGTH_LONG).show();
            }else{
//                if(isTablet){
//                    EventResultsFragment eventResultsFragment = new EventResultsFragment();
//                    eventResultsFragment.setArguments(dataToPass);
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.e_fragFrame, eventResultsFragment)
//                            .commit();
//                }else {
                    Intent goToResultsScreen = new Intent(EventHomeActivity.this, EventResults.class);
                    goToResultsScreen.putExtras(dataToPass);
                    EventHomeActivity.this.startActivity(goToResultsScreen);
//                }
            }
        });

        savedButton.setOnClickListener(x->{
            Intent goToSavedScreen = new Intent(EventHomeActivity.this, SavedEvents.class);
            EventHomeActivity.this.startActivity(goToSavedScreen);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

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
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}