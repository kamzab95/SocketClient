package com.kamzab95.socketclient;

import android.os.AsyncTask;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by kamzab95 on 24/05/2017.
 */

public class Client extends AsyncTask<Void, Void, Void> {
    String addr;
    int port;
    String response = "";
    TextView textView;
    boolean loop = false;
    String message = "test_message";

    Client(String addr, int port, TextView textView){
        this.addr = addr;
        this.port = port;
        this.textView = textView;
    }

    void setLoopMessage(String text){
        message = text;
        loop = true;
    }

    void stopLoop(){
        loop = false;
    }
    @Override
    protected Void doInBackground(Void... params) {

        Socket socket = null;
        Log.d("press", "press");

        try{
            socket = new Socket(addr, port);
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);


            while(loop) {
                OutputStream out = socket.getOutputStream();
                PrintWriter output = new PrintWriter(out);
                output.println(message);
                output.flush(); //output.close();
                Log.d("done_for_now", "done2");
            }

            OutputStream out = socket.getOutputStream();
            PrintWriter output = new PrintWriter(out);
            output.println("end of transmision [END]");
            output.flush(); //output.close();

            Log.d("done_for_now", "done2");

            int bytesRead;
            Log.d("wait for input", "...");
            InputStream inputStream = socket.getInputStream();
            Log.d("Input rec", "res");
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                Log.d("bytes", "bytes "+bytesRead);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
                Log.d("bytes", "bytes " + response);

                if(response.contains("[END]")){
                    response.replace("[END]", "\n");
                    //inputStream.close();
                    break;
                }
            }

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null){
                try{
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("resp", response);
        textView.setText(response);
        super.onPostExecute(aVoid);
    }

}
