package com.saurabh.gimagesearch;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private List<ImageDetails> imageDetails;
    private Context context;

    public RecycleViewAdapter(List<ImageDetails> imageDetails, Context context) {
        this.imageDetails = imageDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listimageiteam, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final ImageDetails imageDetail = imageDetails.get(position);
        String imageSrc = "";

        final String imageTitle = imageDetail.getTitle();
        final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        //check if thumbnail is present
        if (!imageDetail.isPageMapNull()) {
            if (!imageDetail.getPageMap().isThumbnailsNULL()) {
                imageSrc = imageDetail.getPageMap().getThumbnails().get(0).getSrc();
                Glide.with(context)
                        .asBitmap()
                        .load(imageSrc)
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.image_not_found)
                        .into(viewHolder.resultImage);
            } else {
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.image_not_found);
            }
        }
        viewHolder.resultImageTitle.setText(imageTitle);

        viewHolder.resultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewImageDetails.class);
                intent.putExtra("imageDetails", imageDetail);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView resultImage;
        TextView resultImageTitle;
        RelativeLayout resultLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            resultImage = itemView.findViewById(R.id.result_image);
            resultImageTitle = itemView.findViewById(R.id.result_imageTitle);
            resultLayout = itemView.findViewById(R.id.result_layout);

        }
    }
}
