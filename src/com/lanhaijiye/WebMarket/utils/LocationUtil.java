package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by Administrator on 2015/4/29.
 */
public class LocationUtil {
    public static void getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(context, context.getResources().getString(R.string.can_not_locate), Toast.LENGTH_SHORT).show();
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//GPS可以定位
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    Log.e("GPS定位", location.getLongitude() + "；" + location.getLatitude());
                    //todo 坐标交互
                }
            } else {//GPS定位不行，用网络定位
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
                    // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    // Provider被enable时触发此函数，比如GPS被打开
                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    // Provider被disable时触发此函数，比如GPS被关闭
                    @Override
                    public void onProviderDisabled(String provider) {

                    }

                    //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            Log.e("Map", "Location changed : Lat: "
                                    + location.getLatitude() + " Lng: "
                                    + location.getLongitude());
                        }
                    }
                });
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Log.e("网络定位", location.getLongitude() + "；" + location.getLatitude());
                    //todo 坐标交互
                }
            }
        }
    }
}
