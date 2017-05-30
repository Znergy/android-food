package com.example.guest.myrestaurant;

import android.os.Build;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class RestaurantsListActivityTest {
    private RestaurantsListActivity activity;
    private ListView mRestaurantListView;

//    @Before
//    public void setup() {
//        activity = Robolectric.setupActivity(RestaurantsListActivity.class);
//        mRestaurantListView = (ListView) activity.findViewById(R.id.listView);
//    }

    @Test
    public void restaurantListViewPopulates() {
        assertNotNull(mRestaurantListView.getAdapter());
        assertEquals(mRestaurantListView.getAdapter().getCount(), 15);
    }

}
