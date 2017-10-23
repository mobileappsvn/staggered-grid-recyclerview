package robert.com.demo.staggered.staggeredlayoutdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by robert on 10/19/17.
 */

public class StaggeredEntity implements Parcelable {
    public SpacesItemDecoration.ALIGNMENT text;
    public int height;
    public int spanIndex;
    public boolean visible;// = true if text = SpacesItemDecoration.ALIGNMENT.RIGHT

    public StaggeredEntity(SpacesItemDecoration.ALIGNMENT text, int spanIndex, int height, boolean visible) {
        this.text = text;
        this.spanIndex = spanIndex;
        this.height = height;
        this.visible = visible;
    }

    public StaggeredEntity(Parcel in) {
        this.text = (in.readByte() == 0 ? SpacesItemDecoration.ALIGNMENT.LEFT : SpacesItemDecoration.ALIGNMENT.RIGHT);
        this.spanIndex = in.readInt();
        this.height = in.readInt();
        this.visible = in.readByte() != 0;
    }

    public static final Creator<StaggeredEntity> CREATOR = new Creator<StaggeredEntity>() {
        @Override
        public StaggeredEntity createFromParcel(Parcel in) {
            return new StaggeredEntity(in);
        }

        @Override
        public StaggeredEntity[] newArray(int size) {
            return new StaggeredEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((text == SpacesItemDecoration.ALIGNMENT.LEFT) ? (byte) 0 : (byte) 1);
        dest.writeInt(spanIndex);
        dest.writeInt(height);
        dest.writeByte(visible ? (byte) 1 : (byte) 0);
    }
}
