/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.io;

import com.google.gson.Gson;
import com.yattatech.domain.Seminary;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * A writer class which knows how to dump a seminary data
 * to a file in disk
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class ShalomFileWriter {
    
    private static final Logger LOGGER = Logger.getLogger("ShalomFileWriter");
    
    public ShalomFileWriter() {
    }
    
    public boolean write(Seminary seminary) {                        
        final String path    = seminary.getFilePath();
        final String json    = new Gson().toJson(seminary);        
        boolean sucess       = true;
        DataOutputStream out = null;                        
        try {
            out              = new DataOutputStream(new FileOutputStream(path));
            out.writeLong(ChecksumCalculator.calculateChecksum(json));
            out.writeUTF("\n");
            out.writeUTF(json);
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.getMessage());
            sucess = false;
        } finally {           
            IOUtils.closeQuietly(out);
            return sucess;
        }        
    }
}
