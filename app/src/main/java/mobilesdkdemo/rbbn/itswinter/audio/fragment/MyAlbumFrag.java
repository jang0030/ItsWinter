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
import mobilesdkdemo.rbbn.itswinter.audio.db.WinterRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

/**
 * It is not use and It was replaced by MyListFrag
 */
public class MyAlbumFrag extends Fragment {
    private RecyclerView rvAlbumList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter myAdapter;
    private View v;
    private WinterRepository repo;
    private ArrayList<Album> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_my_album, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list=new ArrayList<>();
        repo=new WinterRepository(getContext());
        initialRecyclerView();
        retrieveList();

    }

    private void retrieveList() {
        repo.getList_Album().observe(getActivity(), albums -> {
            if(list.size() > 0){
                list.clear();
            }
            if(albums != null){
                list.addAll(albums);
            }
            myAdapter.notifyDataSetChanged();
        });
    }

    private void initialRecyclerView() {
        rvAlbumList=v.findViewById(R.id.rvList);
        rvAlbumList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        rvAlbumList.setLayoutManager(layoutManager);
        myAdapter=new AlbumAdapter(getContext(), list);
        rvAlbumList.setAdapter(myAdapter);
    }

//    public boolean removeItem(Album item){
//      //int pos= list.indexOf(item);
//        boolean result;
//        try {
//            repo.delete_Album(item);
//            list.remove(item);
//            myAdapter.notifyDataSetChanged();
//            result=true;
//        }catch (Exception e){
//            result=false;
//        }
//
//      return  result;
//    }

    public void notifyChanged(){
        if(myAdapter!=null) myAdapter.notifyDataSetChanged();
    }

}