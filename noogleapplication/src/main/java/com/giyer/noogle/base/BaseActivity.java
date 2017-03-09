package com.giyer.noogle.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.giyer.noogle.R;

/**
 * Created by giyer7 on 3/7/17.
 */

public abstract class BaseActivity extends BaseEventBusActivity implements BaseFragment.BaseFragmentListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        BottomNavigationView navigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_recent:
                                Toast.makeText(getApplicationContext(), "Recent Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_feed:
                                Toast.makeText(getApplicationContext(), "Feed Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_category:
                                Toast.makeText(getApplicationContext(), "Category Clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showSnackBar(View view, String message) {

    }

    @Override
    public void showUIBlockingProgress() {

    }

    @Override
    public void hideUIBlockingProgress() {

    }

    @Override
    public void showHorizontalProgressBar() {

    }

    @Override
    public void hideHorizontalProgressBar() {

    }

    @Override
    public void allowViewToScroll(boolean allowViewToScroll) {

    }

    @Override
    public void dismissKeyboard(View viewHoldingKeyboardForum) {

    }

    @Override
    public void showKeyboard(EditText editText) {

    }

    @Override
    public void onFragmentActionBack() {

    }

    @Override
    public void onRequestTitleUpdate(String titleResource) {

    }

    @Override
    public void onFragmentActionFinish() {

    }
}
