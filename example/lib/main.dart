import 'package:flutter/material.dart';
import 'package:optimization_battery/optimization_battery.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: BatteryOptimizationsObserver(builder: (context, isIgonre) {
          return Column(
            children: [
              Text('isIgonred : $isIgonre'),
              if (isIgonre != null && !isIgonre)
                ElevatedButton(
                    onPressed: () {
                      OptimizationBattery.openBatteryOptimizationSettings();
                    },
                    child: const Text("open igonre battery setting"))
            ],
          );
        }),
      ),
    );
  }
}
