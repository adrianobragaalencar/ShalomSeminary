/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

/**
 * Class that represents a neighbor from Brazil
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class Neighbor extends Domain implements Codeable {
 
    private final int codeCity;
    private final int code;
    private String name;
    
    public Neighbor(int codeCity, int code, String name) {
        this.codeCity = codeCity;
        this.code     = code;
        this.name     = name;
    }
    
    public int getCodeCity() {
        return codeCity;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 47;
        hash     = 31 * hash + codeCity;
        hash     = 31 * hash + code;
        hash     = 31 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (getClass() == obj.getClass()) {
            final Neighbor other = (Neighbor) obj;
            return ((codeCity == other.codeCity) &&
                    (code     == other.code)     &&
                    (name     == null) ? other.name == null : name.equals(other.name));
        }
        return false;
    }
}
