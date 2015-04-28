/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.util;

import java.util.Comparator;
import com.yattatech.domain.Couple;

/**
 * Comparator that orders a list of couple by husband name
 * in ascending order
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class CoupleNameComparator implements Comparator<Couple> {

    @Override
    public int compare(Couple lh, Couple rh) {
        if (lh == null) {
            return rh == null ? 0 : 1;
        } else if (rh == null) {
            return -1;
        }
        return lh.getName().compareTo(rh.getName());
    }    
}
