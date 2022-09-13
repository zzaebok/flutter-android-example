import 'package:flutter/material.dart';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter - Android Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter - Android Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  
  // MethodChannel
  static const platform = MethodChannel("com.example.example/message");

  // Message
  String _message = "Initial message";

  // Invoke Method
  Future<void> _getMessage() async {
    String message;
    try {
      message = await platform.invokeMethod('getMessageAndroid');
    } on PlatformException {
      message = "Failed to get message from Android";
    }

    setState(() {
      _message = message;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(_message),
            ElevatedButton(onPressed: _getMessage, child: const Text("Get message"))
          ],
        ),
      ),
    );
  }
}
