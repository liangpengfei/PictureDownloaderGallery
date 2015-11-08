package xhome.uestcfei.com.picturedownloadergallerydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.annotation.Target;

import xhome.uestcfei.com.picturedownloadergallery.PictureDownloaderGallery;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PictureDownloaderGallery downloaderGallery1;
    private PictureDownloaderGallery downloaderGallery2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        downloaderGallery1 = (PictureDownloaderGallery) findViewById(R.id.downloader1);
        downloaderGallery2 = (PictureDownloaderGallery) findViewById(R.id.downloader2);

        String[] urls1   = {"http://upload.jianshu.io/collections/images/13199/Unnamed_QQ_Screenshot20150315121101.png?imageMogr2/auto-orient/strip|imageView2/2/w/180",
                "http://upload.jianshu.io/collections/images/2150/3.png?imageMogr2/auto-orient/strip|imageView2/2/w/180",
                "http://qzapp.qlogo.cn/qzapp/100410602/240190197CC910A185BA32398359D4C5/100",
                "http://upload.jianshu.io/users/upload_avatars/654319/c54c8e001748.jpeg?imageMogr/thumbnail/90x90/quality/100",
                "http://qzapp.qlogo.cn/qzapp/100410602/D3CDD72BBDF11AE46CB5260830C67958/100",
                "http://wx.qlogo.cn/mmopen/ehNBaYGR6m30BKiayZTsM2Kd1DZf207Dt741Pvh4X3Rpd3zXOddHIiaj6yPuiaKxqdR4hpXk4gF09lwbQfib0fh9uvVHvpwkLcLk/0"};

        String[] urls2 = {"http://ww4.sinaimg.cn/large/7a8aed7bjw1exr0p4r0h3j20oy15445o.jpg"};

        downloaderGallery1.setUrls(urls1);

        downloaderGallery1.setImageItemListener(new PictureDownloaderGallery.ImageItemListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "the " + position + "item is clicked!", Toast.LENGTH_LONG).show();
            }
        });

        downloaderGallery2.setUrls(urls2);

        downloaderGallery2.setImageItemListener(new PictureDownloaderGallery.ImageItemListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"the " + position + "item is clicked!",Toast.LENGTH_LONG).show();
            }
        });




    }

}
