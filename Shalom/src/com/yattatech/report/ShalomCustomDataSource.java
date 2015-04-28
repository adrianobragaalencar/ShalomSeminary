/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.report;

import com.yattatech.domain.Couple;
import com.yattatech.domain.Husband;
import com.yattatech.domain.Seminary;
import com.yattatech.domain.Wife;
import com.yattatech.util.StringUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import static com.yattatech.Constants.EMPTY_STR;
import com.yattatech.domain.City;
import com.yattatech.domain.FedUnit;
import com.yattatech.domain.Neighbor;

/**
 * Custom data source responsible for generate our 
 * report 
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class ShalomCustomDataSource implements JRDataSource {

    private static final Logger LOGGER  = Logger.getLogger("ShalomCustomDataSource");
    private final StringBuilder builder = new StringBuilder(1024);
    private final List<Couple> couples;
    private BufferedImage defaultImage;
    private int index = -1;    
    
    public ShalomCustomDataSource(Seminary seminary) {
        couples = seminary.getOrderedCouplesByName();
    }
    
    @Override
    public boolean next()throws JRException {
        return ++index < couples.size();
    }

    @Override
    public Object getFieldValue(JRField field)throws JRException {
        Object value;
        final Couple couple    = couples.get(index);
        final Husband husband  = couple.getHusband();
        final Wife wife        = couple.getWife();
        final String fieldName = field.getName();
        
        if ("coupleName".equals(fieldName)) {
            value = getValueOrDefault(couple.getName(), EMPTY_STR);
        } else if ("coupleWeddingDay".equals(fieldName)) {          
            value = getDate(couple.getDay(), couple.getMonth());
        } else if ("coupleState".equals(fieldName)) {
            final FedUnit fedUnit = couple.getState();
            value = fedUnit == null ? EMPTY_STR : fedUnit.getDesc();
        } else if ("coupleCity".equals(fieldName)) {                      
            final City city = couple.getCity();
            value = city == null ? EMPTY_STR : city.getName();
        } else if ("coupleNeighbor".equals(fieldName)) {                      
            final Neighbor neighbor = couple.getNeighbor();
            value = neighbor == null ? EMPTY_STR : neighbor.getName();
        } else if ("coupleAddress".equals(fieldName)) {      
            value = getValueOrDefault(couple.getAddress(), EMPTY_STR);
        } else if ("coupleZipcode".equals(fieldName)) {                      
            value = getValueOrDefault(couple.getZipcode(), EMPTY_STR);
        } else if ("couplePhoto".equals(fieldName)) {            
            value = loadImageFromPath(couple.getPhotoPath());
        } else if ("husbName".equals(fieldName)) {
            value = getValueOrDefault(husband.getName(), EMPTY_STR);
        } else if ("husbEmail".equals(fieldName)) {          
            value = getValueOrDefault(husband.getEmail(), EMPTY_STR);
        } else if ("husbBirthday".equals(fieldName)) {          
            value = getDate(husband.getDay(), husband.getMonth());
        } else if ("husbPhones".equals(fieldName)) {
            value  = getPhones(husband.getTelephone(), husband.getCellphone());           
        } else if ("husbWork".equals(fieldName)) {          
            value = getValueOrDefault(husband.getWork(), EMPTY_STR);
        } else if ("wifeName".equals(fieldName)) {
            value = getValueOrDefault(wife.getName(), EMPTY_STR);
        } else if ("wifeEmail".equals(fieldName)) {          
            value = getValueOrDefault(wife.getEmail(), EMPTY_STR);
        } else if ("wifeBirthday".equals(fieldName)) {          
            value = getDate(wife.getDay(), wife.getMonth());
        } else if ("wifePhones".equals(fieldName)) {          
            value  = getPhones(wife.getTelephone(), wife.getCellphone());
        } else if ("wifeWork".equals(fieldName)) {                      
            value = getValueOrDefault(wife.getWork(), EMPTY_STR);
        } else {
            value = null;
        }       
        return value;
    }    
    
    private String getValueOrDefault(String value, String def) {
        return StringUtil.isEmpty(value) ? def : value;
    }
    
    private String getDate(String day, String month) {
        if ((StringUtil.isEmpty(day)) ||
            (StringUtil.isEmpty(month))) {
            return EMPTY_STR;
        }
        builder.setLength(0);
        return builder.append(day)
                      .append('/')
                      .append(month)
                      .toString();
    }
    
    private String getPhones(String phone, String cellphone) {        
        final boolean hasPhone = !StringUtil.isEmpty(phone);
        final boolean hasCell  = !StringUtil.isEmpty(cellphone);
        builder.setLength(0);
        if (hasPhone) {
            builder.append(phone);
            if (hasCell) {
                builder.append('/')
                       .append(cellphone);                
            }
        } else if (hasCell) {
            builder.append(cellphone);
        }
        return builder.toString();
    }
    
    private BufferedImage loadImageFromPath(final String path) {
        try {
            // It could be reentrant, cause it's possible to load default image            
            // and get an error and try again
            return (StringUtil.isEmpty(path)) ? loadDefaultImage() : ImageIO.read(new File(path));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.getMessage());            
            return loadDefaultImage();
        }        
    }
    
    private BufferedImage loadDefaultImage() {    
        if (defaultImage == null) {
            try {
                defaultImage = ImageIO.read(getClass().getResourceAsStream("/shalom.jpg"));
            } catch (IOException ioe) {
                LOGGER.log(Level.SEVERE, ioe.getMessage());
            }
        }
        return defaultImage;
    }
}
