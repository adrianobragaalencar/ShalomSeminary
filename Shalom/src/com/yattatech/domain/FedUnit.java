/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

/**
 * A class that represents a federative unit from Brazil
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class FedUnit extends Domain implements Codeable {
    
    private final int code;
    private final String desc;
    
    public FedUnit(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }    
    
    @Override
    public String toString() {
        return desc;
    }        

    @Override
    public int hashCode() {
        int hash = 47;
        hash     = 31 * hash + code;
        hash     = 31 * hash + (desc == null ? 0 : desc.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (getClass() == obj.getClass()) {
            final FedUnit other = (FedUnit)obj;
            return ((code == other.code) &&
                    (desc == null) ? other.desc == null : desc.equals(other.desc));
        }        
        return false;
    } 
}
