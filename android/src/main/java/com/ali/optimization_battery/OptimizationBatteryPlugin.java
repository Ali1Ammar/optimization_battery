package com.ali.optimization_battery;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.content.ActivityNotFoundException;
import android.app.Activity;
import android.os.PowerManager;
import android.content.Intent;
import android.content.Context;
import static android.content.Context.POWER_SERVICE;
import android.provider.Settings;

/** OptimizationBatteryPlugin */
public class OptimizationBatteryPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware  {
  private MethodChannel channel;
  private Context mContext;
  private Activity activity;
  
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
      LaunchStatus launchStatus = openBatteryOptimizationSettings();
      if (launchStatus == LaunchStatus.NO_ACTIVITY) {
        result.error("NO_ACTIVITY", "Launching a setting requires a foreground activity.", null);
      } else if (launchStatus == LaunchStatus.ACTIVITY_NOT_FOUND) {
        result.error(
            "ACTIVITY_NOT_FOUND",
            "No Activity found to handle intent",
            null);
      } else {
        result.success(true);
      }
    }
    else {
      result.notImplemented();
    }
  }
  
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }


  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    activity=  activityPluginBinding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    activity=null;
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

  LaunchStatus openBatteryOptimizationSettings() {
    // return Success if sdk version is below 23
    if(android.os.Build.VERSION.SDK_INT<23)
      return LaunchStatus.OK;

    if (activity == null) {
        return LaunchStatus.NO_ACTIVITY;
    }

    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    try {
      activity.startActivity(intent);
    } catch (ActivityNotFoundException e) {
      return LaunchStatus.ACTIVITY_NOT_FOUND;
    }

    return LaunchStatus.OK;
  }



  @Override
  public void onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }


}

  enum LaunchStatus {
    /** The intent was well formed. */
    OK,
    /** No activity was found . */
    NO_ACTIVITY,
    /** No Activity found that can handle given intent. */
    ACTIVITY_NOT_FOUND,
  }