package ru.xpcom.glidetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.icu.number.Scale;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MAIN";
    private static final String URL_IMAGES = "https://images11.domashnyochag.ru/upload/img_cache/14d/14dcbc2af577a140a18cf2923e07f881_ce_740x947x0x0_cropped_666x852.jpg";
    ImageView mImageView;
    Button btnDrawable, btnPlaceholder, btnUrl, btnError, btnResize, btnRotate, btnScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiComponent();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDrawable)
            Glide.with(this).load(R.drawable.imagedefault).into(mImageView);
        else if (v.getId() == R.id.btnPlaceholder)
            Glide.with(this).load("www.xpcom.ru").placeholder(R.drawable.ic_baseline_image_24).into(mImageView);
        else if (v.getId() == R.id.btnUrl)
            Glide.with(this).load(URL_IMAGES).placeholder(R.drawable.ic_baseline_image_24).into(mImageView);
        else if (v.getId() == R.id.btnResize)
            Glide.with(this).load(URL_IMAGES).placeholder(R.drawable.ic_baseline_image_24).apply(new RequestOptions().override(20, 20)).into(mImageView);
        else if (v.getId() == R.id.btnRotate)
            Glide.with(this).load(URL_IMAGES).transform(new RotateTransformation(90f)).placeholder(R.drawable.ic_baseline_image_24).into(mImageView);
        else if (v.getId() == R.id.btnScale)
            Glide.with(this).load(URL_IMAGES).centerCrop().placeholder(R.drawable.ic_baseline_image_24).into(mImageView);
        else if (v.getId() == R.id.btnError)
            Glide.with(this).load("www.xpcom.ru").error(R.drawable.ic_baseline_error_24).into(mImageView);
        else Log.d(TAG, "No Button");
    }

    private void uiComponent() {
        mImageView = findViewById(R.id.image_view);

        btnDrawable = findViewById(R.id.btnDrawable);
        btnPlaceholder = findViewById(R.id.btnPlaceholder);
        btnUrl = findViewById(R.id.btnUrl);
        btnError = findViewById(R.id.btnError);
        btnResize = findViewById(R.id.btnResize);
        btnRotate = findViewById(R.id.btnRotate);
        btnScale = findViewById(R.id.btnScale);

        btnDrawable.setOnClickListener(this);
        btnPlaceholder.setOnClickListener(this);
        btnUrl.setOnClickListener(this);
        btnError.setOnClickListener(this);
        btnResize.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnScale.setOnClickListener(this);

    }

    public static class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;

        public RotateTransformation(float rotateRotationAngle) {
            super();

            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotateRotationAngle);

            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(("rotate" + rotateRotationAngle).getBytes());
        }

    }
}