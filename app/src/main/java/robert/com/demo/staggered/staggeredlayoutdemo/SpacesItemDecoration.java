package robert.com.demo.staggered.staggeredlayoutdemo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by robert on 10/19/17.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private String TAG = "SpacesItemDecoration";
    public enum ALIGN {
        LEFT(0), RIGHT(1);
        private int value;

        ALIGN(int value) {
            this.value = value;
        }
    }

    public enum ALIGNMENT {
        LEFT,
        RIGHT;
    }

    public interface AlignmentDetect {
        void readyUpdate(int position, int spanIndex, ALIGNMENT alignment);
    }

    private final int mSpace;
    AlignmentDetect mAlignmentDetectListener;

    public SpacesItemDecoration(int space, AlignmentDetect mAlignmentDetectListener) {
        this.mSpace = space;
        this.mAlignmentDetectListener = mAlignmentDetectListener;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        Log.e(TAG, "-->view.getClass().getSimpleName()=" + view.getClass().getSimpleName() + "-->getParent=" + parent.getParent().getClass().getSimpleName());
        outRect.left = outRect.right = outRect.bottom = mSpace;

        int position = parent.getChildAdapterPosition(view);

        StaggeredAdapter.ViewHolder vh = new StaggeredAdapter.ViewHolder(view);

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view .getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if(spanIndex == 1 && position > 0) {
            //outRect.left = mSpace;
            Log.e(TAG, "-->(align on right)position=" + position);
            vh.mTxtRobert.setVisibility(View.GONE);
            vh.mTxtHoang.setVisibility(View.VISIBLE);
            mAlignmentDetectListener.readyUpdate(position, spanIndex, ALIGNMENT.RIGHT);
        } else {
            //outRect.right = 100*mSpace;
            Log.e(TAG, "<--(align on left)position=" + position);
            vh.mTxtHoang.setVisibility(View.GONE);
            vh.mTxtRobert.setVisibility(View.VISIBLE);
            mAlignmentDetectListener.readyUpdate(position, spanIndex, ALIGNMENT.LEFT);
        }
        //outRect.bottom = mSpace * 2;

    }
}
