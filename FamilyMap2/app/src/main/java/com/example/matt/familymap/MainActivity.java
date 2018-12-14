package com.example.matt.familymap;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;



public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        loginFragment = new LoginFragment();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, loginFragment).commit();
    }

    public void openMap(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        for(int i = 0; i < 3; i++){
            menu.getItem(i).setVisible(true);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {}

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}
}

