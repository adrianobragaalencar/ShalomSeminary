/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.dao;

import com.yattatech.domain.City;
import com.yattatech.domain.FedUnit;
import com.yattatech.domain.Neighbor;
import com.yattatech.domain.ZipcodeSearch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple dao class responsible for inserting/updating/deleting 
 * shalom data.
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class DaoManager {
    
    private static final Logger LOGGER          = Logger.getLogger("DaoManager");
    private static final Neighbor NULL_NEIGHBOR = new Neighbor(-1, -1, "");
    private final String url;
    private List<FedUnit> fedUnits;
    private List<String> works;
    private static final String ZIPCODE_SEARCH = "select s.street_name,"              +
                                                         "s.zipcode,"                 +
                                                         "n.cd_neighbor,"             +
                                                         "c.cd_city,"                 +
                                                         "u.cd_uf"                    +            
                                                 " from street s,"                    +
                                                       "neighbor n,"                  +
                                                       "city c,"                      +
                                                       "uf u"                         +
                                                 " where s.zipcode = %s and"          + 
                                                 " n.cd_neighbor = s.cd_neighbor and" +
                                                 " c.cd_city = n.cd_city and"         +
                                                 " u.cd_uf = c.cd_uf";
	     
    
    public DaoManager() {
        url = "jdbc:sqlite::resource:" + getClass().getResource("/shalom.db").toString();
        System.out.println(url);            
        try {            
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException cnfe) {
            LOGGER.log(Level.SEVERE, cnfe.getMessage());
            throw new RuntimeException(cnfe);
        }
    }    
    
    public List<FedUnit> getFederativeUnits() {
        if (fedUnits == null) {
            fedUnits = new ArrayList<FedUnit>();    
            Connection conn  = null;
            Statement statm  = null; 
            ResultSet result = null;
            try {
                conn   = getConnection();
                statm  = conn.createStatement();
                result = statm.executeQuery("select * from uf");
                while (result.next()) {
                    final int code    = result.getInt("cd_uf");
                    final String desc = result.getString("uf_desc");
                    fedUnits.add(new FedUnit(code, desc));
                }
            } catch (SQLException sqle) { 
                LOGGER.log(Level.SEVERE, sqle.getMessage());
            } finally {
                closeResultSet(result);
                closeStatement(statm);
                closeConnection(conn);
            }           
            fedUnits = Collections.unmodifiableList(fedUnits);
        }        
        return fedUnits;
    }
    
    public List<City> getCitiesByFedUnit(FedUnit fedUnit) {

        List<City> cities = new ArrayList<City>();
        Connection conn   = null;
        Statement statm   = null; 
        ResultSet result  = null;
        try {
            conn   = getConnection();
            statm  = conn.createStatement();
            result = statm.executeQuery("select * from city where cd_uf = " + fedUnit.getCode());
            while (result.next()) {
                final int codeUF  = result.getInt("cd_uf");
                final int code    = result.getInt("cd_city");
                final String name = result.getString("city_name");
                cities.add(new City(codeUF, code, name));
            }
        } catch (SQLException sqle) { 
            LOGGER.log(Level.SEVERE, sqle.getMessage());
        } finally {
            closeResultSet(result);
            closeStatement(statm);
            closeConnection(conn);
            return cities;
        }                   
    }
    
    public List<Neighbor> getNeighborsByCity(City city) {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        Connection conn          = null;
        Statement statm          = null; 
        ResultSet result         = null;
        try {
            neighbors.add(NULL_NEIGHBOR);
            conn   = getConnection();
            statm  = conn.createStatement();
            result = statm.executeQuery("select * from neighbor where cd_city = " + city.getCode());
            while (result.next()) {
                final int codeCity = result.getInt("cd_city");
                final int code     = result.getInt("cd_neighbor");
                final String name  = result.getString("neighbor_name");
                neighbors.add(new Neighbor(codeCity, code, name));
            }
        } catch (SQLException sqle) { 
            LOGGER.log(Level.SEVERE, sqle.getMessage());
        } finally {
            closeResultSet(result);
            closeStatement(statm);
            closeConnection(conn);
            return neighbors;
        }                           
    }          
    
    public ZipcodeSearch searchByZipcode(String zipcode) {
        ZipcodeSearch zipcodeSearch = null;
        Connection conn             = null;
        Statement statm             = null; 
        ResultSet result            = null;
        try {
            conn   = getConnection();
            statm  = conn.createStatement();
            System.out.println(String.format(ZIPCODE_SEARCH, zipcode));
            result = statm.executeQuery(String.format(ZIPCODE_SEARCH, zipcode));
            if (result.next()) {
                final String streetName = result.getString("street_name");
                final String zipCode    = result.getString("zipcode");
                final int codeNeighbor  = result.getInt("cd_neighbor");
                final int codeCity      = result.getInt("cd_city");
                final int codeFU        = result.getInt("cd_uf");
                zipcodeSearch           = new ZipcodeSearch(streetName, zipCode, codeNeighbor, codeCity, codeFU);                
            }
        } catch (SQLException sqle) { 
            LOGGER.log(Level.SEVERE, sqle.getMessage());
        } finally {
            closeResultSet(result);
            closeStatement(statm);
            closeConnection(conn);
            return zipcodeSearch;
        }                                   
    }
    
    public List<String> getWorksDescription() {
        if (works != null) {
            return works;
        }
        works            = new ArrayList<String>();
        Connection conn  = null;
        Statement statm  = null; 
        ResultSet result = null;
        try {
            conn   = getConnection();
            statm  = conn.createStatement();
            result = statm.executeQuery("select desc from work");
            while (result.next()) {
                works.add(result.getString("desc"));
            }
        } catch (SQLException sqle) { 
            LOGGER.log(Level.SEVERE, sqle.getMessage());
        } finally {
            closeResultSet(result);
            closeStatement(statm);
            closeConnection(conn);
            return works;
        }                                   
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
    
    private void closeConnection(Connection conn) {
        try {
            if (conn != null) {
               conn.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }
    
    private void closeStatement(Statement statm) {
        try {
            if (statm != null) {
               statm.close();
            }            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }        
    }

    private void closeResultSet(ResultSet result) {
        try {
            if (result != null) {
               result.close();
            }                        
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }        
    }
}
