package mobilesdkdemo.rbbn.itswinter.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import mobilesdkdemo.rbbn.itswinter.R;

public class EventHomeActivity extends AppCompatActivity {

//    TODO: be able to click event link, take to browser (alert dialog)
//    TODO: save last search term in shared Preferences
//    TODO: add favorite feature for event (save to database)(toast)
//    TODO: add favorites screen (fragment?)
//    TODO: add favorites search (OPTIONAL)
//    TODO: add favorites remove (snackbar with revert option)




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Event Schedule");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button searchButton = findViewById(R.id.e_searchButton);
        EditText city = findViewById(R.id.e_citySearch);
        EditText radius = findViewById(R.id.e_radiusSearch);

        searchButton.setOnClickListener(x->{
            Intent goToResultsScreen = new Intent(EventHomeActivity.this, eventResults.class);
            goToResultsScreen.putExtra("city",city.getText().toString());
            goToResultsScreen.putExtra("radius",radius.getText().toString());
            EventHomeActivity.this.startActivity(goToResultsScreen);
        });
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