import 'package:flutter/material.dart';
import 'package:theorem_reach/theorem_reach.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    TheoremReach.instance
        .init(apiKey: "<apiKey>", userId: "externalUserId");
    TheoremReach.instance.onRewardCenterOpened(() {
      print('opened');
    });
    TheoremReach.instance.onRewardCenterClosed(() {
      print('closed');
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed: () async {
              await TheoremReach.instance.show();
            },
            child: Text("Show"),
          ),
        ),
      ),
    );
  }
}
