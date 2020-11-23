package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.MainActivity;
import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.db.WinterRepository;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.GenericListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.JsonUtils;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;

/**
 * This AudioHomeActivity is for Audio Main page
 *  * <p>
 *  This AudioHomeActivity is extended {@link AppCompatActivity}
 *  This AudioHomeActivity is implemented {@link AlbumAdapter.AlbumItemClicked}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class AudioHomeActivity extends AppCompatActivity implements AlbumAdapter.AlbumItemClicked {

    private static final String TAG="AudioHomeActivity";
    private static final String KEYWORD="audio_keyword";
    //private AlbumListFrag albumsFrag;
    ImageButton btnSearch;
    EditText etKeyword;
    String keyword;
    TextView tvHeader;
    private ArrayList<Album> list;
    private WinterRepository repo;
    private GenericListFrag listFrag;
    private AlbumAdapter myAdpater;

    private class AlbumQuery extends AsyncTask< String, Integer, String> {
        ArrayList<Album> albums;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(AudioHomeActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.show();
            albums=new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                Thread.sleep(300);
                dialog.setProgress(20);
            } catch (InterruptedException e) {
                Log.d(TAG, "onPostExecute: "+e.getMessage());
            }

            String url=String.format("https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=%s",args[0]);
            albums= JsonUtils.getArrayListbyUrl(Album.class,url, "album");
            dialog.setProgress(80);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute: "+albums.size());
            listFrag.retriveList(albums);
            tvHeader.setText(String.format("Album List(%d)",albums.size()));
            try {
                Thread.sleep(300);
                dialog.setProgress(100);
            } catch (InterruptedException e) {
                Log.d(TAG, "onPostExecute: "+e.getMessage());
            }

            dialog.dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_home);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        albumsFrag= (AlbumListFrag) getSupportFragmentManager().findFragmentById(R.id.albumsFrag);
        repo=new WinterRepository(this);
        list=new ArrayList<>();
        myAdpater=new AlbumAdapter(this, list, false);
        listFrag= GenericListFrag.newInstance(myAdpater,R.layout.fragment_album_list);
        btnSearch=findViewById(R.id.btnSearch);
        etKeyword=findViewById(R.id.etKeyword);
        tvHeader=findViewById(R.id.tvHeader);
        keyword= PreferenceManager.getString(this,KEYWORD);
        etKeyword.setText(keyword);

        btnSearch.setOnClickListener(v->{
            keyword=etKeyword.getText().toString().trim();
            if(!keyword.isEmpty()) executeAlbumQuery(keyword);
            else Toast.makeText(this, "Please enter your keyword", Toast.LENGTH_SHORT).show();
        });
        executeAlbumQuery(keyword);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, listFrag) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment

        etKeyword.setOnKeyListener((v, keyCode, event)->{
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                      btnSearch.callOnClick();
                return true;
            }
            return  false;
        });
    }

    private void executeAlbumQuery(String albumName){
        AlbumQuery req=new AlbumQuery();
        req.execute(albumName);
        PreferenceManager.setValue(this,KEYWORD,albumName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audio_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                AudioHomeActivity.this.finish();
                break;
            case (R.id.action_mine):
                startActivity(new Intent(AudioHomeActivity.this, MyAlbumActivity.class));
                break;
            case (R.id.action_main):
                startActivity(new Intent(AudioHomeActivity.this, MainActivity.class));
                break;
            case (R.id.action_help):
                new AlertDialog.Builder(this).setTitle("Help")
                        .setMessage("This Page is home of Audio-API.\n" +
                                "When you enter your title of the album that you want to find and click the search button, you can find the ablum list with the title.\n" +
                                "When you click each item, you can access the detailed information about the album with its tracks.")
                        .setPositiveButton(R.string.yes,(click, arg) -> {

                        } )
                        .create().show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAlbumItemClicked(Album item) {
      Intent intent=new Intent(AudioHomeActivity.this, AlbumDetailActivity.class);
      intent.putExtra("album", item);
      startActivity(intent);

    }

    @Override
    public void onAlbumItemLongClicked(Album item) {
        new AlertDialog.Builder(this).setTitle("Add").setMessage("Do you want to add this album in your box?")
                .setPositiveButton(R.string.yes,(click, arg) -> {
                        item.setIsSaved(1);
                        repo.insert_Album(item);
                } )
                .setNegativeButton("No", (click, arg) -> {  })
                .create().show();
    }

    @Override
    public void onAlbumItemAddClicked(Album item) {
        onAlbumItemLongClicked(item);
    }
}