/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech;

import com.yattatech.settings.Settings;
import java.io.File;

/**
 * Class with usefull constants used by the entire
 * system
 *
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 */
public final class Constants {
    
    public static final int ZIP_CODE_SIZE  = 8;
    public static final String VERSION     = "v1.0";
    public static final String SHALOM_EXT  = "shalom";
    public static final String EMPTY_STR   = "";
    public static final String REPORT_PATH = Settings.getProperty("seminary.report.path");
    
    private Constants() {
        throw new AssertionError();
    }
}
