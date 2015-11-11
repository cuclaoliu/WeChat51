package edu.cuc.stephen.wechat51;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;
    private LinearLayout mLinearLayoutChat;

    private TextView mTextViewChat;
    private TextView mTextViewFriend;
    private TextView mTextViewContact;
    private ImageView mImageViewTabLine;
    private BadgeView mBadgeView;
    private int mScreenThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabLine();

        initView();
    }

    private void initTabLine() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreenThird = outMetrics.widthPixels / 3;

        mImageViewTabLine = (ImageView) findViewById(R.id.id_iv_tab_line);
        ViewGroup.LayoutParams layoutParams = mImageViewTabLine.getLayoutParams();
        layoutParams.width = mScreenThird;
        mImageViewTabLine.setLayoutParams(layoutParams);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        mTextViewChat = (TextView) findViewById(R.id.id_tv_chat);
        mTextViewFriend = (TextView) findViewById(R.id.id_tv_friend);
        mTextViewContact = (TextView) findViewById(R.id.id_tv_contact);
        mLinearLayoutChat = (LinearLayout) findViewById(R.id.id_ll_chat);

        mFragmentList = new ArrayList<>();
        ChatMainTabFragment chatMainTabFragment = new ChatMainTabFragment();
        FriendMainTabFragment friendMainTabFragment = new FriendMainTabFragment();
        ContactMainTabFragment contactMainTabFragment = new ContactMainTabFragment();
        mFragmentList.add(chatMainTabFragment);
        mFragmentList.add(friendMainTabFragment);
        mFragmentList.add(contactMainTabFragment);
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentList.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        final int halfGreen = 0xff008000;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override   //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPix) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImageViewTabLine.getLayoutParams();
                layoutParams.leftMargin = (int) ((positionOffset + position) * mScreenThird);
                mImageViewTabLine.setLayoutParams(layoutParams);
            }

            @Override       //滑动结束
            public void onPageSelected(int position) {
                resetTexView();
                switch (position) {
                    case 0:
                        if (mBadgeView != null)
                            mLinearLayoutChat.removeView(mBadgeView);
                        mBadgeView = new BadgeView(MainActivity.this);
                        mBadgeView.setBadgeCount(333);
                        mLinearLayoutChat.addView(mBadgeView);
                        mTextViewChat.setTextColor(halfGreen);
                        break;
                    case 1:
                        mTextViewFriend.setTextColor(halfGreen);
                        break;
                    case 2:
                        mTextViewContact.setTextColor(halfGreen);
                }
            }

            private void resetTexView() {
                mTextViewChat.setTextColor(Color.BLACK);
                mTextViewFriend.setTextColor(Color.BLACK);
                mTextViewContact.setTextColor(Color.BLACK);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
