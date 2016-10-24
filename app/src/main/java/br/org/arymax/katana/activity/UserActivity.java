package br.org.arymax.katana.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import br.org.arymax.katana.R;
import br.org.arymax.katana.broadcastreceiver.NotificationReceiver;
import br.org.arymax.katana.fragment.HomeFragment;
import br.org.arymax.katana.fragment.MakeQuestionFragment;
import br.org.arymax.katana.fragment.MyQuestionsFragment;
import br.org.arymax.katana.fragment.UserInfoFragment;
import br.org.arymax.katana.http.QuestionGetTask;
import br.org.arymax.katana.utility.Constants;

import static java.lang.System.out;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    public static Menu mMenu;
    private NavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_home);
        //Intent intent= new Intent(this, NotificationReceiver.class);
        //startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, 0);
        String prontuario = preferences.getString("prontuario", "");
        String nome = preferences.getString("nome", "");

        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        mNavigation.setNavigationItemSelectedListener(this);

        View headerView = mNavigation.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.txtNavUser)).setText(nome);
        ((TextView) headerView.findViewById(R.id.textView)).setText(prontuario);

        Fragment home = fragmentManager.findFragmentByTag(HomeFragment.HOME_FRAGMENT_TAG);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(home == null){
            fragmentTransaction.add(R.id.content_user, new HomeFragment(), HomeFragment.HOME_FRAGMENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.content_user, home, HomeFragment.HOME_FRAGMENT_TAG);
        }
        fragmentTransaction.commit();
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
        mNavigation.setCheckedItem(item.getItemId());

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(true);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(false);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(true);
            getSupportActionBar().setTitle(R.string.action_home);


            Fragment home = fragmentManager.findFragmentByTag(HomeFragment.HOME_FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(home == null){
                fragmentTransaction.add(R.id.content_user, new HomeFragment(), HomeFragment.HOME_FRAGMENT_TAG);
            } else {
                fragmentTransaction.replace(R.id.content_user, home, HomeFragment.HOME_FRAGMENT_TAG);
            }
            fragmentTransaction.commit();

        } else if (id == R.id.nav_make_question)
        {
            getSupportActionBar().setTitle(R.string.action_make_question);
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(false);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(true);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(false);

            Fragment mqFragment = fragmentManager.findFragmentByTag(MakeQuestionFragment.MAKE_QUESTION_FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(mqFragment == null){
                fragmentTransaction.add(R.id.content_user, new MakeQuestionFragment(), MakeQuestionFragment.MAKE_QUESTION_FRAGMENT_TAG);
            } else {
                fragmentTransaction.replace(R.id.content_user, mqFragment, MakeQuestionFragment.MAKE_QUESTION_FRAGMENT_TAG);
            }
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_my_profile)
        {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(true);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(false);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(false);
            getSupportActionBar().setTitle(R.string.action_my_profile);

            Fragment usiFragment = fragmentManager.findFragmentByTag(UserInfoFragment.USER_INFO_FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(usiFragment == null){
                fragmentTransaction.add(R.id.content_user, new UserInfoFragment(), UserInfoFragment.USER_INFO_FRAGMENT_TAG);
            } else {
                fragmentTransaction.replace(R.id.content_user, usiFragment, UserInfoFragment.USER_INFO_FRAGMENT_TAG);
            }
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_my_answers)
        {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(true);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(false);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(true);
            getSupportActionBar().setTitle(R.string.action_my_answers);


        }


        else if (id == R.id.nav_my_questions)
        {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(true);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(false);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(true);
            getSupportActionBar().setTitle(R.string.action_my_questions);


            Fragment mqFragment = fragmentManager.findFragmentByTag(MyQuestionsFragment.My_QUESTIONS_FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(mqFragment == null){
                fragmentTransaction.add(R.id.content_user, new MyQuestionsFragment(), MyQuestionsFragment.My_QUESTIONS_FRAGMENT_TAG);
            } else {
                fragmentTransaction.replace(R.id.content_user, mqFragment, MyQuestionsFragment.My_QUESTIONS_FRAGMENT_TAG);
            }
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_settings) {
            MenuItem settings = mMenu.findItem(R.id.action_settings);
            settings.setVisible(true);
            MenuItem send = mMenu.findItem(R.id.action_enviar);
            send.setVisible(false);
            MenuItem search = mMenu.findItem(R.id.action_search);
            search.setVisible(false);

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
