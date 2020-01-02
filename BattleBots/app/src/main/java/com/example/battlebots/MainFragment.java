package com.example.battlebots;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainFragment extends Fragment implements Button.OnClickListener {

    TextView output;
    Button mkconn;
    Button leftup;
    Button leftdown;
    Button rightup;
    Button rightdown;
    Button up;
    Button down;
    Button left;
    Button right;
    Button fire;
    EditText angle;
    Button scan;
    Button noop;
    EditText hostname, port;
    Thread myNet;
    EditText botSetup;
    boolean connected;
    doNetwork stuff;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_main, container, false);
        output = myView.findViewById(R.id.output);
        output.append("\n");
        hostname = myView.findViewById(R.id.EThostname);
        botSetup = myView.findViewById(R.id.botsetup);
//        hostname.setText("10.0.2.2"); //This address is the localhost for the computer the emulator is running on.
//        hostname.setText("192.168.0.105");
//        hostname.setText("10.121.134.195");
        hostname.setText("10.216.217.131");
//        hostname.setText("10.121.174.200");
        port = myView.findViewById(R.id.ETport);
        mkconn = myView.findViewById(R.id.makeconn);
        mkconn.setOnClickListener(this);
        left = myView.findViewById(R.id.left);
        left.setOnClickListener(this);
        right = myView.findViewById(R.id.right);
        right.setOnClickListener(this);
        up = myView.findViewById(R.id.up);
        up.setOnClickListener(this);
        down = myView.findViewById(R.id.down);
        down.setOnClickListener(this);
        leftdown = myView.findViewById(R.id.leftdown);
        leftdown.setOnClickListener(this);
        leftup = myView.findViewById(R.id.leftup);
        leftup.setOnClickListener(this);
        rightdown = myView.findViewById(R.id.rightdown);
        rightdown.setOnClickListener(this);
        rightup = myView.findViewById(R.id.rightup);
        rightup.setOnClickListener(this);
        fire = myView.findViewById(R.id.fire);
        fire.setOnClickListener(this);
        angle = myView.findViewById(R.id.angle);
        scan = myView.findViewById(R.id.scan);
        scan.setOnClickListener(this);
        noop = myView.findViewById(R.id.noop);
        noop.setOnClickListener(this);
        connected = false;
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.makeconn:
                if(!connected) {
                    stuff = new doNetwork();
                    myNet = new Thread(stuff);
                    myNet.start();
                    connected = true;
                }
                break;
            case R.id.left:
                if(connected){
                    stuff.execute = "move -1 0";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.right:
                if(connected){
                    stuff.execute = "move 1 0";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.up:
                if(connected){
                    stuff.execute = "move 0 -1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.down:
                if(connected){
                    stuff.execute = "move 0 1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.leftdown:
                if(connected){
                    stuff.execute = "move -1 1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.leftup:
                if(connected){
                    stuff.execute = "move -1 -1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.rightdown:
                if(connected){
                    stuff.execute = "move 1 1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.rightup:
                if(connected){
                    stuff.execute = "move 1 -1";
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.fire:
                if(connected){
                    String fireAngle = angle.getText().toString();
                    if(!fireAngle.equalsIgnoreCase("")) {
                        stuff.execute = "fire " + fireAngle;
                    }else{
                        stuff.execute = "fire 0";
                    }
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.scan:
                if(connected){
                    stuff.execute = "scan";
//                    mkmsg("scan clicked\n");
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
            case R.id.noop:
                if(connected){
                    stuff.execute = "noop";
//                    mkmsg("noop clicked\n");
                }else{
                    mkmsg("Need to connect first..\n");
                }
                break;
        }


        //An example of how you would write from here via the thread.  Note,
        //this will likely force close here, because the connection is not fully made at this point.
        //the thread just started.
        //stuff.out.println("hi there.");

    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            output.append(msg.getData().getString("msg"));
            return true;
        }

    });

    public void mkmsg(String str) {
        //handler junk, because thread can't update screen!
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("msg", str);
        msg.setData(b);
        handler.sendMessage(msg);
    }

    /*
     * this code does most of the work in a thread, so that it doesn't lock up the activity_main (UI) thread
     * It call mkmsg (which calls the handler to update the screen)
     */
    class doNetwork implements Runnable {
        public PrintWriter out;
        public BufferedReader in;
        public String execute;

        public doNetwork(){
            execute = "";
        }

        public void run() {
            int p = Integer.parseInt(port.getText().toString());
            String h = hostname.getText().toString();
            mkmsg("Host is " + h + "\n");
            mkmsg("Port is " + p + "\n");
            try {
                InetAddress serverAddr = InetAddress.getByName(h);
                mkmsg("Attempt Connecting..." + h + "\n");
                Socket socket = new Socket(serverAddr, p);

                //made connection, setup the read (in) and write (out)
                mkmsg("Connected.." + h + "\n");
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //now send a message to the server and then read back the response.
                try {
                    String message = botSetup.getText().toString();
                    //write a message to the server
                    mkmsg(message+"\n");
                    out.println(message);

                    String str;
                    while (!(str = in.readLine()).split(" ", 2)[0].equalsIgnoreCase("Status")) {
                        mkmsg(str+"\n");
                    }
                    mkmsg(str + "\n");
                    String status[] = str.split(" ");
                    if (status[0].equalsIgnoreCase("Status")) {
                        if (Integer.parseInt(status[3]) != 0 || Integer.parseInt(status[4]) != 0) {
                            execute = "noop";
                        }
                    }

                    while(connected) {
                        if(!execute.isEmpty()) {
                            mkmsg(execute + "\n");
                            out.println(execute);
//                        execute = "noop";
                            while (!(str = in.readLine()).split(" ", 2)[0].equalsIgnoreCase("Status")) {
                                mkmsg(str + "\n");
                                if (str.equalsIgnoreCase("Info Dead") || str.equalsIgnoreCase("Info GameOver")) {
                                    connected = false;
                                    break;
                                }
                            }
                            if (connected) {
                                mkmsg(str + "\n");
                                status = str.split(" ");
                                if (status[0].equalsIgnoreCase("Status")) {
                                    if (Integer.parseInt(status[3]) != 0 || Integer.parseInt(status[4]) != 0) {
                                        execute = "noop";
                                    }else{
                                        execute = "";
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    mkmsg(e.toString()+"\n");
                    mkmsg("Error happened sending/receiving\n");
                    connected = false;

                } finally {
                    mkmsg("Closing connection...");
                    in.close();
                    out.close();
                    socket.close();
                }
//                in.close();
//                out.close();
//                socket.close();

            } catch (Exception e) {
                mkmsg(e.toString());
                mkmsg("Unable to connect...\n");
                connected = false;
            }
        }
    }
}
