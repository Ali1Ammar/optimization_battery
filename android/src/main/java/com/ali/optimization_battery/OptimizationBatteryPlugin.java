package com.ali.optimization_battery;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.os.PowerManager;
import android.content.Intent;
import android.content.Context;
import static android.content.Context.POWER_SERVICE;
import android.provider.Settings;

/** OptimizationBatteryPlugin */
public class OptimizationBatteryPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private Context mContext;
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "optimization_battery");
    channel.setMethodCallHandler(this);
    mContext = flutterPluginBinding.getApplicationContext();
  }



  @Override
  public void onMethodCall(MethodCall call, Result result) {

    if (call.method.equals("isIgnoringBatteryOptimizations")) {
      boolean reading = isIgnoringBatteryOptimizations();
      result.success(reading);
      return;
    }
    else if (call.method.equals("openBatteryOptimizationSettings")) {
      boolean reading = openBatteryOptimizationSettings();
      result.success(reading);
      return;
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  boolean isIgnoringBatteryOptimizations() {
    // return true if sdk version is below 23
    if(android.os.Build.VERSION.SDK_INT<23)
      return true;
    
    Intent intent = new Intent();
    String packageName = mContext.getPackageName();
    PowerManager mPowerManager = (PowerManager) (mContext.getSystemService(POWER_SERVICE));

    // ---- If ignore go to settings, else request ----

    if(mPowerManager.isIgnoringBatteryOptimizations(packageName)) {
      return true;
    } else {
      return false;
    }
  }

  boolean openBatteryOptimizationSettings() {
    // return Success if sdk version is below 23
    if(android.os.Build.VERSION.SDK_INT<23)
      return true;
    
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    mContext.startActivity(intent);
    return true;
  }
}
