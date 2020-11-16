package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapterOrg;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.db.WinterRepository;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.ListMultiFrag;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.MyAlbumFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;

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

    private ListMultiFrag listFra;
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
        listFra= ListMultiFrag.newInstance(new AlbumAdapter(this, list),R.layout.fragment_album_list);
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
                .replace(R.id.fragmentLocation, listFra) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment

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
                listFra.notifyChanged();
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
                listFra.notifyChanged();
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
                        .setMessage("This Page is my storage box for my albums.\n" +
                                "When you click each item, you can access detail ablum infomations with their tracks.\n" +
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

    @Override
    public void onAlbumItemLongClicked(Album item) {
        //selectedItem=item;
        //AlertDialog dialog=Utility.createAndShowDialog(this,"Delete Album", "Do you want to delete this album?");
        new AlertDialog.Builder(this).setTitle("Delete").setMessage("Do you want to delete this album?")
                .setPositiveButton(R.string.yes,(click, arg) -> {
                    removeAlbumItem(item);
                } )
                .setNegativeButton("No", (click, arg) -> {  })
                .create().show();

    }
    public void removeAlbumItem(Album item){
        try {
            repo.delete_Album(item); //list is setted livedata
            Toast.makeText(MyAlbumActivity.this, "It was deleted", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(MyAlbumActivity.this, "It was not deleted", Toast.LENGTH_SHORT).show();
        }

    }

}