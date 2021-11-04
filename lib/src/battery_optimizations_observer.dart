import 'dart:io';

import 'package:flutter/widgets.dart';
import 'package:optimization_battery/src/methodchannel.dart';

class BatteryOptimizationsObserver extends StatelessWidget {
  final Widget Function(BuildContext, bool? isIgnored) builder;
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
  const BatteryOptimizationsObserverAndroid({Key? key, required this.builder})
      : super(key: key);

  @override
  _BatteryOptimizationsObserverAndroidState createState() =>
      _BatteryOptimizationsObserverAndroidState();
}

class _BatteryOptimizationsObserverAndroidState
    extends State<BatteryOptimizationsObserverAndroid>
    with WidgetsBindingObserver {
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
