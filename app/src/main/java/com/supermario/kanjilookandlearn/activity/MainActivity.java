package com.supermario.kanjilookandlearn.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.supermario.kanjilookandlearn.R;
import com.supermario.kanjilookandlearn.data.Kanji;
import com.supermario.kanjilookandlearn.database.DatabaseHandler;
import com.supermario.kanjilookandlearn.database.KanjiProvider;
import com.supermario.kanjilookandlearn.view.ContentFragment;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KANJI_LIST_FRAGMENT = "kanj_list_fragment";
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toast toast;
    private boolean isShowFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            if(DatabaseHandler.isHavingDatabase() == false ){
                DatabaseHandler.createDB(this);
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent();
        }

        initView();

    }

    private void initView() {
        initId();

    }

    private void initId() {

    }

    private void setupDrawerContent() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.getMenu().getItem(0).setChecked(true);
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.top:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment, KANJI_LIST_FRAGMENT);
                        fragmentTransaction.commit();
                        return true;


                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search));
        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Kanji> list;
                if (newText == null || newText.equals("")) {
                    list = KanjiProvider.getAll(MainActivity.this);
                } else {
                    list = KanjiProvider.searchKanji(MainActivity.this, newText);

                }
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);
                if (f instanceof ContentFragment) {
                    String text = "";
                    ContentFragment contentFragment = (ContentFragment) f;

                    if (list != null) {
                        contentFragment.updateKanjiList(list);
                    }


                }


                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_show_favorite) {
            if(isShowFavorite){
                isShowFavorite = false;
                item.setIcon(R.drawable.icon_favorite_not_show);
                showFavorite(false);
            }else{
                isShowFavorite = true;
                item.setIcon(R.drawable.icon_favorite_show);
                showFavorite(true);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFavorite(boolean isShowFavorite) {
        ArrayList<Kanji> list;
        if (isShowFavorite) {
            list = KanjiProvider.getAllFavorite(MainActivity.this);
        }else{
            list = KanjiProvider.getAll(MainActivity.this);

        }
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (f instanceof ContentFragment){
            String text = "";
            ContentFragment contentFragment = (ContentFragment)f;

            if(list != null){
                contentFragment.updateKanjiList(list);
            }else{
                contentFragment.updateKanjiList(new ArrayList<Kanji>());
            }


        }
    }
}
