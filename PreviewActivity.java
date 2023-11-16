package org.tensorflow.person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviewActivity extends Activity {

    TextView tv_count,tv_score;
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        initialize();
    }

    public void initialize(){
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_image = (ImageView) findViewById(R.id.iv_image);
    }

    public void onTakePhoto(View view){
        Intent intent = new Intent(this, DetectorActivity.class);
        intent.putExtra("backCamera", false);
        startActivityForResult(intent, 1);
    }

    public void onTakePhotoBack(View view){
        Intent intent = new Intent(this, DetectorActivity.class);
        intent.putExtra("backCamera", true);
        startActivityForResult(intent, 1);
    }

    public Bitmap drawText(Activity activity, Bitmap mBitmap, String displaytext1) {
        Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        // create a canvas on which to draw
        Canvas canvas = new Canvas(bmOverlay);

        Paint paint = new Paint();
        paint.setColor(activity.getResources().getColor(android.R.color.holo_purple));
        paint.setTextSize(14);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setFakeBoldText(false);
        //paint.setShadowLayer(1, 0, 0, Color.BLACK);

        // if the background image is defined in main.xml, omit this line
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        canvas.drawText("Person: "+displaytext1, 10, mBitmap.getHeight() - 15, paint);
        // set the bitmap into the ImageView

        return bmOverlay;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(resultCode == 1){
                String person = data.getStringExtra("person");
                String score = data.getStringExtra("score");
                byte[] byteArray = data.getByteArrayExtra("img");
                Bitmap bmp = drawText(this, BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length), person);
                //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                iv_image.setImageBitmap(bmp);
                tv_count.setText("Person: "+person);
                tv_score.setText("Score: "+score);
            }
        }
    }
}
