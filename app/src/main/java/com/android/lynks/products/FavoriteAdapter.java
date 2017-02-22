package com.android.lynks.products;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteAdapter extends ArrayAdapter<Product> {
    private static final String LOG_TAG = FavoriteAdapter.class.getSimpleName();
    Context context;
    int layoutResourceId;
    ArrayList<Product> products = new ArrayList<>();
    public FavoriteAdapter(Activity context, int layoutResourceId, ArrayList<Product> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId=layoutResourceId;
        this.context=context;
        this.products=products;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FavoriteAdapter.ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new FavoriteAdapter.ViewHolder();
            holder.product_title = (TextView) row.findViewById(R.id.fav_title);
            holder.product_price = (TextView) row.findViewById(R.id.fav_price);
            holder.product_link = (TextView) row.findViewById(R.id.fav_link);
            holder.product_image = (ImageView) row.findViewById(R.id.fav_image);
            row.setTag(holder);
        } else {
            holder = (FavoriteAdapter.ViewHolder) row.getTag();
        }
        final Product item = products.get(position);
        holder.product_title.setText(item.title);
        holder.product_price.setText(item.price);
        holder.product_link.setText(item.link);
        holder.product_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(item.link));
                context.startActivity(i);
            }
        });
        Glide.with(getContext()).load(item.image).asBitmap().placeholder(R.drawable.img).into(holder.product_image);
        Log.e(LOG_TAG,item.image);
        return row;
    }



    static class ViewHolder {
        TextView product_title;
        TextView product_price;
        TextView product_link;
        ImageView product_image;
    }
}

