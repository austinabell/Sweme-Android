package austinabell8.sweme;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements FeedFragment.OnFragmentInteractionListener,
        DiscoverFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        View.OnClickListener {

    private Fragment mFragment;

    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private List<View> viewList;
    private Button mLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        View view = navigation.findViewById(R.id.navigation_discover);
        view.performClick();

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
            case R.id.action_logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    private void initView() {
        View feedView = getLayoutInflater().inflate(R.layout.fragment_feed, null);
        View discoverView = getLayoutInflater().inflate(R.layout.fragment_discover, null);
        View profileView = getLayoutInflater().inflate(R.layout.fragment_profile, null);

        viewList = new ArrayList<>();
        viewList.add(feedView);
        viewList.add(discoverView);
        viewList.add(profileView);

        viewPager = (ViewPager) findViewById(R.id.view_pager_bottom_navigation);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
//        viewPager.setPageTransformer(true, new MyPageTransformer());

        mLogoutButton = (Button) profileView.findViewById(R.id.log_out_button);
        mLogoutButton.setOnClickListener(this);

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
                    return true;
                case R.id.navigation_discover:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(2);
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

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
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
            MainActivity.this.startActivity(openIntent);

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
}