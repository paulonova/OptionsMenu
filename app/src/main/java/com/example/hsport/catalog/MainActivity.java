package com.example.hsport.catalog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ITEM_LOGOUT = 1001;
    private CoordinatorLayout coordinatorLayout;

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
                Snackbar.make(view, "You pressed the FAB", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(getString(R.string.lorem_ipsum) + "\n\n");
        }
        TextView tv = (TextView) findViewById(R.id.longText);
        tv.setText(builder.toString());

        //Initiate the Image..
        ImageView iv = (ImageView) findViewById(R.id.photo);
        String imageName = "jacket101";

        // Simple way!
//        int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
//        iv.setImageResource(res);

        // Bättre
        try {
            InputStream stream = getAssets().open(imageName + ".png");
            Drawable d = Drawable.createFromStream(stream, null);
            iv.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //      groupId, itemId, order, CharSequence);
        menu.add(0,MENU_ITEM_LOGOUT,102, R.string.logout);

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
                Snackbar.make(coordinatorLayout, "You selected about", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;

            case MENU_ITEM_LOGOUT:
                Snackbar.make(coordinatorLayout, "You selected logout", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
