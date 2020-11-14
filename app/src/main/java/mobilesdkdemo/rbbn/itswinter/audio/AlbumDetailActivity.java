package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.TrackAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.TrackListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.model.Track;
import mobilesdkdemo.rbbn.itswinter.databinding.ActivityAlbumDetailBinding;


public class AlbumDetailActivity extends AppCompatActivity implements TrackAdapter.TrackItemClicked {

    private Album album;
    private ActivityAlbumDetailBinding binding;
    TrackListFrag trackListFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_album_detail);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_album_detail);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);

        album=getIntent().getParcelableExtra("album");

        binding.tvTitle.setText(album.getStrAlbum());
        binding.tvArtist.setText(album.getStrArtist());
        binding.tvYear.setText(""+album.getIntYearReleased());
        binding.tvStatus.setText(album.getStrLocked());
        if (album.getStrAlbumThumb() == null) {
            binding.ivPoster.setImageResource(R.drawable.ic_audio);
        } else {
            //tvScore.setText(item.getPoster_full_path());
            Glide.with((Context) getApplicationContext())
                    .load(album.getStrAlbumThumb())
                    .into(binding.ivPoster);
        }
        String summary=album.getStrDescriptionEN()!=null&& album.getStrDescriptionEN().length()>100?
                album.getStrDescriptionEN().substring(0,100)+"...":album.getStrDescriptionEN();
        binding.tvDescription.setText("Album Description\n"+summary);
        binding.tvDescription.setOnClickListener(v->{
            if(album.getStrDescriptionEN()!=null&& album.getStrDescriptionEN().length()>100)
            Toast.makeText(this, album.getStrDescriptionEN(), Toast.LENGTH_SHORT).show();
        });
        binding.tvGenre.setText("Genre:"+album.getStrGenre());
        binding.tvScore.setText("Score:"+album.getIntScore());
        trackListFrag=new TrackListFrag();
        Bundle dataToPass = new Bundle();
        dataToPass.putInt("albumId",album.getIdAlbum());
        trackListFrag.setArguments( dataToPass ); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, trackListFrag) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (android.R.id.home):
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrackItemClicked(Track item) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("http://www.google.com/search?q=%s+ARTIST+NAME",item.getStrArtist())));
        startActivity(intent);
    }
}