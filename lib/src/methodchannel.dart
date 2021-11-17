import 'dart:async';

import 'package:flutter/services.dart';

class OptimizationBattery {
  static const MethodChannel _channel = MethodChannel('optimization_battery');
  /// check if the app ignoring BatteryOptimizations
  static Future<bool> isIgnoringBatteryOptimizations() async {
    final reading =
        await _channel.invokeMethod('isIgnoringBatteryOptimizations');
    return reading!;
  }
  /// open BatteryOptimizations setting page
  static Future<bool> openBatteryOptimizationSettings() async {
    final reading =
        await _channel.invokeMethod('openBatteryOptimizationSettings');
    return reading!;
  }
}
