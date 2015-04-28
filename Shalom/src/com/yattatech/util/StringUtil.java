/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.util;

import com.yattatech.predicate.StringPredicate;

/**
 * Utility class which takes care about any string 
 * operation, doing any dirty job for us
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 */
public class StringUtil {
    
    private StringUtil() {
        throw new AssertionError();
    }
    
    public static boolean isEmpty(String str) {
        return (str == null) || (str.isEmpty()) || (str.trim().isEmpty());
    }
    
    public static String getValue(Object value, StringPredicate p) {
        return p.accept(value) ? value.toString() : null;
    }
}
