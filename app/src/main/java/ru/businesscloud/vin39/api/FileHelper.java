package ru.businesscloud.vin39.api;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileHelper extends Thread {

    private String mPath;
    private String mName;
    private Context mContext;
    private Delegate mDelegate;
    public interface Delegate {
        void comlete();
    };

    public FileHelper(String path, String name, Delegate delegate) {
        mPath = path;
        mName = name;
        mDelegate = delegate;
    }
    @Override
    public void run() {
        try {
            downloadUsingStream(mPath, mName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadUsingStream(String urlStr, String name) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +"/" +name);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
        mDelegate.comlete();
    }
}
