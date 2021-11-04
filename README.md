# optimization_battery

check if the app is ignoring battery optimizations , open IGNORE BATTERY OPTIMIZATION SETTINGS (Android Only)



#
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
#
### Take user to battery optimization settings
this opens the battery optimization settings.  Apps can be placed on the whitelist through the settings UI

```dart
OptimizationBattery.openBatteryOptimizationSettings();
```

#
### CREDIT : 
this plugin is inspired(copying code) from [battery_optimization](https://pub.dev/packages/battery_optimization) but after update to null safety and new android plugin APIs