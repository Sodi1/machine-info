/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.barcodereader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.samples.vision.barcodereader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Graphic instance for rendering barcode position, size, and ID within an associated graphic
 * overlay view.
 */
public class BarcodeGraphic extends GraphicOverlay.Graphic {

    private int mId;
    public JSONObject info;
    private static final int COLOR_CHOICES[] = {
            Color.BLUE
    };

    private static int mCurrentColorIndex = 0;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile Barcode mBarcode;

    BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mRectPaint = new Paint();
        mRectPaint.setColor(selectedColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);

        mTextPaint = new Paint();
        mTextPaint.setColor(selectedColor);
        mTextPaint.setTextSize(36.0f);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Barcode getBarcode() {
        return mBarcode;
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateItem(Barcode barcode,JSONObject info) {
        mBarcode = barcode;
        this.info = info;
        postInvalidate();
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) throws JSONException {
        Barcode barcode = mBarcode;
        if (barcode == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        // Draws the bounding box around the barcode.
        RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);

        float[] line1 = {rect.left,rect.bottom,rect.left - 25,rect.bottom + 50,-150,rect.left - 25 -150 };
        float[] line2 = {rect.left,rect.top,rect.left - 25,rect.top - 25,-150,rect.left - 25 -150};
        float[] line3 = {rect.right,rect.top,rect.right + 25,rect.top -25,+150,rect.right + 25};
        float[] line4 = {rect.right,rect.bottom,rect.right + 25,rect.bottom +25,+150,rect.right + 25};
        float[] line5 = {rect.left,rect.top - ((rect.top - rect.bottom)/2) ,rect.left - 25,rect.top - ((rect.top - rect.bottom)/2) - 25,-150,rect.left - 25 -150};
        float[] line6 = {rect.right,rect.top - ((rect.top - rect.bottom)/2) ,rect.right - 25,rect.top - ((rect.top - rect.bottom)/2),+150,rect.right + 25 };
        float[][] lines = {line1,line2,line3,line4,line5,line6};

        String key;
        String value;
        Iterator<String> itr;
        itr = info.keys();
        int i =0;
        while(itr.hasNext()){
            key = itr.next().toString();
            value = info.getString(key);
            canvas.drawLine(lines[i][0],lines[i][1],lines[i][2],lines[i][3],paint);
            canvas.drawLine(lines[i][2],lines[i][3],lines[i][2] + lines[i][4] ,lines[i][3],paint);
            canvas.drawText(key + ": " + value,lines[i][5], lines[i][3] - 10, mTextPaint);
            i++;
        }
        // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
//        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);
    }

}
