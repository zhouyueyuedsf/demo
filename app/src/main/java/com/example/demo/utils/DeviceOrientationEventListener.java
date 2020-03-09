package com.example.demo.utils;

import android.content.Context;
import android.view.OrientationEventListener;

import java.lang.ref.WeakReference;

public class DeviceOrientationEventListener extends OrientationEventListener {


    public static final int DEVICE_UNKNOWN = -1;
    public static final int DEVICE_TOP_TOP_0 = 1;
    public static final int DEVICE_LEFT_TOP_90 = 2;
    public static final int DEVICE_BOTTOM_TOP_180 = 3;
    public static final int DEVICE_RIGHT_TOP_270 = 4;

    private int currOrientation = DEVICE_UNKNOWN;
    private int oldOrientation = DEVICE_UNKNOWN;

    private WeakReference<OnDeviceOrientationChangedListener> listener;

    public DeviceOrientationEventListener(Context context) {
        super(context);
    }

    public DeviceOrientationEventListener(Context context, int rate) {
        super(context, rate);
    }

    public void setOnDeviceOrientationChangedListener(OnDeviceOrientationChangedListener _listener ){
        listener = new WeakReference<OnDeviceOrientationChangedListener>(_listener);
    }

    public int getDeviceOldOrientation(){
        return oldOrientation;
    }
    public int getDeviceCurrOrientation(){
        return currOrientation;
    }
    public void setDeviceCurrOrientation(int _currOrientation){
        oldOrientation = currOrientation;
        currOrientation = _currOrientation;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if((orientation >= 0 && orientation <= 30) || (orientation >= 330 && orientation<= 360)){
            /**正面向上 竖屏正面，保持不变*/
            if(currOrientation != DEVICE_TOP_TOP_0) {

                oldOrientation = currOrientation;
                currOrientation = DEVICE_TOP_TOP_0;
                if(listener != null && listener.get() != null){
                    listener.get().onDeviceOrientationChanged(oldOrientation,currOrientation,false);
                }

            }
        }else if((orientation >= 60 && orientation <= 120)){
            /**左边向上 横屏反面，向右旋转90*/
            if(currOrientation != DEVICE_LEFT_TOP_90 ) {

                oldOrientation = currOrientation;
                currOrientation = DEVICE_LEFT_TOP_90;
                if(listener != null && listener.get() != null){
                    listener.get().onDeviceOrientationChanged(oldOrientation,currOrientation,false);
                }
            }

        }else if((orientation >= 150 && orientation <= 210)){
            /**底边向上 竖屏反面，向右旋转180*/
            if(currOrientation != DEVICE_BOTTOM_TOP_180) {

                oldOrientation = currOrientation;
                currOrientation = DEVICE_BOTTOM_TOP_180;
                if(listener != null && listener.get() != null){
                    listener.get().onDeviceOrientationChanged(oldOrientation,currOrientation,false);
                }
            }

        }else if(orientation >= 240 && orientation <= 300){
            /**右边向上 恒屏正面，向右旋转270 */
            if(currOrientation != DEVICE_RIGHT_TOP_270) {

                oldOrientation = currOrientation;
                currOrientation = DEVICE_RIGHT_TOP_270;
                if(listener != null && listener.get() != null){
                    listener.get().onDeviceOrientationChanged(oldOrientation,currOrientation,false);
                }
            }

        }
    }

    public interface OnDeviceOrientationChangedListener{
        void onDeviceOrientationChanged(int oldOri, int currOri, boolean isForce);
    }


}


































