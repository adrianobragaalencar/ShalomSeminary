/*
 * Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech;

import com.yattatech.bg.SeminarySaver;
import com.yattatech.facade.SystemFacade;
import com.yattatech.form.AboutForm;
import com.yattatech.form.CoupleCRUDForm;
import com.yattatech.form.SeminaryForm;
import com.yattatech.util.SeminaryUtil;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Main entry point for Shalom Seminário de vida no
 * espírito santo.
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 * 
 */
public final class Shalom {

    private static final Logger LOGGER       = Logger.getLogger("Shalom");
    private static final SystemFacade FACADE = SystemFacade.getInstance();
    private static final SeminarySaver SAVER = new SeminarySaver();
    private static CoupleCRUDForm displayPanel;

    public static void main(String... args) {        
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOGGER.log(Level.SEVERE, String.format("uncaughtException %s", e.getMessage()));
            }        
        });        
        SeminaryUtil.createSeminaryFolder();                
        java.awt.EventQueue.invokeLater(new Runnable() { 
                        
            @Override 
            public void run() { 
                setSystemLookAndFeel(); 
                displayPanel = new CoupleCRUDForm(); 
                JFrame displayFrame = new JFrame("SEMINÁRIO DE VIDA NO ESPÍRITO SANTO " + Constants.VERSION); 
                displayFrame.getContentPane().add(displayPanel); 
                displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                displayFrame.pack(); 
                displayFrame.setResizable(false);
                displayFrame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {                        
                        LOGGER.info("closing main frame");
                        FACADE.saveSeminary();
                    }                    

                    @Override
                    public void windowClosed(WindowEvent e) {
                        // to be sure that we're really killing the jvm
                        // cause sometimes we're getting some pending ones
                        // it's causing a big memory footprint
                        System.exit(0);
                    }
                });
                addMenuBar(displayFrame);
                displayFrame.setVisible(true); 
                if (FACADE.hasSeminaries()) {
                    openSeminaryFiles(displayFrame);
                } else {                    
                    createSeminaryFile(displayFrame);        
                }
                SAVER.start();
            } 
        });         
    }
    
    private static void openSeminaryFiles(JFrame displayFrame) {
        LOGGER.info("openSeminaryFiles");
        JFileChooser chooser               = new JFileChooser(FACADE.getSeminaryFilesPath());
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Arquivos Shalom", Constants.SHALOM_EXT);            
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(fileFilter);
        final int value = chooser.showOpenDialog(displayFrame);
        if (value == JFileChooser.APPROVE_OPTION) {           
            if (FACADE.loadSeminary(chooser.getSelectedFile().getAbsolutePath())) {
                final int size = FACADE.getSeminary().coupleSize();
                // Don't change the order below, if you do it we gonna
                // mess UI up
                displayPanel.setCurrentIndex(size);
                displayPanel.setLabelCoupleSize(size);                   
            } else {
                JOptionPane.showMessageDialog(displayFrame, "Não foi possível carregar arquivo de seminário.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(2);   
            }
        } else  {
            createSeminaryFile(displayFrame);
        }
    }
    
    private static void createSeminaryFile(JFrame displayFrame) {
        LOGGER.info("createSeminaryFile");
        SeminaryForm seminaryForm = new SeminaryForm(displayFrame, true);
        seminaryForm.setTitle("Novo Seminário");
        seminaryForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        seminaryForm.pack(); 
        seminaryForm.setLocationRelativeTo(displayFrame);
        seminaryForm.setVisible(true);                
    }
    
    private static void addMenuBar(final JFrame frame) {        
        LOGGER.info("addMenuBar");
        JMenuBar menuBar     = new JMenuBar();
        JMenu menuSem        = new JMenu("Seminário");
        JMenu menuQuad       = new JMenu("Quadrante");        
        JMenu menuHelp       = new JMenu("Ajuda");        
        JMenuItem menuExit   = new JMenuItem("Sair");
        JMenuItem menuCreate = new JMenuItem("Gerar");
        JMenuItem menuAbout  = new JMenuItem("Sobre");
        
        menuAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                                
                AboutForm aboutForm = new AboutForm(frame, true);
                aboutForm.setTitle("Sobre");
                aboutForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                aboutForm.pack(); 
                aboutForm.setLocationRelativeTo(frame);
                aboutForm.setVisible(true);
            }            
        });
        
        menuCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FACADE.getSeminary().hasCouples()) {
                    final String result = JOptionPane.showInputDialog(frame, 
                                                                      "Forneça o subtítulo para o quadrante.", 
                                                                      "", 
                                                                      JOptionPane.QUESTION_MESSAGE);
                    if (result != null) {                                                            
                        generateReport(frame, result);
                    } else {
                        LOGGER.info("User has canceled report generation");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, 
                                                  "Não existe casais cadastrados.", 
                                                  "Aviso", 
                                                  JOptionPane.INFORMATION_MESSAGE);                       
                }
            }                        
        });
        
        menuExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
                final int result = JOptionPane.showConfirmDialog(frame, 
                                                                 "Deseja realmente sair?", 
                                                                 "Alerta", 
                                                                 JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {                    
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    frame.dispose();
                }
            }
        });               
        
        menuBar.add(menuSem);
        menuBar.add(menuQuad);        
        menuBar.add(menuHelp);

        menuSem.add(menuExit);
        menuQuad.add(menuCreate);   
        menuHelp.add(menuAbout);
                
        frame.setJMenuBar(menuBar);
    }
    
    private static void generateReport(final JFrame frame, final String title) {        
        
        final Cursor cursor         = frame.getCursor();
        final JDialog dialog        = new JDialog(frame);
        final JProgressBar progress = new JProgressBar();
        dialog.add(BorderLayout.CENTER, progress);                
        dialog.setSize(200, 40);
        dialog.setModal(true);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(frame);      
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);        
        progress.setIndeterminate(true);
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        final SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            
            @Override
            protected Boolean doInBackground() throws InterruptedException {
                return FACADE.generateReport(title);
            }
            
            @Override
            protected void done() {
                frame.setCursor(cursor);
                dialog.dispose();
            }
        };
        worker.execute();
        dialog.setVisible(true);
        try {
            if (worker.get()) {
                JOptionPane.showMessageDialog(frame, "Quadrante gerado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Erro ao gerar o quadrante.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }                
    }
    
    private static void setSystemLookAndFeel() { 
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (ClassNotFoundException cnfe){
            LOGGER.log(Level.WARNING, cnfe.getMessage());
        } catch (InstantiationException ie) {
            LOGGER.log(Level.WARNING, ie.getMessage());
        } catch (IllegalAccessException iae){
            LOGGER.log(Level.WARNING,iae.getMessage());
        } catch (UnsupportedLookAndFeelException ulfe) {
            LOGGER.log(Level.WARNING, ulfe.getMessage());
        } 
    } 
}
