package com.experience.util;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemClock {

    private static final long PERIOD = 1;

    private volatile long now;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor((runnable) -> {
        Thread thread = new Thread(runnable, SystemClock.class.getSimpleName());
        thread.setDaemon(true);
        return thread;
    });
    private final Runnable updateClockRunner = () -> SystemClock.this.now = (System.currentTimeMillis());

    private SystemClock() {
        this.now = System.currentTimeMillis();
        startSchedule();
    }

    private static SystemClock instance() {
        return Holder.INSTANCE;
    }

    public static long now() {
        return instance().currentTimeMillis();
    }

    public static String nowDate() {
        return new Timestamp(instance().currentTimeMillis()).toString();
    }

    private void startSchedule() {
        scheduler.scheduleAtFixedRate(updateClockRunner, PERIOD, PERIOD, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now;
    }

    private static class Holder {
        static final SystemClock INSTANCE = new SystemClock();
    }

}
