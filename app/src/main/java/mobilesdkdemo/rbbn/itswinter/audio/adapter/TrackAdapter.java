package mobilesdkdemo.rbbn.itswinter.audio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.model.Track;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    public interface TrackItemClicked{
        void onTrackItemClicked(Track item);
    }
    private TrackItemClicked context;
    private List<Track> list;
    View v;
    public TrackAdapter(Context context, List<Track> list) {
        this.context = (TrackItemClicked) context;
        this.list = list;
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
        Track item=list.get(position);
        holder.itemView.setTag(item);
        holder.setItem(item);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
