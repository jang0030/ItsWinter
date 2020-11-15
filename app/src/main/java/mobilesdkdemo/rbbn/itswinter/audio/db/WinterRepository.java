package mobilesdkdemo.rbbn.itswinter.audio.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;


public class WinterRepository {

    private WinterDB db;
    //private AlbumDao dao;

    public WinterRepository(Context context) {
        this.db = WinterDB.getInstance(context);
    }

    public void insert_Album(Album album){ new AlbumInsertAsyncTask(db.getAlbumDao()).execute(album); }

    public void delete_Album(Album album){ new AlbumDeleteAsyncTask(db.getAlbumDao()).execute(album); }

    public void update_Album(Album album){ new AlbumUpdateAsyncTask(db.getAlbumDao()).execute(album); }

    public LiveData<List<Album>> getList_Album(){ return (db.getAlbumDao()).getList_Album();}


    private   class AlbumInsertAsyncTask extends AsyncTask<Album, Void, Void> {
        private AlbumDao dao;

        public AlbumInsertAsyncTask(AlbumDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Album... Albums) {
            long[] id=dao.insert_Album(Albums);
            return null;
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
