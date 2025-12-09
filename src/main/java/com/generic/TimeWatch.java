package com.generic;

import java.util.concurrent.TimeUnit;

public class TimeWatch {
	long starts;

    public static TimeWatch start() {
        return new TimeWatch();
    }

    private TimeWatch() {
        reset();
    }

    public TimeWatch reset() {
        starts = System.currentTimeMillis();
        return this;
    }

    public long time() {
        long ends = System.currentTimeMillis();
        return ends - starts;
    }

    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.MILLISECONDS);
    }
    
    public static void main(String args[]) throws InterruptedException {
    	TimeWatch watch = TimeWatch.start();
        Thread.sleep(5000);
       for(int i = 0;i<=5;i++) {
    	   Thread.sleep(1000);
       }
       long passedTimeInMs = watch.time();
        long passedTimeInSeconds = watch.time(TimeUnit.SECONDS);
        System.out.println(passedTimeInSeconds);
    }
}
