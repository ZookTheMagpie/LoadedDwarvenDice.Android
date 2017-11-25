package no.ntnu.hallvardpc.loadeddwarvendice;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder> {
    Context context;
    List<Integer> list;
    OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public ThumbnailAdapter(Context context, List<Integer> list)
    {
        this.list = list;
        this.context = context;
    }
    @Override
    public ThumbnailAdapter.ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thumbnail,parent,false);
        return new ThumbnailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThumbnailAdapter.ThumbnailViewHolder holder, int position) {
        /*Picasso.with(context)
                .load(Uri.parse("https://image.slidesharecdn.com/krisddfillable3-5charsheet-090608011808-phpapp02/95/kris-dd-fillable-35-char-sheet-1-728.jpg?cb=1244423900" + "?width=400"))
                .into(holder.thumb);*/
        holder.thumb.setImageResource(R.drawable.character_sheet);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public void setSheets()
    {
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumb;

        public ThumbnailViewHolder(View view) {
            super(view);
            this.thumb = view.findViewById(R.id.imageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        listener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
