package xhome.uestcfei.com.picturedownloadergallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Email : luckyliangfei@gmail.com
 * Created by fei on 15/11/6.
 */
public class PictureDownloaderGallery extends ViewGroup {

    private static final String TAG = "QQPhotonView";
    private String [] urls ;
    private List<Bitmap> bitmapList ;

    private int childWidth;
    private int childHeight;
    private int hSpace;
    private int vSpace;

    private int size;

    private Context context;

    private int errorColor = Color.RED;
    private int waittingColor = Color.LTGRAY;

    private interface RequestLayout{
        void request(int item);
    }

    public interface ImageItemListener{
        void onItemClick(int position);
    }

    private ImageItemListener imageItemListener;

    private RequestLayout requestLayout;

    public PictureDownloaderGallery(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PictureDownloaderGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PictureDownloaderGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        hSpace = 2;
        vSpace = 2;
        bitmapList = new ArrayList<>();
        requestLayout = new RequestLayout() {
            @Override
            public void request(int item) {
                ImageView imageView = (ImageView) getChildAt(item);
                Bitmap src = bitmapList.get(item);
                if (src == null) {
                    imageView.setImageResource(R.drawable.smail_error);
                }else {
                    int width = src.getWidth();
                    int height = src.getHeight();

                    //use temp bitmap
                    Bitmap temp = Bitmap.createScaledBitmap(src, Math.max(width, childWidth), Math.max(height, childHeight), true);
                    Bitmap bitmap ;
                    if (temp.getWidth() > temp.getHeight()) {
                        bitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(temp, temp.getWidth() / 2 - temp.getHeight() / 2, 0, temp.getHeight(), temp.getHeight()), childWidth, childHeight, true);
                    }else {
                        bitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(temp, 0, temp.getHeight() / 2 - temp.getWidth() / 2, temp.getWidth(), temp.getWidth()),childWidth,childHeight,true);
                    }
                    imageView.setImageBitmap(bitmap);
                }

            }
        };
    }

    public void setErrorBackgroundColor(int errorColor) {
        this.errorColor = errorColor;
    }

    public void setWaittingColor(int waittingColor) {
        this.waittingColor = waittingColor;
    }

    public void setImageItemListener(ImageItemListener imageItemListener) {
        this.imageItemListener = imageItemListener;
    }

    public void setUrls(final String [] urls) {
        this.urls = urls;
        resetSize(urls.length);
        for (int i = 0; i < urls.length; i++) {
            new MyTask().execute(i);
        }
    }

    /**
     * task to download
     */
    class MyTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            URL url = null;
            try {
                url = new URL(urls[params[0]]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapList.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return params[0];
        }


        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);
            requestLayout.request(aVoid);
        }
    }

    /**
     * init the number of views
     * @param size
     */
    public void resetSize(int size) {
        this.size = size;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //to compute the width and height of the sub view
        childWidth = (rw - 2 * hSpace) / 3;
        childHeight = childWidth;
        if (size == 1) {
            childWidth = childWidth + childWidth / 2;
            childHeight = childWidth;
        }

        //to add subview to the layout
        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(context);
            addView(view);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.left = (i % 3) * (childWidth + hSpace);
            layoutParams.top = (i / 3) * (childWidth + vSpace);
        }

        Log.d(TAG, "rh" + rh + "final" + ((size - 1) / 3 + 1) * childHeight);
        if (widthMode == MeasureSpec.EXACTLY) {
            rh = ((size - 1) / 3 + 1) * childHeight;
        }

        setMeasuredDimension(rw, rh);
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < size; i++) {
            ImageView child = (ImageView) this.getChildAt(i);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.setBackgroundColor(waittingColor);
            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
                    lParams.top + childHeight);
            final int finalI = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageItemListener.onItemClick(finalI);
                }
            });
        }
    }

    /**
     * inner class to store the info of subview , just like bundle
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }

    /**
     * use this method to generate the layoutparams
     * @param attrs
     * @return
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new PictureDownloaderGallery.LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof PictureDownloaderGallery.LayoutParams;
    }

}
