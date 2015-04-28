/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Domain that represents a person
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public class Person extends Domain {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;    
    @SerializedName("work")
    private String work;
    @SerializedName("day")
    private String day;
    @SerializedName("month")
    private String month;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("cellphone")
    private String cellphone;
    
    public Person() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }    
    
    public String getWork() {
        return work;
    }
    
    public void setWork(String work) {
        this.work = work;
    }    
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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

    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }    

    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
