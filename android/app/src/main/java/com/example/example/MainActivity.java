package com.example.example;

import android.content.Intent;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    // Channel name
    private static final String CHANNEL = "com.example.example/message";

    // Result variable
    private MethodChannel.Result myResult;

    // Request code
    private static final int REQUEST_CODE = 1234;

    // Invoked method
    private void getMessageAndroid() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
    
    // Configure flutter engine
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        // Method channel
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
            .setMethodCallHandler(
                (call, result) -> {
                    myResult = result;

                    // Invoked method handling
                    if (call.method.equals("getMessageAndroid")) {
                        try {
                            getMessageAndroid();
                        } catch (Exception e) {
                            myResult.error("Unavailable", "Opennig SecondActivity is not available", null);
                        }
                    } else {
                        myResult.notImplemented();
                    }
                }
            );
    }

    // Activity result from invoked method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String resultString = data.getData().toString();
                myResult.success(resultString);
            } else {
                myResult.error("Unavailable", "Result from SecondActivity is not OK", null);
            }
        }
    }
}
