package jegan.nbaprimetime;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class NBAStatTracker extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set layout view to NBACasual's main layout
        setContentView(R.layout.activity_nbastat_tracker);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            HomeDescription fragment = new HomeDescription();
            ft.add(R.id.displayFragment, fragment);
            ft.addToBackStack("");
            ft.commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // creates and adds an action bar menu to the app
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            // If NBA teams item is selected
            case R.id.teamListing:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Displays NBA teams in a ListView
                DisplayNBATeams displayNBATeamsFragment = new DisplayNBATeams();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.displayFragment, displayNBATeamsFragment);
                ft.addToBackStack(null);
                ft.commit();

                return true;

            // If NBA leaders item is selected
            case R.id.leaderListing:
                // hide the keyboard if it is showing
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Displays NBA daily leaders in a listview
                DisplayNBALeaders displayNBALeadersFragment = new DisplayNBALeaders();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.displayFragment, displayNBALeadersFragment);
                ft.commit();

                return true;

            // If Settings Help item is selected
            case R.id.help_settings:
                // hide the keyboard if it is showing
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Displays Help information
                DisplayHelp help = new DisplayHelp();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.displayFragment, help);
                ft.commit();

                return true;

            // Displays About information
            case R.id.about_settings:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                DisplayAbout about = new DisplayAbout();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.displayFragment, about);
                ft.commit();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
