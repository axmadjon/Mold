package uz.mold.test;

import android.os.Parcel;
import android.os.Parcelable;

public class TestArgMessage implements Parcelable {

    public final String message;

    public TestArgMessage(String message) {
        this.message = message;
    }

    protected TestArgMessage(Parcel in) {
        message = in.readString();
    }

    public static final Creator<TestArgMessage> CREATOR = new Creator<TestArgMessage>() {
        @Override
        public TestArgMessage createFromParcel(Parcel in) {
            return new TestArgMessage(in);
        }

        @Override
        public TestArgMessage[] newArray(int size) {
            return new TestArgMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
    }
}
