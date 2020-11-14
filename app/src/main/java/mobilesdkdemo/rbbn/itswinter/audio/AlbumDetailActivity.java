package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;


public class AlbumDetailActivity extends AppCompatActivity {

    private Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_album_detail);
       // binding = DataBindingUtil.setContentView(this,R.layout.activity_album_detail);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);

        album=getIntent().getParcelableExtra("album");
    }
}