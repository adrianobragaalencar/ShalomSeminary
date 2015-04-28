/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

/**
 * Object with a zipcode search result
 *
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class ZipcodeSearch extends Domain {    
    
    private final String streetName;
    private final String zipCode;
    private int codeNeighbor;
    private int codeCity;
    private int codeFU;

    public ZipcodeSearch(String streetName, String zipCode,
                         int codeNeighbor, int codeCity, int codeFU) {
        this.streetName   = streetName;
        this.zipCode      = zipCode;
        this.codeNeighbor = codeNeighbor;
        this.codeCity     = codeCity;
        this.codeFU       = codeFU;
    }
    
    public String getStreetName() {
        return streetName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getCodeNeighbor() {
        return codeNeighbor;
    }

    public int getCodeCity() {
        return codeCity;
    }

    public int getCodeFU() {
        return codeFU;
    }
}
