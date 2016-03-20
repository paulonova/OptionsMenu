package com.example.hsport.catalog;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;
    public static final String PRODUCT_ID = "PRODUCT_ID";
    private static final int DETAIL_REQUEST = 111;
    public static final String RETURN_MESSAGE = "RETURN_MESSAGE";

    private CoordinatorLayout coordinatorLayout;

    private static String webUrl = "https://www.facebook.com/paulo.vilanova.12";
    private static String email = "paulonova144@gmail.com";
    private List<Product> products = DataProvider.productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Send email", "");
                String[] TO = {email};
                String[] CC = {email};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                if(emailIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Toast.makeText(MainActivity.this, "Sending email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String[] items = getResources().getStringArray(R.array.clothing);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                                                                      android.R.id.text1, items);

        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.list_item, products);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Product product = products.get(position);
                intent.putExtra(PRODUCT_ID,  product.getProductId());

                startActivityForResult(intent, DETAIL_REQUEST);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //      groupId, itemId, order, CharSequence);
        menu.add(0, MENU_ITEM_LOGOUT, 102, R.string.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //I can create more settings..
        switch (id){
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout, "You selected settings", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;

            case R.id.action_about:
                //Snackbar.make(coordinatorLayout, "You selected about", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_web:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                if(webIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(webIntent);
                }
                return true;

            case R.id.action_cart:
                Snackbar.make(coordinatorLayout, "You selected the shopping cart", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;

            case MENU_ITEM_LOGOUT:
                Snackbar.make(coordinatorLayout, "You selected logout", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == DETAIL_REQUEST){
            if(resultCode == RESULT_OK){
                String message = data.getStringExtra(RETURN_MESSAGE);
                Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).setAction("Go to cart", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Going to cart", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        }
    }




}
