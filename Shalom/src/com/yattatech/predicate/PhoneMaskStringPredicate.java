/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.predicate;

/**
 * A predicate which validates if given string is different from 
 * current mask
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class PhoneMaskStringPredicate implements StringPredicate {

    private final String mask;
    
    public PhoneMaskStringPredicate() {
        // as defined in Form
        this.mask = "(  )    -    ";
    }
    
    @Override
    public boolean accept(Object value) {
        if (value == null) {
            return false;
        }
        final String strValue = value.toString();
        return !strValue.equals(mask);
    }    
}
