package br.org.arymax.katana.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.org.arymax.katana.R;
import br.org.arymax.katana.fragment.MakeQuestionFragment;
import br.org.arymax.katana.utility.Constants;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.user, mMenu);
        MenuItem send = mMenu.findItem(R.id.action_enviar);
        send.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_make_question)
        {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(false);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(true);
            MakeQuestionFragment mkq = new MakeQuestionFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_user, mkq);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_my_answers)
        {

        }


        else if (id == R.id.nav_my_questions)

        {

        }

        else if (id == R.id.nav_my_profile)
        {

        }

        else if (id == R.id.nav_settings) {

        } else if(id == R.id.nav_logout){
            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("logado", false);
            editor.putLong("pk", -1);
            editor.putString("zze", "");
            editor.putString("prontuario", "");
            Intent intent = new Intent(UserActivity.this ,LoginActivity.class);
            startActivity(intent);
            finish();
            editor.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
