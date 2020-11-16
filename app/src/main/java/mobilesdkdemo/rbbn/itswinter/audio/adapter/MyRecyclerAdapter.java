package mobilesdkdemo.rbbn.itswinter.audio.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

public abstract class MyRecyclerAdapter<T, V extends ViewHolder> extends RecyclerView.Adapter<V> {

    private ArrayList<T> list;

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public void retriveList(List<T> newList){
        if(newList.size()>0){
            list.clear();
            list.addAll(newList);
            this.notifyDataSetChanged();
        }
    }

    public boolean removeItem(T item){
        boolean result= list.remove(item);
        this.notifyDataSetChanged();
        return result;
    }
}
