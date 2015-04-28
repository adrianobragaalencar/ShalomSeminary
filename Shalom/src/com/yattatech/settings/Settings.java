/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yattatech.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Adriano
 */
public final class Settings {
    
    private static final Properties PROPERTIES = getPropertiesFile();
    
    private Settings() {
            throw new AssertionError();
    }
	
    private static Properties getPropertiesFile() {

        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            inputStream           = Settings.class.getResourceAsStream("/shalom.config.properties");
            properties.load(inputStream);
            return properties;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {       
            if (inputStream != null) {
                try { inputStream.close(); } catch (Exception e) {}            
            }            
        }
    }
	
    /**
     * Searches for the property with the specified name in 
     * this property list.
     * 
     * @param name property name
     * @return the found value or <code>null</code> if
     * nothing was found
     * 
     */
    public static String getProperty(String name) {
        return PROPERTIES.getProperty(name);
    }
	
    /**
     * Searches for the property with the specified name in 
     * this property list and cast to int.
     * 
     * @param name property name
     * @return the found value or <code>0</code> if
     * nothing was found
     * 
     */
    public static int getPropertyAsInt(String name) {
        String value = getProperty(name);
        return value == null ? 0 : Integer.parseInt(value);
    }	
}
