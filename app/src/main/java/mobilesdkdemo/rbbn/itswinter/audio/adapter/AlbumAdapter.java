package mobilesdkdemo.rbbn.itswinter.audio.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;
import mobilesdkdemo.rbbn.itswinter.audio.fragment.GenericListFrag;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

/**
 * This adapter is a concrete class that extends {@link GenericAdapter}
 *  * <p>
 *  AlbumAdapter is used to be a myadapter field of {@link GenericListFrag}.
 *  AlbumAdapter has the context as the field of  this object.
 *  the context is setted as the Acitivity.
 *  This has one interface {@link AlbumItemClicked}
 *  The interface is used to control this adapter by the Activity.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class AlbumAdapter extends GenericAdapter<Album, AlbumAdapter.ViewHolder> {

    private AlbumItemClicked context;
    private boolean isMine;
    public interface AlbumItemClicked{
        void onAlbumItemClicked(Album item);
        void onAlbumItemLongClicked(Album item);
        void onAlbumItemAddClicked(Album item);
    }

    public AlbumAdapter(Context context, ArrayList<Album> list, boolean isMine) {
        this.context = (AlbumItemClicked) context;
        this.setList(list);
        this.isMine=isMine;
    }

    public class ViewHolder extends GenericAdapter.ViewHolder{

        ImageView ivPoster;
        TextView tvTitle, tvArtist, tvGenre, tvYear;
        ImageButton ivAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvArtist=itemView.findViewById(R.id.tvArtist);
            tvGenre=itemView.findViewById(R.id.tvGenre);
            tvYear=itemView.findViewById(R.id.tvYear);
            ivAdd=itemView.findViewById(R.id.ivAdd);

            if(isMine){
                ivAdd.setBackgroundResource(R.drawable.ic_delete);
            }
            itemView.setOnClickListener(v->{
                context.onAlbumItemClicked((Album) itemView.getTag());
            });

            itemView.setOnLongClickListener(v->{
                context.onAlbumItemLongClicked((Album) itemView.getTag());
                return false;
            });

            ivAdd.setOnClickListener(v->{
                context.onAlbumItemAddClicked((Album) itemView.getTag());
            });


        }
        private void setItem(Album item){
            tvTitle.setText(item.getStrAlbum());
            tvArtist.setText(item.getStrArtist());
            tvGenre.setText("Genre:"+item.getStrGenre());
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
        Album album=this.getList().get(position);
        holder.itemView.setTag(album);
        holder.setItem(album);
    }


}
