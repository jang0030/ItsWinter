package mobilesdkdemo.rbbn.itswinter.audio.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.TrackAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Track;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackListFrag extends Fragment {


    private View v;
    private RecyclerView rvTrackList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter myAdpater;
    private List<Track> list;
    private Bundle dataFromActivity;
    private int id;
    private IAudioRepository repo;
    public TrackListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataFromActivity = getArguments();
        id = dataFromActivity.getInt("albumId");
        v= inflater.inflate(R.layout.fragment_track_list, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list=new ArrayList<>();
        repo=new AudioRepository();
        initialRecylerView();
        retriveList();
    }

    private void retriveList() {
       repo.getTracks(id, new Callback<Wrapper>() {
           @Override
           public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
               if(response.body().getTrack()!=null && response.body().getTrack().size()>0) {
                   if(list.size()>0) list.clear();
                   list.addAll(response.body().getTrack());
                    myAdpater.notifyDataSetChanged();
               }else{
                   Toast.makeText(getContext(), "There are no results", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Wrapper> call, Throwable t) {

           }
       });
    }

    private void initialRecylerView() {
        rvTrackList=v.findViewById(R.id.rvTrackList);
        rvTrackList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        rvTrackList.setLayoutManager(layoutManager);
        myAdpater= new TrackAdapter(this.getContext(),list);
        rvTrackList.setAdapter(myAdpater);
    }

}