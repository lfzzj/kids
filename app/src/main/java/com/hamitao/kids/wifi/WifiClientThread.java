package com.hamitao.kids.wifi;

import android.content.Context;
import android.net.DhcpInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @data on 2018/5/22 18:36
 * @describe:
 */

public class WifiClientThread extends Thread {
    public Socket socket;
    public Context context;
    public static final int SERVERPORT = 8191;

    public OutputStream os;
    public InputStream in;
    private String mSSID = "";
    private String mPasswd = "";

    public WifiClientThread(Context context) {
        this.context = context;
    }

    public void setWifiInfo(String SSID, String passwd) {
        mSSID = SSID;
        mPasswd = passwd;
    }

    public void run() {
        try {
            DhcpInfo dhcp = new WifiManageUtils(context).getDhcpInfo();
            int ipInt = dhcp.gateway;
            String serverip = String.valueOf(new StringBuilder()
                    .append((ipInt & 0xff)).append('.').append((ipInt >> 8) & 0xff)
                    .append('.').append((ipInt >> 16) & 0xff).append('.')
                    .append(((ipInt >> 24) & 0xff)).toString()

            );
            socket = new Socket(serverip, SERVERPORT);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    if (socket == null) {
                        return;
                    }
                    System.out.println("client connect");

                    try {
                        os = socket.getOutputStream();
                        in = socket.getInputStream();
                        os.write((mSSID + ";" + mPasswd).getBytes("utf-8"));// 将文件名和文件大小传给接收端
                        os.flush();

                    } catch (Exception e) {

                        e.printStackTrace();
                    } finally {

                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}