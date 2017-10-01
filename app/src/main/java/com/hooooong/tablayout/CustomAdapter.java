package com.hooooong.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Android Hong on 2017-09-27.
 */

/**
 * Pager Adapter 에 Fragment 배열을 넘겨
 * 동작하게 한다.
 */
public class CustomAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT = 4;

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new TwoFragment();
            case 2:
                return new ThreeFragment();
            case 3:
                return new FourFragment();
            default:
                return new OneFragment();
            // 그냥 New 로 생성을 하게 되면
            // 마지막에 참조하지 않으면 GC 에서는 삭제 처리가 된다.
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
