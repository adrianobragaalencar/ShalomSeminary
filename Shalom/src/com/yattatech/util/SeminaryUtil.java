/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.util;

import com.yattatech.Constants;
import com.yattatech.settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * Utility class responsible for taking care about all
 * seminary operations as create a new one and add new 
 * couple info 
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class SeminaryUtil {
    
    private static final Logger LOGGER         = Logger.getLogger(SeminaryUtil.class.getSimpleName());
    private static final String PATH           = Settings.getProperty("seminary.folder");
    private static final String IMAGE_PATH     = Settings.getProperty("seminary.image.folder");
    private static final String REPORT_PATH    = Settings.getProperty("seminary.report.folder");
    private static final boolean IMAGE_COPY    = Boolean.parseBoolean(Settings.getProperty("seminary.images.copy"));    
    private static final StringBuilder BUFFER  = new StringBuilder(1024);    
    private static final FilenameFilter FILTER = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(Constants.SHALOM_EXT);
        }
    };        
    private static String folderPath;
    
    private SeminaryUtil() {
        throw new AssertionError();
    }
    
    public static void createSeminaryFolder() {
        final File file         = new File(PATH);        
        final File imageFolder  = new File(PATH, IMAGE_PATH);
        final File reportFolder = new File(PATH, REPORT_PATH);        
        if (file.exists()) {
            LOGGER.info("Folder already exists");            
            imageFolder.mkdirs();
            reportFolder.mkdirs();            
        } else if (file.mkdirs()) {
            imageFolder.mkdirs();
            reportFolder.mkdirs();
            LOGGER.info("Folders created");
        } else {
            LOGGER.info("Impossible to create defined folder");
            LOGGER.info("Impossible to start application");
            System.exit(1);            
        }
    }    
    
    public static boolean hasSeminaries() {
        final File file = new File(PATH);
        String[] files  = null;
        if (file.exists()) {
            LOGGER.info(String.format("checking seminaries in %s", PATH));
            files = file.list(FILTER);
        } else {
            LOGGER.info("has no seminaries files created");
        }
        return ((files != null) && (files.length > 0));
    }
    
    public static String getSeminaryFilesPath() {
        if (folderPath != null) {
            return folderPath;
        }
        if (new File(PATH).exists()) {
            folderPath = PATH;
        }
        return folderPath;
    }
    
    private static String getSeminaryImagePath() {        
        synchronized (SeminaryUtil.class) {                        
            BUFFER.setLength(0);
            return BUFFER.append(getSeminaryFilesPath())
                         .append(File.separator)
                         .append(IMAGE_PATH)
                         .append(File.separator)
                         .toString();
        }               
    }
    
    public static String copyImageFileToSeminaryFolder(String imagePath) {
        if (!IMAGE_COPY) {
            return imagePath;
        }
        if (isInImageFolder(imagePath)) {
            return imagePath;
        }
        final File file = new File(imagePath);
        if (file.exists()) {
            final String ext  = FilenameUtils.getExtension(imagePath);
            final String path = getSeminaryImagePath()                 + 
                                java.util.UUID.randomUUID().toString() +
                                '.' + ext;
            InputStream in    = null;
            OutputStream out  = null;
            try {
                in  = new FileInputStream(imagePath);
                out = new FileOutputStream(path);
                IOUtils.copy(in, out);
                return path;
            } catch (IOException ioe) {
                LOGGER.log(Level.SEVERE, ioe.getMessage());
            } finally {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
            }
        }
        return null;
    }
    
    public static String getReportPath() {
        synchronized (BUFFER) {
            BUFFER.setLength(0);
            final String reportPath = BUFFER.append(getSeminaryFilesPath())
                                            .append(File.separator)
                                            .append(REPORT_PATH)
                                            .append(File.separator)                                            
                                            .toString();
            return reportPath;        
        }
    }
    
    private static boolean isInImageFolder(String path) {
        final String imagePath = getSeminaryImagePath();
        final boolean contains = path.startsWith(imagePath);
        LOGGER.info(String.format("Image is already in image folder %b", contains));
        return contains;
    }
}
