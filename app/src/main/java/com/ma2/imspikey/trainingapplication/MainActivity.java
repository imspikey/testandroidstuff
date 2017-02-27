package com.ma2.imspikey.trainingapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ma2.imspikey.trainingapplication.adapters.RecyclerAdapter;
import com.ma2.imspikey.trainingapplication.model.Food;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;

    private Camera mCamera;
    private CameraPreview mPreview;
    private TextureView mTextureView;
    private LinearLayout cambg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
////
////        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
////
////        mLinearLayoutManager = new LinearLayoutManager(this);
////
////        mRecyclerView.setLayoutManager(mLinearLayoutManager);
////
////        ArrayList<Food> foods = new ArrayList<>();
////        for (int i = 0;i < 10;i++)
////        {
////            Food food = new Food();
////
////            food.name ="Cheken Kabab With Extra Salad"+ i;
////            food.description="One of the best availabale meals on this days menue it should be not lost"+ i;
////            foods.add(food);
////        }
//
//        RecyclerAdapter adapter = new RecyclerAdapter(foods);
//    //   mRecyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
            else
            {
               //  mCamera =  getCamera();
//                mPreview = new CameraPreview(this, mCamera);
//                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//                preview.addView(mPreview);
                setContentView(R.layout.activity_cam);
                cambg = (LinearLayout)findViewById(R.id.cambg);
                mTextureView = (TextureView) findViewById(R.id.txView);
                mTextureView.setSurfaceTextureListener(this);


                mTextureView.getViewTreeObserver().addOnDrawListener( new ViewTreeObserver.OnDrawListener(){

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onDraw() {

                     //   return false;
                    }
                });
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    Camera c =  getCamera();
                    int a = 3;
                } else {
                  //  AppUtils.showUserMessage("You declined to allow the app to access your camera", this);
                Toast.makeText(this,"Did not authorise",Toast.LENGTH_LONG).show();
                }
        }
    }


    public   Camera getCamera(){
        Camera c = null;
        try {
            int nOfCameras = Camera.getNumberOfCameras();
            c= Camera.open(0);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return  c;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        mCamera = Camera.open();

        try {
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Bitmap bitmap = mTextureView.getBitmap();
        blur(bitmap,cambg);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bitmap, View view) {
        long startMs = System.currentTimeMillis();

        float radius = 20;

        int width =  Math.round(bitmap.getWidth() *.5f);
        int height  = Math.round(bitmap.getHeight() *.5f);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(this);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpin =Allocation.createFromBitmap(rs,inputBitmap);
        Allocation tempout = Allocation.createFromBitmap(rs,outputBitmap);
        intrinsicBlur.setRadius(20);
        intrinsicBlur.setInput(tmpin);
        intrinsicBlur.forEach(tempout);
        tempout.copyTo(outputBitmap);

        BitmapDrawable ob = new BitmapDrawable(getResources(), outputBitmap);

        cambg.setBackground(ob);


//        RenderScript rs = RenderScript.create(this);
//
//        Allocation overlayAlloc = Allocation.createFromBitmap(
//                rs, overlay);
//
//        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
//                rs, overlayAlloc.getElement());
//
//        blur.setInput(overlayAlloc);
//
//        blur.setRadius(radius);
//
//        blur.forEach(overlayAlloc);
//
//        overlayAlloc.copyTo(overlay);
//
//        view.setBackground(new BitmapDrawable(
//                getResources(), overlay));
//        rs.destroy();
    }

//    private void blur(Bitmap bkg, View view) {
//        long startMs = System.currentTimeMillis();
//        float scaleFactor = 1;
//        float radius = 20;
//
//            scaleFactor = 8;
//            radius = 2;
//
//
//        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()/scaleFactor),
//                (int) (view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(overlay);
//        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);
//        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
//        Paint paint = new Paint();
//        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
//        canvas.drawBitmap(bkg, 0, 0, paint);
//
//        overlay = FastBlur.doBlur(overlay, (int)radius, true);
//        view.setBackground(new BitmapDrawable(getResources(), overlay));
//        statusText.setText(System.currentTimeMillis() - startMs + "ms");
//    }

}
