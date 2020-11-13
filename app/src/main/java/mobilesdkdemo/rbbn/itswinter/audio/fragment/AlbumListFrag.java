package mobilesdkdemo.rbbn.itswinter.audio.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlbumListFrag extends Fragment {

    View v;
    RecyclerView rvAlbumList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;
    ArrayList<Album> list;
    private String albumName;

    private IAudioRepository audioRepository;
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
        audioRepository=new AudioRepository();
        initialRecylerView();
        albumName="daft_punk";
        retriveList(albumName);

    }

    public void retriveList(String albumName) {
      audioRepository.getAlbums(albumName, new Callback<Wrapper>() {
          @Override
          public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
                if(response.body().getAlbum().size()>0){
                      if(list.size()>0) list.clear();
                      list.addAll(response.body().getAlbum());
                      myAdapter.notifyDataSetChanged();
                }
          }

          @Override
          public void onFailure(Call<Wrapper> call, Throwable t) {

          }
      });

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

}