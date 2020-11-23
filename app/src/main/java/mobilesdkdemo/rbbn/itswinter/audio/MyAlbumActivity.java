package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.db.WinterRepository;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.GenericListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;

/**
 * This MyAlbumActivity is for My Album page
 *  * <p>
 *  This MyAlbumActivity is extended {@link AppCompatActivity}
 *  This MyAlbumActivity is implemented {@link AlbumAdapter.AlbumItemClicked}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class MyAlbumActivity extends AppCompatActivity implements AlbumAdapter.AlbumItemClicked{

    //private MyAlbumFrag myAlbumFrag;
    private Album selectedItem;
    private static final String TAG="MyAlbumActivity";
    private static final String KEYWORD="my_Album_filter";
    //private AlbumListFrag albumsFrag;
    ImageButton btnSearch;
    EditText etKeyword;
    String keyword;
    WinterRepository repo;
    TextView tvHeader;
    private ArrayList<Album> list;

    private GenericListFrag myListFrag;
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
        myListFrag = GenericListFrag.newInstance(new AlbumAdapter(this, list, true),R.layout.fragment_album_list);
        btnSearch=findViewById(R.id.btnSearch);
        etKeyword=findViewById(R.id.etKeyword);
        tvHeader=findViewById(R.id.tvHeader);
        keyword= PreferenceManager.getString(this,KEYWORD);
        etKeyword.setText(keyword);

        btnSearch.setOnClickListener(v->{
            keyword=etKeyword.getText().toString().trim();
            executeAlbumQuery(keyword);
        });
        executeAlbumQuery("");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, myListFrag) //Add the fragment in FrameLayout
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

    private void executeAlbumQuery(String filtering){
        if(filtering.isEmpty()){
            repo.getList_Album().observe(this, albums -> {
                if(list.size() > 0){
                    list.clear();
                }
                if(albums != null){
                    list.addAll(albums);
                    tvHeader.setText(String.format("My Album List(%d)",albums.size()));
                }
                myListFrag.notifyChanged();
            });
        }else{
            repo.getList_Album_filter(filtering).observe(this, albums -> {
                if(list.size() > 0){
                    list.clear();
                }
                if(albums != null){
                    list.addAll(albums);
                    tvHeader.setText(String.format("My Album List(%d)",albums.size()));
                }
                myListFrag.notifyChanged();
            });
        }

        PreferenceManager.setValue(this,KEYWORD,filtering);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myalbum, menu);
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
                        .setMessage("This Page is your storage box for your albums.\n" +
                                "When you click each item, you can access detail ablum infomations with its tracks.\n" +
                                "When you click each item for more long time, you can delete to the item in your storage. ")
                        .setPositiveButton(R.string.yes,(click, arg) -> {

                        } )
                        .create().show();
                break;
            case (R.id.action_home):
                startActivity(new Intent(MyAlbumActivity.this, AudioHomeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAlbumItemClicked(Album item) {
        Intent intent=new Intent(MyAlbumActivity.this, AlbumDetailActivity.class);
        intent.putExtra("album", item);
        startActivity(intent);

    }

    public void onAlbumItemLongClicked(Album item) {

        new AlertDialog.Builder(this).setTitle("Delete").setMessage("Do you want to delete this album?")
                .setPositiveButton(R.string.yes,(click, arg) -> {
                    //removeAlbumItem(item);
                    try {
                        repo.delete_Album(item); //list is setted livedata
                    }catch (Exception e){
                        Toast.makeText(MyAlbumActivity.this, "There is some issue", Toast.LENGTH_SHORT).show();
                    }
                } )
                .setNegativeButton("No", (click, arg) -> {  })
                .create().show();


    }

    @Override
    public void onAlbumItemAddClicked(Album item) {
        onAlbumItemLongClicked(item);
    }



}