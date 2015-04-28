/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.bg;

import com.yattatech.facade.SystemFacade;
import com.yattatech.settings.Settings;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Background worker responsible for savind every
 * once in a while current seminary, it's just to
 * avoid any data lost
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class SeminarySaver implements Runnable {
    
    private static final SystemFacade FACADE = SystemFacade.getInstance();
    private static final Logger LOGGER       = Logger.getLogger("SeminarySaver");
    private final long timeout;
    private final Thread worker;
    
    public SeminarySaver() {
        timeout = Long.parseLong(Settings.getProperty("seminary.saver.timeout"));
        worker  = new Thread(this, "SeminarySaver-ThreadWorker");
        worker.setDaemon(true);
        worker.setPriority(Thread.MIN_PRIORITY);        
    }
    
    public void start() {
        LOGGER.info("Starting savind worker thread");
        worker.start();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                TimeUnit.MINUTES.sleep(timeout);
            } catch (InterruptedException ex) {
                // not big deal
            }
            LOGGER.info("Saving seminary data");
            FACADE.saveSeminary();
        }
    }
}
