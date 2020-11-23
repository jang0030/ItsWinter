package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.TrackAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.db.WinterRepository;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.GenericListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.model.Track;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.databinding.ActivityAlbumDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This AlbumDetailActivity is for detail album page
 *  * <p>
 *  This AlbumDetailActivity is extended {@link AppCompatActivity}
 *  This AlbumDetailActivity is implemented {@link TrackAdapter.TrackItemClicked}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class AlbumDetailActivity extends AppCompatActivity implements TrackAdapter.TrackItemClicked{

    private static final String TAG="AudioHomeActivity";
    private Album album;
    private ActivityAlbumDetailBinding binding;
    //private TrackListFrag trackListFrag;
    private ArrayList<Track> list;
    private GenericListFrag listFrag;
    private WinterRepository repo;
    private IAudioRepository audioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_album_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);

        album = getIntent().getParcelableExtra("album");

        binding.tvTitle.setText(album.getStrAlbum());
        binding.tvArtist.setText(album.getStrArtist());
        binding.tvYear.setText("" + album.getIntYearReleased());
        binding.tvStatus.setText(album.getStrLocked());
        if (album.getStrAlbumThumb() == null) {
            binding.ivPoster.setImageResource(R.drawable.ic_audio);
        } else {
            //tvScore.setText(item.getPoster_full_path());
            Glide.with((Context) getApplicationContext())
                    .load(album.getStrAlbumThumb())
                    .into(binding.ivPoster);
        }
        String summary = album.getStrDescriptionEN() != null && album.getStrDescriptionEN().length() > 100 ?
                album.getStrDescriptionEN().substring(0, 100) + "..." : album.getStrDescriptionEN();
        binding.tvDescription.setText("Album Description\n" + summary);
        binding.tvDescription.setOnClickListener(v -> {
            if (album.getStrDescriptionEN() != null && album.getStrDescriptionEN().length() > 100)
                Toast.makeText(this, album.getStrDescriptionEN(), Toast.LENGTH_SHORT).show();
        });
        binding.tvGenre.setText("Genre:" + album.getStrGenre());
        binding.tvScore.setText("Score:" + album.getIntScore());
        String memo=album.getMyMemo()==null?"-":album.getMyMemo();
        binding.etMyMemo.setText(album.getMyMemo());
        binding.tvMyMemo.setText(memo);
        if(album.getIsSaved()==1)binding.llMemoInput.setVisibility(View.GONE);
        else binding.llMemoView.setVisibility(View.GONE);

        //trackListFrag = new TrackListFrag();
        repo=new WinterRepository(this);
        audioRepository=new AudioRepository();
        list=new ArrayList<>();
        listFrag= GenericListFrag.newInstance(new TrackAdapter(this, list),R.layout.fragment_album_list);
        Bundle dataToPass = new Bundle();
        dataToPass.putInt("albumId", album.getIdAlbum());
        listFrag.setArguments(dataToPass); //pass it a bundle for information

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, listFrag) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
        binding.ivSave.setOnClickListener(v->{
            album.setMyMemo(binding.etMyMemo.getText().toString().trim());
            album.setIsSaved(1);
            repo.insert_Album(album);
        });

        executeTrackQuery();

    }

    private void executeTrackQuery() {
        audioRepository.getTracks(album.getIdAlbum(), new Callback<Wrapper>() {
            @Override
            public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
                if(response.body().getTrack()!=null && response.body().getTrack().size()>0) {
                   listFrag.retriveList(response.body().getTrack());
                }else{
                    Toast.makeText(AlbumDetailActivity.this, "There are no results", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Wrapper> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                this.finish();
                break;
            case (R.id.action_help):
                new AlertDialog.Builder(this).setTitle("Help")
                        .setMessage("This Page have detailed information about the album that you clicked.\n" +
                                "This page also provides the tracks of the album.\n" +
                                "When you click each track, you can access detailed information about the track through google.")
                        .setPositiveButton(R.string.ok,(click, arg) -> {

                        } )
                        .create().show();
                break;
            case (R.id.action_home):
                startActivity(new Intent(AlbumDetailActivity.this, AudioHomeActivity.class));
                break;
            case (R.id.action_mine):
                startActivity(new Intent(AlbumDetailActivity.this, MyAlbumActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrackItemClicked(Track item) {
        String keyword=String.format("%s+%s",item.getStrAlbum(),item.getStrArtist());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("http://www.google.com/search?q="+keyword, item.getStrArtist())));
        startActivity(intent);

    }


}