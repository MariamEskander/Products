package com.android.lynks.products;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.lynks.products.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteDb";
    private static final String TABLE_FAVORITE = "favorite";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PRICE = "price";
    private static final String KEY_LINK = "link";
    private static final String KEY_IMAGE = "image";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_PRICE,KEY_LINK,KEY_IMAGE};
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE favorite ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "price TEXT, "+
                "link TEXT, "+
                "image TEXT )";

        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }



    public void addFavorite(Product product){
        Log.e("addFavorite", product.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID,product.id);
        values.put(KEY_TITLE, product.title);
        values.put(KEY_PRICE, product.price);
        values.put(KEY_LINK, product.link);
        values.put(KEY_IMAGE, product.image);

        // 3. insert
        db.insert(TABLE_FAVORITE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    public ArrayList<Product> getAllFavorites() {
        ArrayList<Product> products = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FAVORITE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Product product = null;
        if (cursor.moveToFirst()) {
            do {
                product = new Product();
                product.setId(cursor.getInt(0));
                product.setTitle(cursor.getString(1));
                product.setPrice(cursor.getString(2));
                product.setLink(cursor.getString(3));
                product.setImage(cursor.getString(4));
                products.add(product);
            } while (cursor.moveToNext());
        }

        Log.e("getAllFavorites()", products.toString());
        return products;
    }

    public void deleteFavorite(Product product) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FAVORITE,
                KEY_ID+" = ?",
                new String[] {String.valueOf( product.getId() )});

        // 3. close
        db.close();

        Log.e("deleteFavorie", product.toString());

    }

    public boolean CheckExist(int id){
        boolean flag =  false;
        String query = "SELECT  * FROM " + TABLE_FAVORITE+" WHERE id="+id;
        Cursor cursor= getReadableDatabase().rawQuery(query,null);

        if(cursor.getCount()>0){
           return true;
        }else{
            return false;
        }
    }

}