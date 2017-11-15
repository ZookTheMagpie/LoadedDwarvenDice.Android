package no.ntnu.hallvardpc.loadeddwarvendice;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder> {
    List<Photo> photos = new ArrayList<>();
    Context context;

    OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public ThumbnailAdapter(Context context) {
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
        Drawable drawable = getResources().getDrawable(R.drawable.character_sheet);
        Picasso.with(context)
                .load(Uri.parse(drawable + "?width=400"))
                .into(holder.thumb);
    }

    @Override
    public int getItemCount() {

        return 1;
    }


    public void setSheets() {
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

    public static class Photo {

    }
}
