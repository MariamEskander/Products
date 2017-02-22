package com.android.lynks.products;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter  extends ArrayAdapter<Product> {
    private static final String LOG_TAG = ProductAdapter.class.getSimpleName();
    Context context;
    int layoutResourceId;
    ArrayList<Product> products = new ArrayList<>();
    public ProductAdapter(Activity context,int layoutResourceId, ArrayList<Product> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId=layoutResourceId;
        this.context=context;
        this.products=products;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        final Product item = products.get(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.product_title = (TextView) row.findViewById(R.id.product_title);
            holder.product_price = (TextView) row.findViewById(R.id.product_price);
            holder.product_image = (ImageView) row.findViewById(R.id.product_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.product_title.setText(item.title);
        holder.product_price.setText(item.price);

        Log.e(LOG_TAG,"title " + item.title + "\n" + "price " + item.price);

        Glide.with(getContext()).load(item.image).asBitmap().placeholder(R.drawable.img).into(holder.product_image);

        row.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("title",item.title);
        intent.putExtra("price",item.price);
        intent.putExtra("link",item.link);
        intent.putExtra("image",item.image);
        intent.putExtra("id",String.valueOf(item.id));
        context.startActivity(intent);
        }
    });
        return row;
    }



    static class ViewHolder {
        TextView product_title;
        TextView product_price;
        ImageView product_image;
     }
}

