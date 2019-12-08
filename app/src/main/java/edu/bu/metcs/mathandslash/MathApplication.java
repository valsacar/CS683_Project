package edu.bu.metcs.mathandslash;

import android.app.Application;
import android.content.Context;

/*Using this so I can access string resources from outside of activities (for example in MathBattle)*/
public class MathApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
