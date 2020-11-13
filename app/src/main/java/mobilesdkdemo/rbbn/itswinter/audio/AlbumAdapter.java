package mobilesdkdemo.rbbn.itswinter.audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {



    private AlbumItemClicked context;
    private ArrayList<Album> list;

    interface AlbumItemClicked{
      void onAlbumItemClicked(Album item);
    }

    public AlbumAdapter(Context context, ArrayList<Album> list) {
        this.context = (AlbumItemClicked) context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPoster;
        TextView tvTitle, tvArtist, tvGenre, tvYear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvArtist=itemView.findViewById(R.id.tvArtist);
            tvGenre=itemView.findViewById(R.id.tvGenre);
            tvYear=itemView.findViewById(R.id.tvYear);

            itemView.setOnClickListener(v->{
                    Album album= (Album) itemView.getTag();
                    context.onAlbumItemClicked(album);
            });
        }
        public void setItem(Album item){
            tvTitle.setText(item.getStrAlbum());
            tvArtist.setText(item.getStrArtist());
            tvGenre.setText("Genre:"+item.getStrGenre());
//            tvYear.setText(item.getIntYearReleased());
            tvYear.setText("Released:"+item.getIntYearReleased());

            if (item.getStrAlbumThumb() == null) {
                ivPoster.setImageDrawable(ContextCompat.getDrawable((Context) context,
                        R.drawable.ic_audio));
            } else {
                //tvScore.setText(item.getPoster_full_path());
                Glide.with((Context) context)
                        .load(item.getStrAlbumThumb())
                        .into(ivPoster);
            }
        }
    }
    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder holder, int position) {
        Album album=list.get(position);
        holder.itemView.setTag(album);
        holder.setItem(album);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
