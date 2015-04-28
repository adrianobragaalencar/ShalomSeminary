/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom JPanel responsible only for painting an 
 * image inside of itself
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 */
public final class ImagePanel extends JPanel {
    
    private static final Logger LOGGER = Logger.getLogger(ImagePanel.class.getSimpleName());
    private Image defaultImage;
    private Image image;
    private boolean scaledImage;
    private String imagePath;
    private final ExecutorService executor;
    
    public ImagePanel() {
        executor = Executors.newCachedThreadPool();
        Runtime.getRuntime().addShutdownHook(new Thread() {            
            @Override
            public void run() {
                executor.shutdown();
            }
        });
        try {
            defaultImage = ImageIO.read(getClass().getResourceAsStream("/shalom.jpg"));
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.getMessage());
        }
    }
    
    public void loadImage(final String path) {
        if (path == null) {
            return;
        }
        loadImage(new File(path));
    }
      
    public void loadImage(final File path) {
        if ((path == null) || (!path.exists())) {
            return;
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                image = loadImageFromPath(path);
                image = resizeImage(image);                
                repaint();
            }           
        });
    }
    
    public void loadDefaultImage() {
        image     = null;
        imagePath = null;
        repaint();
    }
    
    private BufferedImage loadImageFromPath(final File file) {
        try {
            imagePath = file.getPath();
            LOGGER.info(String.format("Loading image from %s", imagePath));
            return ImageIO.read(file);            
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.getMessage());
            return null;
        }        
    }
    
    private Image resizeImage(Image original) {
        if (original == null) {
            return null;
        }
        return original.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);            
    }
    
    /**
     * Returns the current image loaded path or null
     * if there's no one
     * 
     * @return String
     * 
     */
    public String getImagePath() {
        return imagePath;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            drawImage(g, image);
        } else if (defaultImage != null) {
            if (!scaledImage) {
                defaultImage = resizeImage(defaultImage);            
                scaledImage  = true;
            }
            drawImage(g, defaultImage);
        }
    }
    
    private void drawImage(Graphics g, Image img) {
        if (img != null) {
            g.drawImage(img, 0, 0, null);
        }        
    }
}
