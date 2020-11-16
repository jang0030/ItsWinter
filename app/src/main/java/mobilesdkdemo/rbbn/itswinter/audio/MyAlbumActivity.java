package mobilesdkdemo.rbbn.itswinter.audio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.MyAlbumFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.Utility;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MyAlbumActivity extends AppCompatActivity implements AlbumAdapter.AlbumItemClicked{

    private MyAlbumFrag myAlbumFrag;
    private Album selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Audio API");
        actionBar.setDisplayHomeAsUpEnabled(true);
        myAlbumFrag= (MyAlbumFrag) getSupportFragmentManager().findFragmentById(R.id.myAlbumFrag);
        selectedItem=null;
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
                Utility.createAndShowDialog(MyAlbumActivity.this, "title", "msg");
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
        selectedItem=item;
        //AlertDialog dialog=Utility.createAndShowDialog(this,"Delete Album", "Do you want to delete this album?");
        new AlertDialog.Builder(this).setTitle("Delete").setMessage("Do you want to delete this album?")
                .setPositiveButton(R.string.yes,(click, arg) -> {
                    if(myAlbumFrag.removeItem(selectedItem)){
                        Toast.makeText(MyAlbumActivity.this, "It was deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyAlbumActivity.this, "It was not deleted", Toast.LENGTH_SHORT).show();
                    }
                } )
                .setNegativeButton("No", (click, arg) -> {  })
                .create().show();

    }

}