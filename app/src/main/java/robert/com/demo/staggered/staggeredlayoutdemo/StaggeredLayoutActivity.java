package robert.com.demo.staggered.staggeredlayoutdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.Random;

public class StaggeredLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int[] mHeight = null;
    private Random mRandom = new Random();
    // Initialize a new String array
    String[] items = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_layout);

        // Get the application context
        mContext = getApplicationContext();

        // Change the action bar color
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mHeight = new int[items.length];

        refreshHeight(items.length);

        /*
            StaggeredGridLayoutManager
                A LayoutManager that lays out children in a staggered grid formation. It supports
                horizontal & vertical layout as well as an ability to layout children in reverse.

                Staggered grids are likely to have gaps at the edges of the layout. To avoid these
                gaps, StaggeredGridLayoutManager can offset spans independently or move items
                between spans. You can control this behavior via setGapStrategy(int).
        */
        /*
            public StaggeredGridLayoutManager (int spanCount, int orientation)
                Creates a StaggeredGridLayoutManager with given parameters.

            Parameters
                spanCount : If orientation is vertical, spanCount is number of columns.
                    If orientation is horizontal, spanCount is number of rows.
                orientation : VERTICAL or HORIZONTAL
        */
        // Define a layout for RecyclerView
        mLayoutManager = mStaggeredGridLayoutManager;//new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new StaggeredAdapter(mContext, items, mHeight);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(1);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }
    final StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
        @Override
        public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);

            Log.e("StaggeredLayoutActivity", "-->state.toString=" + state.toString());
        }
    };

    private void refreshHeight(int length) {
        if (mHeight == null) mHeight = new int[length];

        for(int idx = 0; idx < mHeight.length; idx++) {
            mHeight[idx] = getRandomIntInRange(250, 75);
        }
    }

    // Custom method to get a random number between a range
    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
    }

    @Override
    public void onRefresh() {
        refreshHeight(items.length);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}