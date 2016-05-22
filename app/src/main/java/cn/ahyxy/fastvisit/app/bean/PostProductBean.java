package cn.ahyxy.fastvisit.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zack on 2016/5/22.
 */
public class PostProductBean implements Parcelable {
    private int id;
    @Expose(serialize = true, deserialize = false)
    private String name;
    @SerializedName("c_price")
    private String priceNumber;
    private String spec_name;
    private String unit_name;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(String priceNumber) {
        this.priceNumber = priceNumber;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    protected PostProductBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        priceNumber = in.readString();
        spec_name = in.readString();
        unit_name = in.readString();
        count = in.readInt();
    }

    public static final Creator<PostProductBean> CREATOR = new Creator<PostProductBean>() {
        @Override
        public PostProductBean createFromParcel(Parcel in) {
            return new PostProductBean(in);
        }

        @Override
        public PostProductBean[] newArray(int size) {
            return new PostProductBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(priceNumber);
        dest.writeString(spec_name);
        dest.writeString(unit_name);
        dest.writeInt(count);
    }
}
