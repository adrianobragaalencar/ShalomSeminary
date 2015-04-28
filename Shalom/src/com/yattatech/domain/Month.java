/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

/**
 * Represents a month of year
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class Month extends Domain implements Codeable {
   
    private final int code;
    private final String desc;
    private final int validDays;
    
    public Month(int code, String desc, int validDays) {
        this.code      = code;
        this.desc      = desc;
        this.validDays = validDays;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public int getValidDays() {
        return validDays;
    }
    
    @Override
    public String toString() {
        return desc;
    }
}
