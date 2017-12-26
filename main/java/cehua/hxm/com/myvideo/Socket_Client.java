package cehua.hxm.com.myvideo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Socket_Client {
    private Socket client;
    private String ip;
    private int port;
    private ImageView img;
    private Bitmap bitmap;

    public Socket_Client(String ip, int port, ImageView img) {
        Log.e("----------", "构造");
        this.ip = ip;
        this.port = port;
        this.img = img;
        new Th().start();
    }

    public void client() {
        try {
            // 创建Socket
            client = new Socket();
            // 设置发送地址
            SocketAddress addr = new InetSocketAddress(ip, port);
            // 超时1秒，并连接服务器
            client.connect(addr, 30000);
            StringBuffer string = new StringBuffer();
            //读取服务器发来的数据
            while (true) {
                InputStream inputStream = client.getInputStream();
                int count = 0;
                while (count == 0) {
                    count = inputStream.available();
                }
                byte[] b = new byte[count];
                inputStream.read(b);
                String res = new String(b);
                if (res.indexOf("eof") > 0) {
                    string.append(res.substring(0, res.length() - 3));
                    Log.e("----------", "处理数据中");
                    bitmap = generateImage(string.toString());
                    Message m=new Message();
                    m.what=1;
                    handler.sendMessage(m);
                    string.setLength(0);
                } else {
                    string.append(res);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Bitmap generateImage(String imgStr) {
        if (imgStr == null)
            return null;
        try {
            // 解密
            Log.e("----------", "解密数据");
            byte[] b = Base64Util.decode(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            if (b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("--------","iiiiiiiii");
            img.setImageBitmap(bitmap);
        }
    };
    class Th extends Thread {
        @Override
        public void run() {
            super.run();
            client();
        }
    }


}
