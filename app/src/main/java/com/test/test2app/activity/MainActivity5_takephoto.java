package com.test.test2app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.test.test2app.DefaultFaceView;
import com.test.test2app.R;
import com.test.test2app.bitmap.BitmapMemoryCache;
import com.test.test2app.utils.TakePictureUtils;
import com.zhaoyuntao.androidutils.component.ZButton;
import com.zhaoyuntao.androidutils.permission.PermissionSettings;
import com.zhaoyuntao.androidutils.tools.S;

import java.util.List;

public class MainActivity5_takephoto extends AppCompatActivity {

    private ZButton zButton_click;
    private ZButton zButton_click2;
    private ZButton zButton_photo;
    private BitmapMemoryCache bitmapMemoryCache;
    private DefaultFaceView faceImageView1;
    private DefaultFaceView faceImageView2;
    private DefaultFaceView faceImageView3;
    private DefaultFaceView faceImageView4;
    private DefaultFaceView faceImageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity5_takephoto);
        bitmapMemoryCache = BitmapMemoryCache.createCache(this);

        zButton_click = findViewById(R.id.click);
        zButton_click2 = findViewById(R.id.click2);
        zButton_photo = findViewById(R.id.photo);
        faceImageView1=findViewById(R.id.face1);
        faceImageView2=findViewById(R.id.face2);
        faceImageView3=findViewById(R.id.face3);
        faceImageView4=findViewById(R.id.face4);
        faceImageView5=findViewById(R.id.face5);

        faceImageView1.setName("Bsadsad");
        faceImageView2.setName("Zfgafdggdfdsf");
        faceImageView3.setName("غسطس");
        faceImageView4.setName("1fdgdfdgfdgdgfd");
        faceImageView5.setName("nsdfdsfds");
//        faceImageView.setName("篥");

        zButton_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.s("111");
                TakePictureUtils.takePhoto(MainActivity5_takephoto.this, new TakePictureUtils.PhotoGetter() {
                    @Override
                    public void whenGetPhoto(String absolutePath, Uri uri, Bitmap bitmap) {
                        Bitmap compressBitmap = TakePictureUtils.getCompressBitmap(MainActivity5_takephoto.this, uri, (long) (2 * 1024 * 1024));
                        zButton_photo.setDrawable_back(compressBitmap);
                        S.s("comp:" + compressBitmap.getAllocationByteCount());
                        bitmapMemoryCache.put(absolutePath, compressBitmap);
                        S.s("size:" + bitmapMemoryCache.size());
                    }

                    @Override
                    public void whenPictureNotFound() {
                        S.s("2");
                    }

                    @Override
                    public void whenNoPermission(List<String> permissions) {
                        S.s("3");
                    }

                    @Override
                    public void whenForbidPermission(PermissionSettings permissionSettings) {
                        S.s("4");
                    }
                });
            }
        });
        zButton_click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public static void runThread(Runnable runnable) {

        int stackTraceCount = 3;
        StackTraceElement[] elements = new Throwable().getStackTrace();
        String callerClassName = elements.length > stackTraceCount ? elements[stackTraceCount].getClassName() : "N/A";
        String callerMethodName = elements.length > stackTraceCount ? elements[stackTraceCount].getMethodName() : "N/A";
        String callerLineNumber = elements.length > stackTraceCount ? String.valueOf(elements[stackTraceCount].getLineNumber()) : "N/A";

        int pos = callerClassName.lastIndexOf('.');
        if (pos >= 0) {
            callerClassName = callerClassName.substring(pos + 1);
        }

        String threadName;
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        StringBuilder taskName = new StringBuilder();
        if (traceElements.length > stackTraceCount) {
            StackTraceElement traceElement = traceElements[stackTraceCount];
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append("):");
            taskName.append(traceElement.getMethodName());
            taskName.append("Time:").append(S.now());
            threadName = taskName.toString();
        } else {
            threadName = "<" + callerClassName + "." + callerMethodName + ":" + callerLineNumber + " Time:" + S.now() + "> ";
        }

        S.s("name:" + threadName);
        new Thread(runnable, threadName).start();
    }
}
