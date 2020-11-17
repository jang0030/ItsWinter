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
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.MyRecyclerAdapter;


public class MyListFrag<T> extends Fragment {

    private static final String TAG="MyListFrag";
    private View v;
    private RecyclerView rvList;
    private RecyclerView.LayoutManager layoutManager;
    private static MyRecyclerAdapter myAdapter;
    //private ArrayList<T> list;
    private static int resource;
    public MyListFrag() {
        // Required empty public constructor
    }
    public static MyListFrag newInstance(MyRecyclerAdapter adapter, int resourceId) {
        MyListFrag fragment = new MyListFrag();
        myAdapter=adapter;
        resource=resourceId;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(resource, container, false);
        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  list=new ArrayList<>();
        initialRecylerView();
    }


    public void retriveList(List<T> newList) {
            myAdapter.retrieveList(newList);
    }

    private void initialRecylerView() {
        rvList =v.findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(myAdapter);
    }

    public void notifyChanged(){
        if(myAdapter!=null) myAdapter.notifyDataSetChanged();
    }

//    public boolean removeItem(T item){
//        return myAdapter.removeItem(item);
//    }

}