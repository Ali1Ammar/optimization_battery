# optimization_battery

check if the app is ignoring battery optimizations , open IGNORE BATTERY OPTIMIZATION SETTINGS (Android Only)

### Check if app is ignoring battery optimization

```dart
OptimizationBattery.isIgnoringBatteryOptimizations().then((onValue) {
    setState(() {
        if (onValue) {
            // Igonring Battery Optimization
        } else {
            // App is under battery optimization
        }
    });
});
```

### Take user to battery optimization settings

this opens the battery optimization settings. Apps can be placed on the whitelist through the settings UI

```dart
OptimizationBattery.openBatteryOptimizationSettings();
```

### BatteryOptimizations Observer

use BatteryOptimizationsObserver widget to detect the IgnoringBatteryOptimizations value after user openBatteryOptimizationSettings and return to the app

```dart
BatteryOptimizationsObserver(builder: (context, isIgonre) {
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
        })
```
```isIgonre``` will be null on ios and also at start before complete loading the state and will update the value every time user return to the app  


### CREDIT :

this plugin is inspired from [battery_optimization](https://pub.dev/packages/battery_optimization) but with supporting null safety and new android plugin APIs and BatteryOptimizationsObserver 
