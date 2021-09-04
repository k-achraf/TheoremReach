import 'dart:async';
import 'package:flutter/services.dart';

typedef void OnRewardCenterOpened();
typedef void OnRewardCenterClosed();

class TheoremReach {
  static OnRewardCenterOpened? _onRewardCenterOpened;
  static OnRewardCenterClosed? _onRewardCenterClosed;

  final MethodChannel _channel;
  static TheoremReach get instance => _instance;

  static final TheoremReach _instance =
      TheoremReach.private(const MethodChannel("theorem_reach"));

  TheoremReach.private(MethodChannel channel) : _channel = channel {
    _channel.setMethodCallHandler(_platformCallHandler);
  }

  Future<void> init({required String apiKey, required String userId}) async {
    _channel.invokeMethod(
        "init", <String, String>{"apiKey": apiKey, "userId": userId});
  }

  Future<void> show() async {
    _channel.invokeMethod("show");
  }

  Future<void> _platformCallHandler(MethodCall call) async {
    if (call.method == "rewardCenterOpened") {
      _onRewardCenterOpened?.call();
    }
    if (call.method == "rewardCenterClosed") {
      _onRewardCenterClosed?.call();
    }
  }

  void onRewardCenterOpened(OnRewardCenterOpened onRewardCenterOpened) =>
      _onRewardCenterOpened = onRewardCenterOpened;
  void onRewardCenterClosed(OnRewardCenterClosed onRewardCenterClosed) =>
      _onRewardCenterClosed = onRewardCenterClosed;
}
