/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Adriano
 */
public final class ReportCompilerGenerator {
    
    private static final Logger LOGGER = Logger.getLogger("ReportCompilerGenerator");
    private static final String PATH   = "./report/templates/quadrante.jrxml";
    
    public static void main(String... args) {
        try {
            final JasperReport jasperReport = JasperCompileManager.compileReport(PATH);
            final Serializable serializable = jasperReport.getCompileData();

        } catch (JRException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}
