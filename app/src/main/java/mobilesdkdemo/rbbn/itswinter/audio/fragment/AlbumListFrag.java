package mobilesdkdemo.rbbn.itswinter.audio.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.utility.JsonUtils;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;


public class AlbumListFrag extends Fragment {

    private static final String TAG="AlbumListFrag";
    private static final String KEYWORD="audio_keyword";
    private View v;
    private RecyclerView rvAlbumList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<Album> list;
    private EditText etKeyword;
    private ImageButton btnSearch;
    private String albumName;
    private String keyword;
    private IAudioRepository audioRepository;

    private String album_url;
    public AlbumListFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_album_list, container, false);
        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        btnSearch=v.findViewById(R.id.btnSearch);
        etKeyword=v.findViewById(R.id.etKeyword);
        audioRepository=new AudioRepository();
        keyword= PreferenceManager.getString(getContext(),KEYWORD);
        etKeyword.setText(keyword);

        btnSearch.setOnClickListener(v->{
            keyword=etKeyword.getText().toString().trim();
            if(!keyword.isEmpty()){
                retriveList();
                PreferenceManager.setValue(getContext(),KEYWORD,keyword);
            }else{
                Toast.makeText(getContext(), "Please enter your keyword", Toast.LENGTH_SHORT).show();
            }
        });
        initialRecylerView();
        retriveList();
    }

    public void retriveList() {
       String keyword=etKeyword.getText().toString().trim();
        if(!keyword.isEmpty()){
            AlbumQuery req=new AlbumQuery();
            req.execute(keyword);
        }

    }

    private void initialRecylerView() {
        rvAlbumList=v.findViewById(R.id.rvAlbumList);
        rvAlbumList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        rvAlbumList.setLayoutManager(layoutManager);
        myAdapter=new AlbumAdapter(this.getContext(), list);
        rvAlbumList.setAdapter(myAdapter);
    }

    public void notifyChanged(){
        if(myAdapter!=null) myAdapter.notifyDataSetChanged();
    }

    private class AlbumQuery extends AsyncTask< String, Integer, String>{
        ArrayList<Album> albums;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(getContext());
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.show();
            albums=new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... args) {
            String url=String.format("https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=%s",args[0]);
            albums=JsonUtils.getArrayListbyUrl(Album.class,url, "album");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute: "+albums.size());
            if(albums.size()>0){
                        list.clear();
                        list.addAll(albums);
                        myAdapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        }
    }

}