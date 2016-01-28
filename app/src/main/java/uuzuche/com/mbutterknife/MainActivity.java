package uuzuche.com.mbutterknife;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Map;

import uuzuche.com.mbutterknife.parser.OnClick;
import uuzuche.com.mbutterknife.parser.ViewBinder;
import uuzuche.com.mbutterknife.parser.ViewBinderParser;

public class MainActivity extends AppCompatActivity {

    @ViewBinder(id = R.id.button1)
    public Button button1;

    /**
     * 执行button1的点击事件
     */
    @OnClick(id = R.id.button1)
    public void button1OnClick() {
        Log.i("tag", "这是一个测试的例子");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewBinderParser.inject(MainActivity.this);
    }

}
