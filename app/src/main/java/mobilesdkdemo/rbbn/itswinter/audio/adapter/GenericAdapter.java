package mobilesdkdemo.rbbn.itswinter.audio.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

/**
 * This adapter is a concrete class that extends {@link androidx.recyclerview.widget.RecyclerView.Adapter}
 *  * <p>
 *  GenericAdapter is abstract class.
 *  GenericAdapter need to concreate Adapter ,such as {@link AlbumAdapter}, {@link TrackAdapter}
 *  This has a field one generic ArrayList<T> list;
 *  The list field is used by retrieve list
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public abstract class GenericAdapter<T, V extends ViewHolder> extends RecyclerView.Adapter<V> {

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


    public void retrieveList(List<T> newList){
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

    @Override
    public int getItemCount() {
        return this.getList().size();
    }

}
