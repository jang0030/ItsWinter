package mobilesdkdemo.rbbn.itswinter.audio.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.MyAlbumActivity;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.Utility;

/**
 * This WinterRepository is to query inner DB for SQLite with Room database.
 *  * <p>
 *  This WinterRepository make a singleton {@link WinterDB}.
 *  This WinterRepository uses {@link AlbumInsertAsyncTask}, {@link AlbumDeleteAsyncTask}, {@link AlbumDeleteAsyncTask} that is extented by {@link AsyncTask}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
// This is to query inner DB for SQLite with Room database.
public class WinterRepository {

    private WinterDB db;
    private Context mContext;
    long newId;
    public WinterRepository(Context context) {
        this.db = WinterDB.getInstance(context);
        this.mContext=context;
    }

    public void insert_Album(Album album){ new AlbumInsertAsyncTask(db.getAlbumDao()).execute(album); }

    public void delete_Album(Album album){ new AlbumDeleteAsyncTask(db.getAlbumDao()).execute(album); }

    public void update_Album(Album album){ new AlbumUpdateAsyncTask(db.getAlbumDao()).execute(album); }

    public LiveData<List<Album>> getList_Album(){ return (db.getAlbumDao()).getList_Album();}

    public LiveData<List<Album>> getList_Album_filter(String keyword) {return (db.getAlbumDao()).getList_Album_filter(keyword);}

    private  class AlbumInsertAsyncTask extends AsyncTask<Album, Void, Long> {
        private AlbumDao dao;
        private Album item;
        public AlbumInsertAsyncTask(AlbumDao dao) { this.dao = dao; }

        @Override
        protected Long doInBackground(Album... Albums) {
            newId=0;
            try{
                long[] id=dao.insert_Album(Albums);
                newId=id[0];
                item=Albums[0];
            }catch (SQLiteConstraintException e){
                newId=0;
            }

            return newId;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            //RelativeLayout relativeLayout=new RelativeLayout(mContext);
            View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
            if(aLong==0){
                Toast.makeText(mContext,
                        "* You can not save this album in your box. *\n" +
                            "The album was already saved in your storage.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Snackbar.make(rootView, "This album is saved in your box.", Snackbar.LENGTH_LONG).setAction(R.string.a_Undo,v->{
                    if(item!=null)delete_Album(item);
                }).show();
            }

        }
    }

    private  class AlbumDeleteAsyncTask extends AsyncTask<Album, Void, Long>{
        private AlbumDao dao;
        private Album item;
        public AlbumDeleteAsyncTask(AlbumDao dao) {
            this.dao = dao;
        }
        @Override
        protected Long doInBackground(Album... Albums) {
            newId=0;
            try{
                newId= dao.delete_Album(Albums);
                item=Albums[0];
            }catch (Exception e){
                newId=0;
            }

            return newId;
        }

        @Override
        protected void onPostExecute(Long aLong) {

            View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
            if(aLong==0){
                Toast.makeText(mContext,
                        "* You can not delete this albume in your box." , Toast.LENGTH_SHORT).show();
            }else{
                Snackbar.make(rootView, "This album is deleted in your box.", Snackbar.LENGTH_LONG).setAction(R.string.a_Undo,v->{
                    if(item!=null)insert_Album(item);
                }).show();

            }

        }
    }

    private  class AlbumUpdateAsyncTask extends AsyncTask<Album, Void, Void>{
        private AlbumDao dao;
        public AlbumUpdateAsyncTask(AlbumDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Album... Albums) {
            dao.update_Album(Albums);
            return null;
        }
    }


}
