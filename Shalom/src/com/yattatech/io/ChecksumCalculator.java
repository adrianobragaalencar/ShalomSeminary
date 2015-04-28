/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.io;

import java.util.zip.CRC32;

/**
 * Class which knows how to calculate a checksum 
 * from a string value 
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public class ChecksumCalculator {
    
    private static final CRC32 CRC32 = new CRC32();
    
    private ChecksumCalculator() {
        throw  new AssertionError();
    }
    
    public static long calculateChecksum(String value) {
        for (int i = 0, s = value.length(); i < s; ++i) {
            CRC32.update(value.charAt(i));
        }            
        final long checksum = CRC32.getValue();
        CRC32.reset();
        return checksum;
    }
}
