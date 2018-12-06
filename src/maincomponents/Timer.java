package maincomponents;

import java.util.ArrayList;

public class Timer extends Thread {

    private long timeRun;
    private long lastTime;
    private static final long interval = 1000;
    private boolean running = false;
    private Tickable listener;

    public Timer(Tickable listener){
        this.listener = listener;
        start();
    }

    @Override
    public void run(){
        while(true){
            synchronized (this){
                listener.tick();
                if (!running){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void pause(){
        this.running = false;
    }

    public synchronized void play(){
        this.running = true;
        this.notify();
    }
}
