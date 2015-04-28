/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.util;

import com.yattatech.domain.Couple;
import com.yattatech.domain.Husband;
import java.util.Comparator;

/**
 * Comparator that orders a list of couple by husband name
 * in ascending order
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class HusbandNameComparator implements Comparator<Couple> {

    @Override
    public int compare(Couple lh, Couple rh) {
        if (lh == null) {
            return rh == null ? 0 : 1;
        } else if (rh == null) {
            return -1;
        }
        final Husband leftHusb  = lh.getHusband();
        final Husband rightHusb = rh.getHusband();
        if (leftHusb == null) {
            return rightHusb == null ? 0 : 1;
        } else if (rightHusb == null) {
            return -1;
        }
        return leftHusb.getName().compareTo(rightHusb.getName());
    }    
}
