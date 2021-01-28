package com.yello.task.emitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.yello.task.emitter.response".equals((intent.getAction()))) {
            Toast.makeText(context, "(Emitter App)\nResponse From MiddleMan APP : SUCCESS!" , Toast.LENGTH_SHORT).show();

        }
    }
}
