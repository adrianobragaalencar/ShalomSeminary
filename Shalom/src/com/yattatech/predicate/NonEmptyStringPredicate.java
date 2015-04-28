/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.predicate;

import com.yattatech.util.StringUtil;

/**
 * Class that tests if the value is non null and non empty
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class NonEmptyStringPredicate implements StringPredicate {

    @Override
    public boolean accept(Object value) {
        return (value == null) ? false : !StringUtil.isEmpty(value.toString());
    }    
}
