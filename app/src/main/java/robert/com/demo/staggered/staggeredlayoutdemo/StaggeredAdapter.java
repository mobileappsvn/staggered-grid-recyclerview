package robert.com.demo.staggered.staggeredlayoutdemo;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by robert on 10/19/17.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {
    private int[] mHeight;
    private String[] mDataSet;
    private Context mContext;
    private Random mRandom = new Random();

    public StaggeredAdapter(Context context, String[] DataSet, int[] mHeight) {
        this.mContext = context;
        this.mDataSet = DataSet;
        this.mHeight = mHeight;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRobert;
        public TextView mTxtHoang;
        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mTxtRobert = (TextView) v.findViewById(R.id.robert);
            mTxtHoang = (TextView) v.findViewById(R.id.hoang);
            mCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    @Override
    public StaggeredAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Set a random height for TextView
        holder.mCardView.getLayoutParams().height = mHeight[position];//getRandomIntInRange(250, 75);
        // Set a random color for TextView background
        holder.mCardView.setBackgroundColor(getRandomHSVColor());

        Log.i("StaggeredAdapter", mDataSet[position] + "|pos=" + position + "|AdapterPos="
                + holder.getAdapterPosition() + "|LayoutPos=" + holder.getLayoutPosition()
                + "|ItemId=" + holder.getItemId());

        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(false);
            holder.itemView.setLayoutParams(sglp);
        }
        int[] originalPos = new int[2];
        holder.mCardView.getLocationInWindow(originalPos);
        //or view.getLocationOnScreen(originalPos)
        int x = originalPos[0];
        int y = originalPos[1];

        //RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
        //Log.e("StaggeredAdapter", "-->lp.viewNeedsUpdate()=" + lp.viewNeedsUpdate());
        StaggeredGridLayoutManager.LayoutParams lp1 = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        Log.e("StaggeredAdapter", "-->lp.getSpanIndex()=" + lp1.getSpanIndex());
        Log.e("StaggeredAdapter", "------------------------------------------------------");

    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    // Custom method to get a random number between a range
    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt((max - min) + min) + min;
    }

    // Custom method to generate random HSV color
    protected int getRandomHSVColor() {
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 1.0f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 255;
        // Finally, generate the color
        int color = Color.HSVToColor(alpha, new float[]{hue, saturation, value});
        // Return the color
        return color;
    }
}