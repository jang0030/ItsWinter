package mobilesdkdemo.rbbn.itswinter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private TextView tvHelpInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvHelpInfo=findViewById(R.id.tvHelpInfo);
        tvHelpInfo.setText("When you clicked each icon, you can visit individual app.\n" +
                "● First icon can access the Recipe app that is made by Jiyeon Choi. \n "+
                "● Second icon can access the Covid-19 app that is made by Hyunju Jang.\n " +
                "● Third icon can access the Audio-API app that is made by Kiwoong Kim.\n" +
                "● Forth icon can access the Ticket Event app that is made by Zackery Brennan."
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                HelpActivity.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}