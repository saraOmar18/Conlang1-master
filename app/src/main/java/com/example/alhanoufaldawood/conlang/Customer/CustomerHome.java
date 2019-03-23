package com.example.alhanoufaldawood.conlang.Customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.alhanoufaldawood.conlang.R;
import com.example.alhanoufaldawood.conlang.ReportIssue;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

/////////////// noooooo////////////////
        //Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().show();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().show();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //Action item
        navigationView = (NavigationView) findViewById(R.id.NavigationView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                switch (item.getItemId()) {

                   case R.id.reportIssue:
                       Intent reportIssue = new Intent(CustomerHome.this, ReportIssue.class);
                       startActivity(reportIssue);
                        break;

                    case R.id.profile:
                        // ToDo navigate to profile page
                         Intent profilePage = new Intent(CustomerHome.this, Profile.class);
                         startActivity(profilePage);
                        break;

                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(CustomerHome.this, LoginCustomer.class));

                        break;

                }



                return true;
            }
     });


/////////// //////////////


        BottomNavigationView bottomNavigation = findViewById(R.id.customer_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new TranslatorsListFragment()).commit();
    }
///////nnnnooo///////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+title+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));

    }

    ///////////////

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){

                case R.id.translators_list:
                    selectedFragment = new TranslatorsListFragment();
                    break;

                case R.id.orders:
                    selectedFragment = new OrdersFragment();
                    break;



            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        }

    };
}
