package com.example.zane.downloaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private Button start;
    private Button pause;
    private Button delete;
    private Button reset;
    private TextView total;
    private ProgressBar mProgressBar2;
    private Button start2;
    private Button pause2;
    private Button delete2;
    private Button reset2;
    private TextView total2;

    private ImageView image;
    private ImageView image2;

    private int max;
    private int max2;

    private DownloadUtil mDownloadUtil;
    private DownloadUtil mDownloadUtil2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        start = (Button) findViewById(R.id.button_start);
        pause = (Button) findViewById(R.id.button_pause);
        delete = (Button) findViewById(R.id.button_delete);
        reset = (Button) findViewById(R.id.button_reset);
        total = (TextView) findViewById(R.id.textView_total);
        image = (ImageView) findViewById(R.id.image);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        start2 = (Button) findViewById(R.id.button_start_2);
        pause2 = (Button) findViewById(R.id.button_pause_2);
        delete2 = (Button) findViewById(R.id.button_delete_2);
        reset2 = (Button) findViewById(R.id.button_reset_2);
        total2 = (TextView) findViewById(R.id.textView_total_2);
        image2 = (ImageView) findViewById(R.id.image_2);

        String urlString = "http://bbra.cn/Uploadfiles/imgs/20110303/fengjin/013.jpg";
        final String localPath = Environment.getExternalStorageDirectory()
                                         .getAbsolutePath() + "/ADownLoadTest";
        mDownloadUtil = new DownloadUtil(2, localPath, "abc.jpg", urlString,
                                                this);
        mDownloadUtil2 = new DownloadUtil(2, localPath, "abc.jpg", urlString,
                                                this);

        mDownloadUtil2.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {

            @Override
            public void downloadStart(int fileSize) {
                max2 = fileSize;
                mProgressBar2.setMax(fileSize);
            }

            @Override
            public void downloadProgress(int downloadedSize) {
                mProgressBar2.setProgress(downloadedSize);
                total2.setText((int) downloadedSize * 100 / max + "%");
            }

            @Override
            public void downloadEnd() {
                Bitmap bitmap = decodeSampledBitmapFromResource(localPath
                                                                        + File.separator + "abc.jpg", 200, 200);
                image2.setImageBitmap(bitmap);
            }
        });

        mDownloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {

            @Override
            public void downloadStart(int fileSize) {
                max = fileSize;
                mProgressBar.setMax(fileSize);
            }

            @Override
            public void downloadProgress(int downloadedSize) {
                mProgressBar.setProgress(downloadedSize);
                total.setText((int) downloadedSize * 100 / max + "%");
            }

            @Override
            public void downloadEnd() {
                Bitmap bitmap = decodeSampledBitmapFromResource(localPath
                                                                        + File.separator + "abc.jpg", 200, 200);
                image.setImageBitmap(bitmap);
            }
        });
        start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil.start();
            }
        });
        pause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil.pause();
            }
        });
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil.delete();
                mProgressBar.setProgress(0);
                total.setText("0%");
                image.setImageBitmap(null);
            }
        });
        reset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil.reset();
                mProgressBar.setProgress(0);
                total.setText("0%");
                image.setImageBitmap(null);
            }
        });

        start2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil2.start();
            }
        });
        pause2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil2.pause();
            }
        });
        delete2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil2.delete();
                mProgressBar2.setProgress(0);
                total2.setText("0%");
                image2.setImageBitmap(null);
            }
        });
        reset2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDownloadUtil2.reset();
                mProgressBar2.setProgress(0);
                total2.setText("0%");
                image2.setImageBitmap(null);
            }
        });

    }

    public static Bitmap decodeSampledBitmapFromResource(String fileName,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fileName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                           && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
