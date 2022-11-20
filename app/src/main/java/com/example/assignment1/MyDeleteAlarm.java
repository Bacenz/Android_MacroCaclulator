package com.example.assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

public class MyDeleteAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //The alarm action, delete food_file when called
        String file_name = "food_file";
        String directory = context.getFilesDir().getAbsolutePath();
        File file = new File (directory + "/" + file_name);
        file.delete();
    }
}
