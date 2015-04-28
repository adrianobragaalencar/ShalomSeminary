/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.predicate;

/**
 * Interface that tests a given value applying a 
 * rule if true then the value is returned if false
 * predicate executor can do whatever it wants
 *
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 */
public interface StringPredicate {
        
    boolean accept(Object value);
}
