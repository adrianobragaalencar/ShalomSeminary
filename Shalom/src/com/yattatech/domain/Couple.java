/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Domain that put together common couple information
 *
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 */
public final class Couple extends Domain {
    
    @SerializedName("code")
    private int code;
    @SerializedName("husband")
    private Husband husband;
    @SerializedName("wife")
    private Wife wife;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("compl")
    private String compl;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("day")
    private String day;
    @SerializedName("month")
    private String month;
    @SerializedName("state")
    private FedUnit state;
    @SerializedName("city")
    private City city;
    @SerializedName("neighbor")
    private Neighbor neighbor;
    @SerializedName("photoPath")
    private String photoPath;

    public Couple() {
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompl() {
        return compl;
    }

    public void setCompl(String compl) {
        this.compl = compl;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public FedUnit getState() {
        return state;
    }

    public void setState(FedUnit state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Neighbor getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Neighbor neighbor) {
        this.neighbor = neighbor;
    }        
    

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }            
}
