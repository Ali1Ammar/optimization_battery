import 'dart:io';

import 'package:flutter/widgets.dart';
import 'package:optimization_battery/src/methodchannel.dart';

class BatteryOptimizationsObserver extends StatelessWidget {
  final Widget Function(BuildContext, bool? isIgnored) builder;

  /// widget help to listin to any change to BatteryOptimizations state
  /// only for android , on any other platofrm 'isIgnored' will be null
  /// isIgnored will be null at start before complete load the state for the first time
  /// then it will be re loaded every time user leave the app and return to it
  const BatteryOptimizationsObserver({Key? key, required this.builder})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Platform.isAndroid
        ? BatteryOptimizationsObserverAndroid(
            builder: builder,
          )
        : builder(context, null);
  }
}

class BatteryOptimizationsObserverAndroid extends StatefulWidget {
  final Widget Function(BuildContext, bool? isIgnored) builder;

  /// widget help to listin to any change to BatteryOptimizations state ( only for android)
  const BatteryOptimizationsObserverAndroid({Key? key, required this.builder})
      : super(key: key);

  @override
  _BatteryOptimizationsObserverAndroidState createState() =>
      _BatteryOptimizationsObserverAndroidState();
}

class _BatteryOptimizationsObserverAndroidState
    extends State<BatteryOptimizationsObserverAndroid>
    with WidgetsBindingObserver {
  /// isIgnoringBatteryOptimizations
  bool? isIgnoringBatteryOptimizations;

  @override
  void initState() {
    if (Platform.isAndroid) {
      reSetBatteryOptimizations();
      WidgetsBinding.instance!.addObserver(this);
    }

    super.initState();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      reSetBatteryOptimizations();
    }
  }

  /// re load the isIgnoringBatteryOptimizations value and update the widget
  void reSetBatteryOptimizations() {
    OptimizationBattery.isIgnoringBatteryOptimizations().then((value) {
      if (isIgnoringBatteryOptimizations != value) {
        setState(() {
          isIgnoringBatteryOptimizations = value;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return widget.builder(context, isIgnoringBatteryOptimizations);
  }
}
