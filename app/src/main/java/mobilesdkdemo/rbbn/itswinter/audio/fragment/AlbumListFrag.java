package mobilesdkdemo.rbbn.itswinter.audio.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.utility.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private AlbumQuery req;
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
        album_url="https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=";
         req = new AlbumQuery();


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
//        if(!keyword.isEmpty()){
////            audioRepository.getAlbums(keyword, new Callback<Wrapper>() {
////                @Override
////                public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
////                    if(response.body().getAlbum()!=null && response.body().getAlbum().size()>0) {
////                        if(list.size()>0) list.clear();
////                        list.addAll(response.body().getAlbum());
////                        myAdapter.notifyDataSetChanged();
////                    }else{
////                        Toast.makeText(getContext(), "There are no results", Toast.LENGTH_SHORT).show();
////                    }
////                }
////                @Override
////                public void onFailure(Call<Wrapper> call, Throwable t) {
////                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
////                }
////            });
////        }
        req.execute(keyword);  //Type 1
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

        @Override
        protected String doInBackground(String... args) {
            try {
                 album_url=String.format("https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=%s",args[0]);
                URL url = new URL(album_url);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject json = new JSONObject(sb.toString());
                json.getString("status");
                JSONArray jsonArray = json.getJSONArray("object");
                JSONArray jsonArray1 = new JSONArray(json);
                List<Album> albums = new ArrayList<>();
               // JSONArray jsonArray = new JSONArray(stringJsonContainArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Album album = new Gson().fromJson(jsonArray.get(i).toString(), Album.class);
                    albums.add(album);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}