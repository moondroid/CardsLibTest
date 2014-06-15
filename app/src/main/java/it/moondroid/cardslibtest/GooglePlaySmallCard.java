package it.moondroid.cardslibtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.moondroid.cardslibtest.drawable.CircleDrawable;
import it.moondroid.cardslibtest.drawable.RoundCornersDrawable;

/**
 * Created by Marco on 15/06/2014.
 */
public class GooglePlaySmallCard extends Card {

    protected TextView mTitle;
    protected TextView mSecondaryTitle;
    protected RatingBar mRatingBar;
    protected int resourceIdThumbnail;

    protected String title;
    protected String secondaryTitle;
    protected float rating;

    public int USE_VIGNETTE=0;

    public GooglePlaySmallCard(Context context) {
        this(context, R.layout.carddemo_mycard_inner_content);
    }

    public GooglePlaySmallCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        //Add thumbnail
//        CardThumbnail cardThumbnail = new CardThumbnail(mContext);
//
//        if (resourceIdThumbnail==0)
//            cardThumbnail.setDrawableResource(R.drawable.ic_launcher);
//        else{
//            cardThumbnail.setDrawableResource(resourceIdThumbnail);
//        }
//
//        addCardThumbnail(cardThumbnail);

        //Add Thumbnail
        GoogleNowBirthThumb thumbnail = new GoogleNowBirthThumb(getContext());
        float density = getContext().getResources().getDisplayMetrics().density;
        int size= (int)(125*density);
        thumbnail.setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s"+size+"/new%2520profile%2520%25282%2529.jpg");
        thumbnail.setErrorResource(R.drawable.ic_launcher);
        addCardThumbnail(thumbnail);


        //Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=" + title, Toast.LENGTH_SHORT).show();
            }
        });

        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Removed card=" + title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
        mSecondaryTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_secondaryTitle);
        mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_myapps_main_inner_ratingBar);

        if (mTitle != null)
            mTitle.setText(title);

        if (mSecondaryTitle != null)
            mSecondaryTitle.setText(secondaryTitle);

        if (mRatingBar != null) {
            mRatingBar.setNumStars(5);
            mRatingBar.setMax(5);
            mRatingBar.setStepSize(0.5f);
            mRatingBar.setRating(rating);
        }

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondaryTitle() {
        return secondaryTitle;
    }

    public void setSecondaryTitle(String secondaryTitle) {
        this.secondaryTitle = secondaryTitle;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getResourceIdThumbnail() {
        return resourceIdThumbnail;
    }

    public void setResourceIdThumbnail(int resourceIdThumbnail) {
        this.resourceIdThumbnail = resourceIdThumbnail;
    }


    class GoogleNowBirthThumb extends CardThumbnail {

        public GoogleNowBirthThumb(Context context) {
            super(context);
        }



        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            /*
            viewImage.getLayoutParams().width = 250;
            viewImage.getLayoutParams().height = 250;
            */

            viewImage.setPadding(10, 10, 10, 10);
        }

        @Override
        public boolean applyBitmap(View imageView, Bitmap bitmap) {
            switch (USE_VIGNETTE){
                case  0:
                    return false;
                case 1:
                    //RounderImage
                    int CORNER_RADIUS = 12; // dips
                    //int MARGIN = 12; // dips

                    float density = getContext().getResources().getDisplayMetrics().density;
                    int mCornerRadius = (int) (CORNER_RADIUS * density + 0.5f);
                    int mMargin = 0;

                    RoundCornersDrawable round = new RoundCornersDrawable(bitmap,mCornerRadius , mMargin);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        imageView.setBackground(round);
                    else
                        imageView.setBackgroundDrawable(round);
                    return true;
                case 2:

                    CircleDrawable circle = new CircleDrawable(bitmap,true);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        imageView.setBackground(circle);
                    else
                        imageView.setBackgroundDrawable(circle);
                    return true;
                default:
                    return false;
            }
        }
    }
}
