package com.zeyi.highconcurrence.thread.downloader;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yangsen1 on 2017/4/10.
 * 外部方法陷阱
 * updateProgress调用了一个外部方法，我们对外部方法一无所知，可能会持有一把锁，使用了两把锁就有可能发生死锁
 *
 */
public class Downloader extends Thread{
    private InputStream                     in;
    private OutputStream                    out;
    private ArrayList<ProgressListener>     listeners;

    public Downloader(URL url,String outputStream) throws IOException {
        in          = url.openConnection().getInputStream();
        out         = new FileOutputStream(outputStream);
        listeners   = new ArrayList<ProgressListener>();
    }

    public synchronized void addListener(ProgressListener progressListener){
        listeners.add(progressListener);
    }

    public synchronized void removeListener(ProgressListener progressListener){
        listeners.remove(progressListener);
    }
    private synchronized void updateProgress(int n){
        for (ProgressListener progressListener:listeners){
            progressListener.onProgress(n);
        }
    }

    @Override
    public void run() {
        int n = 0,total = 0;
        byte[] buffer = new byte[1024];

        try {
            while ((n=in.read(buffer))!=-1){
                out.write(buffer,0,n);
                total +=n;
                updateProgress(total);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
