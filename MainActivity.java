package com.nishiguchimaika.fingerpaint;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener, TestDialogFragment.DialogEventListener{

    Canvas mCanvas;
    Paint mPaint;
    Path mPath;
    Bitmap mBitmap;
    float x1, y1;
    int width, height;
    AlertDialog.Builder mAlertBuilder;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    int st;
    int i = 0;
    String StampName;
    private ImageView stamp[];
    float x;
    float y;
    boolean frag = false;
    @InjectView(R.id.imageView)
    ImageView mImageView;

    int pic;
    int opa;
    int sat;
    float val;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mSharedPreferences = getSharedPreferences("fingerprint", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        st = 0;
        mImageView.setOnTouchListener(this);

        pic = Color.BLACK;
        opa = 0;
        sat = 0;
        num = 25;

        mPaint = new Paint();
        mPaint.setStrokeWidth(10.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();

        stamp = new ImageView[5];
        for(i = 0; i < 5; i++){
            stamp[i] = (ImageView) findViewById(getResources().getIdentifier("imageView" + i,"id",getPackageName()));
        }

        stamp[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                StampName = "Star";
                ClipData data = ClipData.newPlainText("Stamp0","Drag");
                view.startDrag(data, new View.DragShadowBuilder(view),(Object) view, 0);
                return false;
            }
        });
        stamp[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StampName = "Heart";
                ClipData Data = ClipData.newPlainText("Stamp1", "Drag");
                v.startDrag(Data, new View.DragShadowBuilder(v), (Object) v, 0);
                return false;
            }
        });
        stamp[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StampName = "Ribbon";
                ClipData Data = ClipData.newPlainText("Stamp2", "Drag");
                v.startDrag(Data, new View.DragShadowBuilder(v), (Object) v, 0);
                return false;
            }
        });
        stamp[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StampName = "Note";
                ClipData Data = ClipData.newPlainText("Stamp3", "Drag");
                v.startDrag(Data, new View.DragShadowBuilder(v), (Object) v, 0);
                return false;
            }
        });
        stamp[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                StampName = "Bear";
                ClipData Data = ClipData.newPlainText("Stamp4", "Drag");
                v.startDrag(Data, new View.DragShadowBuilder(v), (Object) v, 0);
                return false;
            }
        });

        mImageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_EXITED:
                        frag = false;
                        break;
                    case DragEvent.ACTION_DROP:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        frag = true;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        for(i = 0; i< 5; i ++){
            stamp[i].setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()){
                        case DragEvent.ACTION_DRAG_ENDED:
                            ClipData result = event.getClipData();
                            if(frag == true){
                                switch (StampName){
                                    case "Star":
                                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.stamp0);
                                        bmp = Bitmap.createScaledBitmap(bmp,100,100,false);
                                        mCanvas.drawBitmap(bmp, x-(stamp[0].getWidth())/2, y-(stamp[0].getHeight())/2, mPaint);
                                        mImageView.setImageBitmap(mBitmap);
                                        break;
                                    case "Heart":
                                        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.stamp1);
                                        bmp2 = Bitmap.createScaledBitmap(bmp2,100,100,false);
                                        mCanvas.drawBitmap(bmp2, x-(stamp[1].getWidth())/2, y-(stamp[1].getHeight())/2, mPaint);
                                        mImageView.setImageBitmap(mBitmap);
                                        break;
                                    case "Ribbon":
                                        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.stamp2);
                                        bmp3 = Bitmap.createScaledBitmap(bmp3,100,100,false);
                                        mCanvas.drawBitmap(bmp3, x-(stamp[2].getWidth())/2, y-(stamp[2].getHeight())/2, mPaint);
                                        mImageView.setImageBitmap(mBitmap);
                                        break;
                                    case "Note":
                                        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.stamp3);
                                        bmp4 = Bitmap.createScaledBitmap(bmp4,100,100,false);
                                        mCanvas.drawBitmap(bmp4, x-(stamp[3].getWidth())/2, y-(stamp[3].getHeight())/2, mPaint);
                                        mImageView.setImageBitmap(mBitmap);
                                        break;
                                    case "Bear":
                                        Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.stamp4);
                                        bmp5 = Bitmap.createScaledBitmap(bmp5,100,100,false);
                                        mCanvas.drawBitmap(bmp5, x-(stamp[4].getWidth())/2, y-(stamp[4].getHeight())/2, mPaint);
                                        mImageView.setImageBitmap(mBitmap);
                                        break;
                                    default:
                                        break;
                                }

                            }
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
        /*mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(onClick());*/
        /*TextView textView = new TextView(this);
        textView.setText("New text");
*/
    }

    /*private View.OnClickListener onClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayout.addView(createNewTextView(mEditText.getText().toString()));
            }
        };
    }*/

    /*private TextView createNewTextView(String text){
        final ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText("New text:" + text);
        return textView;
    }
*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        if(mBitmap == null){
            width = mImageView.getWidth();
            height = mImageView.getHeight();

            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.WHITE);

            mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                mPath.quadTo(x1, y1, x, y);
                mCanvas.drawPath(mPath, mPaint);
                break;
        }

        x1 = x;
        y1 = y;
        mImageView.setImageBitmap(mBitmap);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        switch (item.getItemId()){
           /* case R.id.menu_text:
                mAlertBuilder = new AlertDialog.Builder(this);
                mAlertBuilder.setTitle(R.string.menu_text);
                mAlertBuilder.setPositiveButton("drawing",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mEditText != null) {
                            mEditText.setVisibility(View.INVISIBLE);
                            *//*mEditText.getTextColors();
                            mEditText.getTextSize();*//*
                            mTextView.setText(TEXT + mEditText.getText().toString());
                            TEXT = mEditText.getText().toString();
                        }
                        *//*mEditText.setVisibility(View.INVISIBLE);*//*
                        mEditor.putInt("text",0);
                        text = 0;
                        mEditor.commit();
                    }
                });
                mAlertBuilder.setNegativeButton("writing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEditText.setVisibility(View.VISIBLE);
                        mEditText.setText("");
                        *//*mEditText.getText();*//*
                        mEditor.putInt("text", 1);
                        text = 1;
                        mEditor.commit();
                    }
                });
                mAlertBuilder.show();
                break;*/
            /*case R.id.menu_stamp:
                mAlertBuilder = new AlertDialog.Builder(this);
                mAlertBuilder.setTitle(R.string.menu_new);
                if(st == 0){
                    mAlertBuilder.setMessage("今作っているプロジェクトを保存しますか？");
                    st = mSharedPreferences.getInt("stamp",1);
                }else if(st == 1){
                    mAlertBuilder.setMessage("今作っているスタンプを保存しますか？");
                    st = mSharedPreferences.getInt("stamp",0);
                }
                mAlertBuilder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(st == 1){
                            Log.d("st","0");
                            save();
                        }else if(st == 0){
                            Log.d("st","ok");
                            stampSave();
                        }
                    }
                });
                mAlertBuilder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCanvas.drawColor(Color.WHITE);
                        Log.d("st","no");
                    }
                });
                mAlertBuilder.show();
                break;*/
            case R.id.menu_change:
                TestDialogFragment newFragment = new TestDialogFragment();
                Bundle b = new Bundle();
                b.putInt("pic",pic);
                b.putInt("opa",opa);
                b.putInt("sat",sat);
                b.putFloat("val",val);
                b.putInt("num",num);
                newFragment.setArguments(b);
                newFragment.setOnDialogEventListener(this);
                newFragment.show(getFragmentManager(),"test");
            case R.id.menu_selectStamp:
                break;
            case R.id.menu_open:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
                break;
            case R.id.menu_save:
                save();
                break;
            case R.id.menu_new:
                mAlertBuilder = new AlertDialog.Builder(this);
                mAlertBuilder.setTitle(R.string.menu_new);
                mAlertBuilder.setMessage("Drawing would not be saved. Do you want to continue??");
                mAlertBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCanvas.drawColor(Color.WHITE);
                        mImageView.setImageBitmap(mBitmap);
                    }
                });
                mAlertBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mAlertBuilder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Uri uri = data.getData();
            try{
                mBitmap = loadImage(uri);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        mCanvas = new Canvas(mBitmap);
        mImageView.setImageBitmap(mBitmap);
    }

    Bitmap loadImage(Uri uri) throws IOException{
        boolean landscape = false;
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(is, null, options);
        is.close();
        int oh = options.outHeight;
        int ow = options.outWidth;
        if(ow>oh){
            landscape = true;
            oh = options.outWidth;
            ow = options.outHeight;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize=Math.max(ow/width, oh/height);
        InputStream is2 = getContentResolver().openInputStream(uri);
        bm = BitmapFactory.decodeStream(is2,null,options);
        is2.close();
        if(landscape){
            Matrix matrix = new Matrix();
            matrix.setRotate(90.0f);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
        }
        bm = Bitmap.createScaledBitmap(bm, width, (int)(width*((double)oh/(double)ow)),false);
        Bitmap offBitMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas offCanvas = new Canvas(offBitMap);
        offCanvas.drawBitmap(bm, 0, (height - bm.getHeight())/2, null);
        bm = offBitMap;
        return bm;

    }

    void save(){
        SharedPreferences prefs = getSharedPreferences("FingerPaintPreferences",MODE_PRIVATE);
        int imageNumber = prefs.getInt("imageNumber",1);
        File file = null;
        DecimalFormat form = new DecimalFormat("0000");
        String dirPath = Environment.getExternalStorageDirectory() + "/FingerPaint/";
        File outDir = new File(dirPath);

        if(!outDir.exists())
            outDir.mkdir(); // make directory
            do {
                file = new File(dirPath + "img" + form.format(imageNumber) + ".png");
                imageNumber++;
            } while (file.exists());

        if(writeImage(file)) {
            scanMedia(file.getPath());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("imageNumber", imageNumber+1);
            editor.commit();

        }
    }

    /*void stampSave(){
        SharedPreferences prefs = getSharedPreferences("FingerPaintPreferences",MODE_PRIVATE);
        int imageNumber = prefs.getInt("imageNumber2",1);
        File file = null;
        DecimalFormat form = new DecimalFormat("0000");
        String dirPath = Environment.getExternalStorageDirectory() + "/StampFolder/";
        File outDir = new File(dirPath);

        if(!outDir.exists())
            outDir.mkdir(); // make directory
        do {
            file = new File(dirPath + "img" + form.format(imageNumber) + ".png");
            imageNumber++;
        } while (file.exists());

        if(writeImage(file)) {
            scanMedia(file.getPath());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("imageNumber2", imageNumber+1);
            editor.commit();

        }
    }*/

    boolean writeImage(File file){
        try{
            FileOutputStream fo=new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
            fo.flush();
            fo.close();
        }catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    MediaScannerConnection mc;
    void scanMedia(final String fp){
        mc = new MediaScannerConnection(this,
                new MediaScannerConnection.MediaScannerConnectionClient(){
                    public void onScanCompleted(String path, Uri uri){
                        mc.disconnect();
                    }

                    public void onMediaScannerConnected(){
                        mc.scanFile(fp,"image/*");
                    }

                }
        );
        mc.connect();
    }

    @Override
    public void setSize(int size) {
        Log.d("setSize",size+"");
        num = size;
        mPaint.setStrokeWidth(size);
    }

    @Override
    public void setColor(int color) {
        Log.d("color", color+"");
        pic = color;
        mPaint.setColor(color);
    }
    @Override
    public void setOpa(int opa1) {
        opa = opa1;
        Log.d("opa", opa1+"");
    }
    @Override
    public void setSat(int sat1) {
        sat = sat1;
        Log.d("sat", sat1+"");
    }
    @Override
    public void setVal(float val1) {
        val = val1;
        Log.d("val", val1+"");
    }
}

