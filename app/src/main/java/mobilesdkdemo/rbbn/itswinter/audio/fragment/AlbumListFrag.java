package mobilesdkdemo.rbbn.itswinter.audio.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

/**
 * It is not use and It was replaced by MyListFrag
 */
public class AlbumListFrag extends Fragment {

    private static final String TAG="AlbumListFrag";
    private static final String KEYWORD="audio_keyword";
    private View v;
    private RecyclerView rvList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<Album> list;

   // private IAudioRepository audioRepository;

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
        initialRecylerView();
    }


//    public void retriveList(ArrayList<Album> albums) {
//
//        if(albums.size()>0){
//            list.clear();
//            list.addAll(albums);
//            myAdapter.notifyDataSetChanged();
//        }
//
//    }

    private void initialRecylerView() {
        rvList =v.findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        rvList.setLayoutManager(layoutManager);
        myAdapter=new AlbumAdapter(this.getContext(), list);
        rvList.setAdapter(myAdapter);
    }

    public void notifyChanged(){
        if(myAdapter!=null) myAdapter.notifyDataSetChanged();
    }



}