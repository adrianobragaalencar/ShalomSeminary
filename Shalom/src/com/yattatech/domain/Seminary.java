/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

import com.google.gson.annotations.SerializedName;
import com.yattatech.util.CoupleNameComparator;
import com.yattatech.util.HusbandNameComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 * Class that represents an instance of Seminário do Espírito Santo
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class Seminary extends Domain {
    
    private static final HusbandNameComparator COMPARATOR_0 = new HusbandNameComparator();
    private static final CoupleNameComparator COMPARATOR_1  = new CoupleNameComparator();
    @SerializedName("couples")
    private List<Couple> couples;    
    @SerializedName("file")
    private String filePath;
    
    public Seminary() {
    }
    
    public void addCouple(Couple couple) {
        if (couples == null) {
            couples = new ArrayList<Couple>();
        }
        couples.add(couple);
    }
    
    public int coupleSize() {
        return couples == null ? 0 : couples.size();
    }
    
    public List<Couple> getCouples() {
        if (couples == null) {
            couples = new ArrayList<Couple>();
        }
        return couples;
    }
    
    public List<Couple> getOrderedCouplesByHusbandName() {
        if (couples == null) {
            couples = new ArrayList<Couple>();
        }
        final ArrayList<Couple> orderCouples = new ArrayList<Couple>(couples);
        Collections.sort(orderCouples, COMPARATOR_0);
        return orderCouples;
    }
    
    public List<Couple> getOrderedCouplesByName() {
        if (couples == null) {
            couples = new ArrayList<Couple>();
        }
        final ArrayList<Couple> orderCouples = new ArrayList<Couple>(couples);
        Collections.sort(orderCouples, COMPARATOR_1);
        return orderCouples;
    }
    
    public boolean hasCouples() {
        return (couples == null) ? false : !couples.isEmpty();
    }
    
    public Couple getCouple(int index) {
        if (couples == null) {
            return null;
        }
        return index < couples.size() ? couples.get(index) : null;
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getPdfReportFilename() {        
        return FilenameUtils.getBaseName(filePath) + ".pdf";
    }
}
