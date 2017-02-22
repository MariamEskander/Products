package com.android.lynks.products;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsActivityFragment extends Fragment {

    @BindView(R.id.grid)
    GridView grid;
    ArrayList<Product> arrayList= new ArrayList<>();;

    public ProductsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, rootView);
        String s= loadJSONFromAsset();
        try {

            JSONObject obj = new JSONObject(s);
            JSONArray jsonArray = obj.getJSONArray("products");
            for (int i = 0;i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Product product = new Product(Integer.parseInt(String.valueOf(jsonObject.get("id"))),
                        String.valueOf(jsonObject.get("title")),String.valueOf(jsonObject.get("price")),
                        String.valueOf(jsonObject.get("url")),String.valueOf(jsonObject.get("photo")));
                arrayList.add(product);
            }
            Log.i("My App", arrayList.toString());

        } catch (Exception e) {
            Log.e("My App", "Could not parse malformed JSON: \"" + s + "\"");
            e.printStackTrace();
        }
        if ( arrayList != null && arrayList.size()> 0) {
            ProductAdapter adapter = new ProductAdapter(getActivity(), R.layout.product_listview, arrayList);
            grid.setAdapter(adapter);
        }
        return rootView;
    }

    public String loadJSONFromAsset() {
        String json = null;
            InputStream is = null;
            try {
                is = getActivity().getAssets().open("db.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        return json;
    }
}
