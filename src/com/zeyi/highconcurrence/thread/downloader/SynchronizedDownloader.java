package com.zeyi.highconcurrence.thread.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yangsen1 on 2017/4/10.
 * 改造updateProgress方法，调用外部方法时不用加锁，使用保护性复制
 */
public class SynchronizedDownloader extends Thread{
    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;

    public SynchronizedDownloader(URL url,String outputStream) throws IOException {
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
    private  void updateProgress(int n){
        ArrayList<ProgressListener> listenersCopy;
        synchronized(this){
            listenersCopy = (ArrayList<ProgressListener>) listeners.clone();
        }
        for (ProgressListener progressListener:listenersCopy){
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
