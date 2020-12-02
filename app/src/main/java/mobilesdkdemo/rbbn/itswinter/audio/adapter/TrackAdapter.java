package mobilesdkdemo.rbbn.itswinter.audio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.GenericListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Track;

/**
 * This adapter is a concrete class that extends {@link GenericAdapter}
 *  * <p>
 *  TrackAdapter is used to be a myadapter field of {@link GenericListFrag}.
 *  TrackAdapter has the context as the field of  this object.
 *  the context is setted as the Acitivity as interface
 *  This has one interface {@link TrackAdapter.TrackItemClicked}
 *  The interface is used to control this adapter by the Activity.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class TrackAdapter extends GenericAdapter<Track, TrackAdapter.ViewHolder> {



    public interface TrackItemClicked{
        void onTrackItemClicked(Track item);
    }
    private TrackItemClicked context;
    //private List<Track> list;
    View v;
    public TrackAdapter(Context context, ArrayList<Track> list) {
        this.context = (TrackItemClicked) context;
        this.setList(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTrackNum, tvTrackName, tvDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrackNum=itemView.findViewById(R.id.tvTrackNum);
            tvTrackName=itemView.findViewById(R.id.tvTrackName);
            tvDuration=itemView.findViewById(R.id.tvDuration);
            itemView.setOnClickListener(v->{
                Track item= (Track) itemView.getTag();
                context.onTrackItemClicked(item);

            });
        }
        public void setItem(Track item){
            tvTrackNum.setText(String.format("%d)",item.getIntTrackNumber()));
            tvTrackName.setText(item.getStrTrack());
            tvDuration.setText(String.format("%d:%d",(int)item.getIntDuration()/60000,(int)(item.getIntDuration()/1000)%60));
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track item=this.getList().get(position);
        holder.itemView.setTag(item);
        holder.setItem(item);
    }

}
