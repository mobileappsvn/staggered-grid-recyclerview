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
    private final int mSpace;

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        Log.e("SpacesItemDecoration", "-->view.getClass().getSimpleName()=" + view.getClass().getSimpleName() + "-->getParent=" + parent.getParent().getClass().getSimpleName());

        int position = parent.getChildAdapterPosition(view);

        StaggeredAdapter.ViewHolder vh = new StaggeredAdapter.ViewHolder(view);

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view .getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if(position > 0){
            if(spanIndex == 1) {
                outRect.left = mSpace;
                Log.e("SpacesItemDecoration", "-->(align on right)position=" + position);
                vh.mTextView.setText("RIGHT");
            } else{
                outRect.right = 100*mSpace;
                Log.e("SpacesItemDecoration", "<--(align on left)position=" + position);
                vh.mTextView.setText("LEFT");
            }

            outRect.bottom = mSpace * 2;
        }
    }
}
