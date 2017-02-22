package com.android.lynks.products;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment  extends Fragment {


    private static final String LOG = "Fav Fragment";

    @BindView(R.id.grid2)
    GridView grid2;
    ArrayList<Product> list = new ArrayList<>();
    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_fragment, container, false);
        ButterKnife.bind(this, rootView);
        MySQLiteHelper db = new MySQLiteHelper(getActivity());
        list = db.getAllFavorites();
        FavoriteAdapter adapter = new FavoriteAdapter(getActivity(), R.layout.favorite_listview, list);
        grid2.setAdapter(adapter);

        return rootView;
    }
}