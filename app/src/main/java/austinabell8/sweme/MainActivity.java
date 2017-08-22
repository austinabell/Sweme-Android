package austinabell8.sweme;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import austinabell8.sweme.helpers.LockableViewPager;

public class MainActivity extends AppCompatActivity
        implements FeedFragment.OnFragmentInteractionListener,
        DiscoverFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    private FeedFragment mFeedFragment;
    private DiscoverFragment mDiscoverFragment;
    private ProfileFragment mProfileFragment;
    private LinearLayout mLinearLayout;

    private LockableViewPager viewPager;
    private BottomNavigationView navigation;

    private boolean doubleBackToExitPressedOnce = false;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        //default discover tab on open
        View view = navigation.findViewById(R.id.navigation_discover);
        view.performClick();
        mLinearLayout = (LinearLayout) findViewById(R.id.llMainActivity);
        addBackgroundFade(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.action_logout:
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //create view
    private void initView() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (LockableViewPager) findViewById(R.id.view_pager_bottom_navigation);
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setSwipeLocked(true);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationViewHelper.removeShiftMode(navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    viewPager.setCurrentItem(0);
                    viewPager.setSwipeLocked(false);
                    return true;
                case R.id.navigation_discover:
                    viewPager.setCurrentItem(1);
                    viewPager.setSwipeLocked(true);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(2);
                    viewPager.setSwipeLocked(false);
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_feed);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_discover);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_profile);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    public void logout() {
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Toast.makeText(MainActivity.this, "Logout failed.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent openIntent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(openIntent);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_button:
                logout();
                break;
        }
    }

    public void addBackgroundFade (Boolean b) {
        if (b==true){
            mLinearLayout.setBackgroundResource(R.drawable.primary_fade);
        }
        else {
            mLinearLayout.setBackgroundResource(0);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new FeedFragment();
                    break;
                case 1:
                    fragment = new DiscoverFragment();
                    break;
                case 2:
                    fragment = new ProfileFragment();
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    mFeedFragment = (FeedFragment) createdFragment;
                    break;
                case 1:
                    mDiscoverFragment = (DiscoverFragment) createdFragment;
                    break;
                case 2:
                    mProfileFragment = (ProfileFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }



//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return getString(R.string.title_section1).toUpperCase(l);
//                case 1:
//                    return getString(R.string.title_section2).toUpperCase(l);
//                case 2:
//                    return getString(R.string.title_section3).toUpperCase(l);
//            }
//            return null;
//        }

    }

}