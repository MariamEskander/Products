package com.android.lynks.products;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsDetailsFragment extends Fragment {


    private static final String LOG = "Details Fragment" ;
    @BindView(R.id.favorite)
    TextView favorite;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.link)
    TextView link;
    @BindView(R.id.pimage)
    ImageView pimage;
    boolean f;
    public ProductsDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, rootView);
        title.setText(getActivity().getIntent().getStringExtra("title"));
        price.setText(getActivity().getIntent().getStringExtra("price"));
        link.setText(getActivity().getIntent().getStringExtra("link"));
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getActivity().getIntent().getStringExtra("link")));
                startActivity(i);
            }
        });
        Glide.with(getActivity()).load(getActivity().getIntent().getStringExtra("image")).asBitmap().placeholder(R.drawable.img).into(pimage);
        MySQLiteHelper db = new MySQLiteHelper(getActivity());
        f = db.CheckExist(Integer.parseInt(getActivity().getIntent().getStringExtra("id")));
        if (f == true)
            favorite.setBackgroundColor(Color.GRAY);
        else
           favorite.setBackgroundColor(Color.RED);
        return rootView;
    }

    @OnClick(R.id.favorite)
    public void AddToFavorite(){
        if (f==true){
            favorite.setBackgroundColor(Color.RED);
            f = false;
            MySQLiteHelper db = new MySQLiteHelper(getActivity());
            db.deleteFavorite(new Product(Integer.parseInt(getActivity().getIntent().getStringExtra("id")),
                    getActivity().getIntent().getStringExtra("title"),
                    getActivity().getIntent().getStringExtra("price"),
                    getActivity().getIntent().getStringExtra("link"),
                    getActivity().getIntent().getStringExtra("image")));
        }
        else if (f==false) {
            favorite.setBackgroundColor(Color.GRAY);
            f = true;
            MySQLiteHelper db = new MySQLiteHelper(getActivity());
            db.addFavorite(new Product(Integer.parseInt(getActivity().getIntent().getStringExtra("id")),
                    getActivity().getIntent().getStringExtra("title"),
                    getActivity().getIntent().getStringExtra("price"),
                    getActivity().getIntent().getStringExtra("link"),
                    getActivity().getIntent().getStringExtra("image")));

        }
    }

}
