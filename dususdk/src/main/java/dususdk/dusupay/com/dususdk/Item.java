package dususdk.dusupay.com.dususdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pkanye on 30-Nov-17.
 */

public class Item implements Parcelable {

    private String id;
    private int image;
    private String text;

    public Item(String id,int image, String text){
        this.id = id;
        this.image = image;
        this.text = text;
    }

    public String getId(){
        return( this.id);
    }
    public int getImage(){
        return( this.image);
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.image);
        dest.writeString(this.text);
    }

    protected Item(Parcel in) {
        this.image = in.readInt();
        this.text = in.readString();
    }

    public static final Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
