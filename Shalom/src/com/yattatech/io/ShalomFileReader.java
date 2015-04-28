/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.io;

import com.google.gson.Gson;
import com.yattatech.domain.Seminary;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * A reader class which knows how to take a seminary data
 * from a file in disk
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class ShalomFileReader {

    private static final Logger LOGGER = Logger.getLogger("ShalomFileReader");
    
    public ShalomFileReader() {
    }
    
    public Seminary read(String path) {
                        
        Seminary seminary     = null;
        DataInputStream input = null;                        
        try {
            input          = new DataInputStream(new FileInputStream(path));            
            long checksum  = input.readLong();
                             input.readUTF();    // skip /n character
            String json    = input.readUTF();            
            long checksum2 = ChecksumCalculator.calculateChecksum(json);
            if (checksum == checksum2) {
                seminary   = new Gson().fromJson(json, Seminary.class);
            }
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.getMessage());           
        } finally {           
            IOUtils.closeQuietly(input);
            return seminary;
        }        
    }    
}
