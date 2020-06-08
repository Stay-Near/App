package com.example.staynear.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.staynear.R;
import com.google.firebase.auth.FirebaseAuth;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Room> {
    private static final String TAG = "HistoryAdapter";
    private Context mContext;
    private int mResource;
    //ViewHolder object
    ViewHolder holder;
    ArrayList<String> appointments;

    private static class ViewHolder {
        TextView title;
        TextView location;
        TextView price;
        TextView date;
        ImageView image;
    }

    public HistoryAdapter(Context context, int resource, ArrayList<Room> objects,ArrayList<String> dates) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.appointments = dates;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the Appointment information
        // String currentUser = ""+ FirebaseAuth.getInstance().getCurrentUser().getUid();
        //String day = "placeholder";
        String day = appointments.get(position);
        String title = getItem(position).getTitle();
        String location = getItem(position).getLocation();
        String price = "$"+getItem(position).getPrice();
        String imgUrl = getItem(position).getPhoto();

        Log.e("Position","Posición : "+ position);
        //create the view result for showing the animation
        final View result;




        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new HistoryAdapter.ViewHolder();
            holder.title = convertView.findViewById(R.id.tvTitle);
            holder.location = convertView.findViewById(R.id.tvLocation);
            holder.price = convertView.findViewById(R.id.tvPrice);
            holder.date = convertView.findViewById(R.id.tvDate);
            holder.image = convertView.findViewById(R.id.image);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (HistoryAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        // Animation animation = AnimationUtils.loadAnimation(mContext,
        //        (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        //result.startAnimation(animation);

        holder.title.setText(title);
        holder.location.setText(location);
        holder.price.setText(price);
        holder.date.setText(day);

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();
        ;
        int defaultImage = mContext.getResources().getIdentifier("@drawable/logo_size",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.image, options);

        return convertView;
    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

}
