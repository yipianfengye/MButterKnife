package uuzuche.com.mbutterknife;

import android.app.Application;

/**
 * Created by liuchao on 2016/1/26.
 */
public class MApplication extends Application{

    public String name;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
