/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.facade;

import com.yattatech.Constants;
import com.yattatech.dao.DaoManager;
import com.yattatech.domain.City;
import com.yattatech.domain.FedUnit;
import com.yattatech.domain.ZipcodeSearch;
import com.yattatech.domain.Month;
import com.yattatech.domain.Neighbor;
import com.yattatech.domain.Seminary;
import com.yattatech.io.ShalomFileReader;
import com.yattatech.io.ShalomFileWriter;
import com.yattatech.report.ShalomCustomDataSource;
import com.yattatech.util.SeminaryUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;

/**
 * Facade used by Shalom system to take of all
 * business operations
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class SystemFacade {
            
    private static final Logger LOGGER           = Logger.getLogger("SystemFacade");
    private static final DateFormat DATE_FORMAT  = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
    private static final SystemFacade UNIQUE     = new SystemFacade();
    private static final ArrayList<Month> MONTHS = new ArrayList<Month>() {
        {
            add(new Month(1,  "Janeiro",   31));
            add(new Month(2,  "Fevereiro", 29));
            add(new Month(3,  "Março",     31));
            add(new Month(4,  "Abril",     30));
            add(new Month(5,  "Maio",      31));
            add(new Month(6,  "Junho",     30));
            add(new Month(7,  "Julho",     31));
            add(new Month(8,  "Agosto",    31));
            add(new Month(9,  "Setembro",  30));
            add(new Month(10, "Outubro",   31));
            add(new Month(11, "Novembro",  30));
            add(new Month(12, "Dezembro",  31));
        }
    };            
    private final Object locker = new Object();
    private final DaoManager daoManager;
    private final ShalomFileWriter fileWriter;
    private final ShalomFileReader fileReader;
    private Seminary seminary;
    
    private SystemFacade() {
        daoManager = new DaoManager();
        fileWriter = new ShalomFileWriter();
        fileReader = new ShalomFileReader();
    }
    
    public static SystemFacade getInstance() {
        return UNIQUE;
    }
    
    public List<Month> getMonths() {
        LOGGER.info("getting months");
        return MONTHS;
    }
    
    public List<FedUnit> getFederativeUnits() {
        LOGGER.info("getting federative units");
        return daoManager.getFederativeUnits();
    }
    
    public List<City> getCitiesByFedUnit(FedUnit fedUnit) {
        LOGGER.info("getting months");
        return daoManager.getCitiesByFedUnit(fedUnit);
    }
    
    public List<Neighbor> getNeighborsByCity(City city) {
        LOGGER.info(String.format("getting neighboors by city %s", city.getName()));
        return daoManager.getNeighborsByCity(city);
    }
    
    public List<String> getWorksDescription() {
        LOGGER.info("getting work descriptions");
        return daoManager.getWorksDescription();
    }
    
    public ZipcodeSearch searchByZipcode(String zipcode) {
        LOGGER.info(String.format("searching address by zipcode %s", zipcode));
        return daoManager.searchByZipcode(zipcode);
    }
    
    public boolean hasSeminaries() {
        return SeminaryUtil.hasSeminaries();
    }
    
    public String getSeminaryFilesPath() {
        LOGGER.info("getting seminary files path");
        return SeminaryUtil.getSeminaryFilesPath();
    }
        
    public boolean createNewSeminary(String name) {
        // Just only one seminary valid per execution
        seminary = new Seminary();
        seminary.setFilePath(generateFilename(name));
        return fileWriter.write(seminary);
    }
    
    public void saveSeminary() {
        synchronized (locker) {
            if (seminary != null) {
                fileWriter.write(seminary);
            }            
        }        
    }    
    
    public boolean loadSeminary(String name) {
    
        LOGGER.info(String.format("loading seminary from path %s", name));
        seminary = fileReader.read(name);
        return (seminary != null);
    }
    
    public Seminary getSeminary() {
        synchronized (locker) {
            return seminary;
        }        
    }
    
    public String copyImageFileToSeminaryFolder(String imagePath) {
        return SeminaryUtil.copyImageFileToSeminaryFolder(imagePath);
    }
    
    public Month getMonthByDesc(String monthDesc) {
        for (Month month : MONTHS) {
            if (month.getDesc().equals(monthDesc)) {
                return month;
            }
        }
        return null;
    }
    
    public boolean generateReport(String seminaryTitle) {
        saveSeminary();
        OutputStream output = null;        
        try {                   
            final Map<String, Object> params = new HashMap<String, Object>();
            params.put("seminaryTitle", seminaryTitle);
            final File file                  = new File(SeminaryUtil.getReportPath() + seminary.getPdfReportFilename());
            output                           = new FileOutputStream(file);             
            final JasperPrint print          = JasperFillManager.fillReport(Constants.REPORT_PATH, 
                                                                            params, 
                                                                            new ShalomCustomDataSource(seminary));
            JasperExportManager.exportReportToPdfStream(print, output); 
            return true;
        } catch (Exception ex) {                            
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return false;
        } finally {
            IOUtils.closeQuietly(output);
        }                
    }
    
    private String generateFilename(String name) {
        return new StringBuilder().append(SeminaryUtil.getSeminaryFilesPath())
                                  .append(File.separatorChar)
                                  .append(name)
                                  .append('_')
                                  .append(DATE_FORMAT.format(new Date()))
                                  .append('.')
                                  .append(Constants.SHALOM_EXT)
                                  .toString();        
    }
}
