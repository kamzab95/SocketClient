package com.kamzab95.socketclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Client client;
    String server_addr = "192.168.2.231";
    int port = 8889;
    Client client;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button connect = (Button) findViewById(R.id.connect_button);
        Button send = (Button) findViewById(R.id.send_button);
        Button up = (Button) findViewById(R.id.button_up);
        Button right = (Button) findViewById(R.id.button_right);
        Button left = (Button) findViewById(R.id.button_left);

        textView = (TextView) findViewById(R.id.textView2);



        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return touchButton(event, "up");
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return touchButton(event, "left");
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return touchButton(event, "right");
            }
        });

        send.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return touchButton(event, "down");
            }
        });

    }

    boolean touchButton(MotionEvent event, String message){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                client = new Client(server_addr, port, textView);
                client.setLoopMessage(message);
                client.execute();
                // PRESSED
                return true; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:
                // RELEASED
                client.stopLoop();
                return true; // if you want to handle the touch event
        }
        return false;
    }
}
