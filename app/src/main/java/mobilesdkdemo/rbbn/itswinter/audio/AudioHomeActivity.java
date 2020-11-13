package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.AlbumListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioHomeActivity extends AppCompatActivity implements AlbumAdapter.AlbumItemClicked {

    private static final String TAG="AudioHomeActivity";


//    FragmentManager fragmentManager;
//    AlbumListFrag listFrag;

//    EditText etKeyword;
//    ImageButton btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_home);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                AudioHomeActivity.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAlbumItemClicked(Album item) {
      // Intent intent=new Intent(AudioHomeActivity.this, TrackActivity.class);

    }
}