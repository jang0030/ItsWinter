package mobilesdkdemo.rbbn.itswinter.audio.db;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.Utility;


public class WinterRepository {

    private WinterDB db;
    private Context mContext;

    public WinterRepository(Context context) {
        this.db = WinterDB.getInstance(context);
        this.mContext=context;
    }

    public void insert_Album(Album album){ new AlbumInsertAsyncTask(db.getAlbumDao()).execute(album); }

    public void delete_Album(Album album){ new AlbumDeleteAsyncTask(db.getAlbumDao()).execute(album); }

    public void update_Album(Album album){ new AlbumUpdateAsyncTask(db.getAlbumDao()).execute(album); }

    public LiveData<List<Album>> getList_Album(){ return (db.getAlbumDao()).getList_Album();}


    private   class AlbumInsertAsyncTask extends AsyncTask<Album, Void, Long> {
        private AlbumDao dao;
        long newId;
        public AlbumInsertAsyncTask(AlbumDao dao) { this.dao = dao; }

        @Override
        protected Long doInBackground(Album... Albums) {

            try{
                long[] id=dao.insert_Album(Albums);
                newId=id[0];
            }catch (SQLiteConstraintException e){
                newId=0;
            }

            return newId;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if(newId==0){
                Toast.makeText(mContext,
                        "* You can not save this albume. *\n" +
                            "The album was already saved in your storage.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext,
                        "This album is saved successfully.",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private  class AlbumDeleteAsyncTask extends AsyncTask<Album, Void, Void>{
        private AlbumDao dao;
        public AlbumDeleteAsyncTask(AlbumDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Album... Albums) {
            dao.delete_Album(Albums);
            return null;
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
