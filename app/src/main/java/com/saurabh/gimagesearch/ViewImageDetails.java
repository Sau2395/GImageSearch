package com.saurabh.gimagesearch;

import android.os.Bundle;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ViewImageDetails extends AppCompatActivity {

    private static final String TAG = "ViewImageDetails";

    private TextView imageTitle, imageWidth, imageHeight, author, viewport;
    private ImageView imageView;
    private ImageDetails imageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_details);
        imageView = findViewById(R.id.fullImageView);
        imageTitle = findViewById(R.id.imageTitle);
        imageHeight = findViewById(R.id.imageHeightValue);
        imageWidth = findViewById(R.id.imageWidthValue);
        viewport = findViewById(R.id.viewportValue);
        author = findViewById(R.id.authorValue);

        ConnectionCheck.isNetworkConnected(this);
        imageDetails = (ImageDetails) getIntent().getSerializableExtra("imageDetails");
        setDetails();
    }

    public void setDetails() {

        imageTitle.setText(imageDetails.getTitle());

        if (!imageDetails.isPageMapNull()) {
            if (!imageDetails.getPageMap().isThumbnailsNULL()) {
                imageWidth.setText(imageDetails.getPageMap().getThumbnails().get(0).getWidth());
                imageHeight.setText(imageDetails.getPageMap().getThumbnails().get(0).getHeight());
            }

            if (!imageDetails.getPageMap().isMetaDataNULL()) {
                viewport.setText(imageDetails.getPageMap().getMetaData().get(0).getViewport());
                author.setText(imageDetails.getPageMap().getMetaData().get(0).getAuthor());
            }

            final CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setStrokeWidth(10f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            //check if thumbnail is present
            if (!imageDetails.getPageMap().isImageNULL()) {
                String imageSrc = imageDetails.getPageMap().getImages().get(0).getSrc();
                Glide.with(this)
                        .asBitmap()
                        .load(imageSrc)
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.image_not_found)
                        .into(imageView);
            } else {
                Glide.with(this)
                        .asBitmap()
                        .load(R.drawable.image_not_found);
            }
        }
    }
}
