/*
 * Copyright iGEM Slovenia 2016, National Institute of Chemistry Slovenia 
 * We would like to distribute the best version available to all the users of the program, without exception.
 * Therefore, all types of users are kindly requested to contact mojca.bencina@ki.si to establish a network of users, 
 * so each one can profit out of collaboration and distribution of the currently best version.
 * If you introduce any change the original code (if you modify the original code or the other version officially available) 
 * and make it more useful by it, please notify the original author on ziga.pusnik@gmail.com 
 * so all the users of the programs can be notified of the improvement. Your contributions will be highly appreciated.
 * Having used CaPTURE (or any derived version of it) or its source code for obtaining results, 
 * you become obliged to give credit to the original authors.
 *
 * This file is part of software CaPTURE and is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package scanPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import static scanPackage.TimeLineJForm.exportCSV;
import static scanPackage.TimeLineJForm.exportJPEG;

public class TimeLineWithWindowJForm extends javax.swing.JFrame {
    
    private class Marker {
        private int absolutePositionX; // the position in pixels relative to jPanel
        private int positionX; // the position in time points
        private int maxSize;
        private boolean isDraged;

        public Marker(int positionX, int maxSize) {
            this.maxSize = maxSize;
            this.positionX = positionX;
            this.absolutePositionX = getAbsolutePositionX();
            this.isDraged = false;
        }

        /**
         * Getter for absolute position of window in pixels. The absolute position is calculated from positionX.
         * @return absolutePositionX
         */
        public int getAbsolutePositionX() {
            int width = jPanel.getWidth();
            int margin = (int)(width*0.08);
            width = width - margin;
            return (int)((double)positionX*width/(maxSize - 1)) + margin;
        }
        
        /**
         * From absolutePositionX in pixels recalculates position in time points.
         * @param absolutePositionX 
         */
        public void recalculatePositions(int absolutePositionX) {
            // set index and set relative position
            int width = jPanel.getWidth();
            int margin = (int)(width*0.08);
            width = width - margin;            
            
            this.positionX = (int)(maxSize*((double)(absolutePositionX - margin)/width));
            if(positionX < 0) {
                positionX = 0;
            }
            if(this.positionX >= maxSize) {
                positionX = maxSize - 1;
            }        
        }

        /**
         * Sets the absolutePositionX.
         * @param absolutePositionX 
         */
        public void setAbsolutePositionX(int absolutePositionX) {
            this.absolutePositionX = getAbsolutePositionX();
        }

        /**
         * Return true if marker is being dragged.
         * @return true if marker is being dragged, else returns false
         */
        public boolean isIsDraged() {
            return isDraged;
        }

        /**
         * Sets the isDraged property to given boolean value.
         * @param isDraged 
         */
        public void setIsDraged(boolean isDraged) {
            this.isDraged = isDraged;
        }
        
        /**
         * Getter for positionX.
         * @return positionX
         */
        public int getPositionX() {
            return this.positionX;
        }
        
        /**
         * Sets position x.
         * @param positionX 
         */
        public void setPositionX(int positionX) {
            this.positionX = positionX;
            this.absolutePositionX = getAbsolutePositionX();
        }
    }
    
    private class Window {
        private int maxSize;
        private int windowSize;
        
        private Marker begin;
        private Marker end;
        


        public Window(int maxSize) {
            this.maxSize = maxSize;
            this.windowSize = maxSize;
            
            begin = new Marker(0, maxSize);
            end = new Marker(maxSize - 1, maxSize);           
        }

        /**
         * Getter the beginning marker of the window.
         * @return Marker begin
         */
        public Marker getBegin() {
            return begin;
        }
        /**
         * Getter for ending marker of the window.
         * @return Marker end
         */
        public Marker getEnd() {
            return end;
        }     
    }
 
    private ArrayList<ImageData> imageData;
    private ArrayList<Double> timePnts;
    private Region avgRegion;
    private Window window;
    private int numOfValidRegions;
    private float percentage;
    private CaPTURE parent;
    private boolean roi;

    /**
     * Getter for ArrayList of ImageData imageData.
     * @return the ArrayList of ImageDataObjects, that holds data for all regions in all time points
     */
    public ArrayList<ImageData> getImageData() {
        return imageData;
    }

    /**
     * Setter for ImageData imageData object
     * @param imageData 
     */
    public void setImageData(ArrayList<ImageData> imageData) {
        this.imageData = imageData;
        window = new Window(imageData.size());
    }
    
    /**
     * Setter for time points.
     * @param tmpnts 
     */
    public void setTimePnts(ArrayList<Double> tmpnts) {
        timePnts = tmpnts;
    }
    
    /**
     * Getter for time points.
     * @return timePnts
     */
    public ArrayList<Double> getTimePnts() {
        return timePnts;
    }
    
    /**
     * Sets the number of valid regions.
     * @param numOfValidRegions 
     */
    public void setNumOfValidRegions(int numOfValidRegions) {
        this.numOfValidRegions = numOfValidRegions;
    }

    /**
     * Setter for percentage value.
     * @param percentage the percentage of total responses in chosen time window
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage > 100.0 ? 100.0f : percentage;
    }

    public void setParent(CaPTURE parent) {
        this.parent = parent;
    }  
    
    /**
     * Creates new form TimeLineWindowJForm
     */
    public TimeLineWithWindowJForm() {
        roi = false;
        initComponents();
    }
    
    /**
     * Gets BufferedImage icon
     * @return icon image
     */
    private Image getIcon() {
        try {
            return ImageIO.read(CaPTURE.class.getResourceAsStream("icon.png"));
        } catch (IOException ex) {
            return null;
        }
    }        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g1) {
                super.paintComponent(g1);
                Graphics2D g = (Graphics2D)g1;
                int width = this.getSize().width;
                int height = this.getSize().height;
                int marginLeft = (int)(width*0.04);
                int marginBottom = marginLeft;
                marginLeft = marginLeft*2;
                try{
                    Marker begin = window.getBegin();
                    Marker end = window.getEnd();

                    begin.setAbsolutePositionX(begin.getAbsolutePositionX());
                    end.setAbsolutePositionX(end.getAbsolutePositionX());
                    if(!roi) {

                        g.setColor(Color.lightGray);
                        g.fillRect(begin.absolutePositionX, 0, end.absolutePositionX - begin.absolutePositionX , height - marginBottom);

                        g.setColor(Color.BLACK);

                        // draw vertical line for first marker
                        g.drawLine(begin.getAbsolutePositionX(), 0, begin.getAbsolutePositionX(), height - marginBottom);
                        // draw vertical line for second marker
                        g.drawLine(end.getAbsolutePositionX(), 0, end.getAbsolutePositionX(), height - marginBottom);

                        // draw marker
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(begin.absolutePositionX  - (int)(marginBottom*0.2), height - marginBottom - (int)(marginBottom*0.2), (int)(marginBottom*0.4), (int)(marginBottom*0.4));
                        g.fillRect(end.absolutePositionX  - (int)(marginBottom*0.2), height - marginBottom - (int)(marginBottom*0.2), (int)(marginBottom*0.4), (int)(marginBottom*0.4));
                        g.setColor(Color.black);
                    }

                    AffineTransform orig = g.getTransform();
                    // draw string
                    g.rotate(-Math.PI/2);
                    g.setFont(new Font("Arial Narrow", Font.PLAIN, (int)(marginLeft*0.25)));
                    g.drawString("Intensity #1", -(int)((height - marginBottom)/3.0 - ((height - marginBottom)/8)),  (int)(marginBottom - marginBottom*0.2));
                    g.drawString("Intensity #2", -(int)(2*(height - marginBottom)/3.0 - ((height - marginBottom)/8)), (int)(marginBottom - marginBottom*0.2));
                    g.drawString("Intensity ratio", -(int)(3*(height - marginBottom)/3.0 - ((height - marginBottom)/8.5)), (int)(marginBottom - marginBottom*0.2));
                    g.setTransform(orig);
                    g.drawString("Time [s]", (int)(marginBottom*0.20), height - (int)(marginBottom/2.0) + (int)(marginBottom*0.25));

                    int regSize = imageData.size();
                    // draw axes in black
                    g.setColor(Color.black);
                    g.drawLine(marginLeft, (int)((height - marginBottom)/3.0), width, (int)((height - marginBottom)/3.0));
                    g.drawLine(marginLeft, (int)(2*(height - marginBottom)/3.0), width, (int)(2*(height - marginBottom)/3.0));
                    g.drawLine(marginLeft, (int)(3*(height - marginBottom)/3.0), width, (int)(3*(height - marginBottom)/3.0));

                    g.drawLine(marginLeft, height - marginBottom, marginLeft, 0);

                    //on first 50 pixels draw scale
                    double dx;
                    if(!roi) {
                        dx = ((double)width - marginLeft)/(regSize - 1);
                    }
                    else {
                        dx = ((double)width - marginLeft)/(end.getPositionX() - begin.getPositionX());
                    }
                    double startX = marginLeft;
                    // draw units
                    g.setColor(Color.black);
                    g.setFont(new Font("Arial Narrow", Font.PLAIN, (int)(marginBottom*0.5)));
                    if(roi) {
                        for(int k = begin.getPositionX() + 1; k <= end.getPositionX(); k++) {
                            // draw units
                            if((k - 1)%10 == 0) {
                                g.drawString(String.format(Locale.US, "%.0f", timePnts.get(k)), (int)startX - (int)(marginBottom*0.20), height - (int)(marginBottom/2.0) + (int)(marginBottom*0.25));
                            }
                            // draw horizontal line
                            g.drawLine((int)startX, height - marginBottom + (int)(marginBottom*0.1), (int)startX, height - marginBottom - (int)(marginBottom*0.1));
                            startX  = startX + dx;
                        }
                    }
                    else {
                        for(int k = 1; k < regSize; k++) {
                            // draw units
                            if((k - 1)%10 == 0) {
                                g.drawString(String.format(Locale.US, "%.0f", timePnts.get(k)), (int)startX - (int)(marginBottom*0.20), height - (int)(marginBottom/2.0) + (int)(marginBottom*0.25));
                            }
                            // draw horizontal line
                            g.drawLine((int)startX, height - marginBottom + (int)(marginBottom*0.1), (int)startX, height - marginBottom - (int)(marginBottom*0.1));
                            startX  = startX + dx;
                        }
                    }

                    double maxG = 0;
                    double maxY = 0;
                    double maxRatio = 0;
                    // find max
                    if(avgRegion != null && avgRegion.getTimepntsG() != null) {

                        maxG = Collections.max(avgRegion.getTimepntsG());

                        maxY = Collections.max(avgRegion.getTimepntsY());

                        maxRatio = Collections.max(avgRegion.getTimepntsRa());

                        double dyG = ((height - marginBottom)/3.0)/maxG;
                        double dyY = ((height - marginBottom)/3.0)/maxY;
                        double dyRa = ((height - marginBottom)/3.0)/maxRatio;

                        // dispaly units
                        double yVal = 0;
                        double yPos = (height - marginBottom)/3.0;
                        for(int i = 0; i < 10; i++) {
                            if(i%2 == 0) {
                                yVal = (i/10.0)*maxG;
                                g.drawString(Integer.toString((int)yVal), (int)(3*marginLeft/4.0) - (int)(marginBottom*0.25), (int)yPos + (int)(marginBottom*0.2));
                            }
                            g.drawLine(marginLeft - (int)(marginBottom*0.1), (int)yPos, marginLeft + (int)(marginBottom*0.1), (int)yPos);
                            yPos = yPos - ((height - marginBottom)/3.0)*0.1;
                        }

                        yVal = 0;
                        yPos = 2*(height - marginBottom)/3.0;
                        for(int i = 0; i < 10; i++) {
                            if(i%2 == 0) {
                                yVal = (i/10.0)*maxY;
                                g.drawString(Integer.toString((int)yVal), (int)(3*marginLeft/4.0) - (int)(marginBottom*0.25), (int)yPos + (int)(marginBottom*0.2));
                            }
                            g.drawLine(marginLeft - (int)(marginBottom*0.1), (int)yPos, marginLeft + (int)(marginBottom*0.1), (int)yPos);
                            yPos = yPos - ((height - marginBottom)/3.0)*0.1;
                        }
                        yVal = 0;
                        yPos = 3*(height - marginBottom)/3.0;
                        for(int i = 0; i < 10; i++) {
                            if(i%2 == 0) {
                                yVal = (i/10.0)*maxRatio;
                                g.drawString(String.format(Locale.US, "%.1f", yVal), (int)(3*marginLeft/4.0) - (int)(marginBottom*0.25), (int)yPos + (int)(marginBottom*0.2));
                            }
                            g.drawLine(marginLeft - (int)(marginBottom*0.1), (int)yPos, marginLeft + (int)(marginBottom*0.1), (int)yPos);
                            yPos = yPos - ((height - marginBottom)/3.0)*0.1;
                        }
                        if(avgRegion.getTimepntsG() != null) {
                            scanPackage.TimeLineJForm.paintCurve(avgRegion, marginLeft, marginBottom, height, width, dyG, dyY, dyRa, regSize, dx, g, false, true, roi ? begin.getPositionX() + 1 : 1, roi ? end.getPositionX() + 1 : regSize);
                        }
                    }
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(width - 2*marginLeft, 0, 2*marginLeft, (int)(0.5*marginLeft));

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial Narrow", Font.BOLD, (int)(marginLeft*0.2)));
                    g.drawString("Response percentage: ", (int)(width - 2*marginLeft + marginLeft*0.15), (int)(marginLeft*0.2));
                    g.drawString(String.format(Locale.US, "%.2f", percentage) + " %",  (int)(width - 2*marginLeft + marginLeft*0.8), (int)(marginLeft*0.4));

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            };
        };
        jMenuBar = new javax.swing.JMenuBar(){@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(51, 51, 51));
                g2d.fillRect(0, 0, getWidth(), getHeight());

            };
        };
        jMenuFile = new javax.swing.JMenu();
        jMenuItemProcess = new javax.swing.JMenuItem();
        jMenuExportAs = new javax.swing.JMenu();
        jMenuItemCSV = new javax.swing.JMenuItem();
        jMenuItemJPEG = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(getIcon());

        jPanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelMouseMoved(evt);
            }
        });
        jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanelMouseReleased(evt);
            }
        });
        jPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );

        jMenuFile.setBackground(new java.awt.Color(51, 51, 51));
        jMenuFile.setForeground(new java.awt.Color(255, 255, 255));
        jMenuFile.setText("File");
        jMenuFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuFile.setOpaque(true);

        jMenuItemProcess.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemProcess.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemProcess.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemProcess.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemProcess.setText("Process");
        jMenuItemProcess.setOpaque(true);
        jMenuItemProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProcessActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemProcess);

        jMenuExportAs.setBackground(new java.awt.Color(51, 51, 51));
        jMenuExportAs.setForeground(new java.awt.Color(255, 255, 255));
        jMenuExportAs.setText("Export as");
        jMenuExportAs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuExportAs.setOpaque(true);

        jMenuItemCSV.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemCSV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemCSV.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemCSV.setText("CSV");
        jMenuItemCSV.setOpaque(true);
        jMenuItemCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCSVActionPerformed(evt);
            }
        });
        jMenuExportAs.add(jMenuItemCSV);

        jMenuItemJPEG.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemJPEG.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemJPEG.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemJPEG.setText("JPEG");
        jMenuItemJPEG.setOpaque(true);
        jMenuItemJPEG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemJPEGActionPerformed(evt);
            }
        });
        jMenuExportAs.add(jMenuItemJPEG);

        jMenuFile.add(jMenuExportAs);

        jMenu1.setBackground(new java.awt.Color(51, 51, 51));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("Export ROI as");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenu1.setOpaque(true);

        jMenuItem1.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setText("JPEG");
        jMenuItem1.setOpaque(true);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuFile.add(jMenu1);

        jMenuBar.add(jMenuFile);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is called when jPanel is resized.
     * @param evt 
     */
    private void jPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelComponentResized
        // panel is resized
        if(window != null) {
            window.getBegin().setAbsolutePositionX(window.getBegin().getAbsolutePositionX());
            window.getEnd().setAbsolutePositionX(window.getEnd().getAbsolutePositionX());
        }     
    }//GEN-LAST:event_jPanelComponentResized

    /**
     * This method is called when mouse is dragged over jPanel.
     * @param evt 
     */
    private void jPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMouseDragged
        // mouse is draged
        Marker begin = window.getBegin();
        Marker end = window.getEnd();
        
        if(begin.isIsDraged()) {
            begin.recalculatePositions(evt.getX());
            
            if(begin.getPositionX() >= end.getPositionX()) {
                begin.setPositionX(end.getPositionX() - 1);
            }
            
            jPanel.repaint();
        }
        else if(end.isIsDraged()) {
            end.recalculatePositions(evt.getX()); 
            
            if(end.getPositionX() <= begin.getPositionX()) {
                end.setPositionX(begin.getPositionX() + 1);
            }
            
            jPanel.repaint();
        }         
    }//GEN-LAST:event_jPanelMouseDragged
    
    /**
     * Returns true if mouse cursor is hovering over Marker.
     * @param marker the marker being processed
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     * @return true if mouse cursor is hovering over Marker, else returns false
     */
    private boolean isOverMarker(Marker marker, int mouseX, int mouseY) {
        double marginBottom = jPanel.getWidth()*0.04;
        if(mouseY <= jPanel.getHeight() - marginBottom + marginBottom*0.2  && mouseY >= jPanel.getHeight() - marginBottom - marginBottom*0.2 ) {
            int xBegin = marker.getAbsolutePositionX();
            int xEnd = marker.getAbsolutePositionX(); 
            int width = jPanel.getWidth();
            int margin = (int)(width*0.02);
            margin = (int)(margin*0.4);
            return mouseX >= xBegin - margin && mouseX <= xBegin + margin;
        }
        return false;
    }
    
    /**
     * This event is called when mouse is moved over jPanel.
     * @param evt 
     */
    private void jPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMouseMoved
        // if hovering over marker set cursor hand
        Marker begin = window.getBegin();
        Marker end = window.getEnd();

        if(isOverMarker(begin, evt.getX(), evt.getY())) {
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));                 
        }
        else if(isOverMarker(end, evt.getX(), evt.getY())) {
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));                 
        }
        else{
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));               
        }   
    }//GEN-LAST:event_jPanelMouseMoved

    private void jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMouseClicked

    }//GEN-LAST:event_jPanelMouseClicked

    /**
     * This event is called when mouse is pressed on jPanel.
     * @param evt 
     */
    private void jPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMousePressed
        // mouse is pressed
        Marker begin = window.getBegin();
        Marker end = window.getEnd();

        if(isOverMarker(begin, evt.getX(), evt.getY())) {
            begin.setIsDraged(true);
        }
        else if(isOverMarker(end, evt.getX(), evt.getY())) {
            end.setIsDraged(true);
        }
    }//GEN-LAST:event_jPanelMousePressed

    /**
     * This event is called when mouse is released on jPanel.
     * @param evt 
     */    
    private void jPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMouseReleased
        // mouse is released
        Marker begin = window.getBegin();
        Marker end = window.getEnd();

        begin.setIsDraged(false);
        end.setIsDraged(false);
    }//GEN-LAST:event_jPanelMouseReleased

    /**
     * Recalculates the average response of all regions that responded in given Window.
     * @param evt 
     */
    private void jMenuItemProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProcessActionPerformed
        // get current window size
        // reprocess window
        avgRegion = new Region();
        ArrayList<Double> pntsG = new ArrayList<>(Collections.nCopies(imageData.size(), 0.0));
        ArrayList<Double> pntsY = new ArrayList<>(Collections.nCopies(imageData.size(), 0.0));
        ArrayList<Double> pntsRa = new ArrayList<>(Collections.nCopies(imageData.size(), 0.0));
        ArrayList<Double> pntsGRegion; 
        ArrayList<Double> pntsYRegion; 
        ArrayList<Double> pntsRaRegion;
        Marker begin = window.getBegin();
        Marker end = window.getEnd();
        HashSet<List<Integer>> set = new HashSet<>();
        int arraySize = pntsG.size();
        for(int i = begin.getPositionX(); i <= end.getPositionX(); i++) {
            ImageData data = imageData.get(i);
            for (Region region : data.getRegions()) {
                int startLength = set.size();
                set.add(Arrays.asList(region.getX(), region.getY()));
                if(set.size() > startLength) {
                    pntsGRegion = region.getTimepntsG();
                    pntsYRegion = region.getTimepntsY();
                    pntsRaRegion = region.getTimepntsRa();
                    for(int j = 0; j < arraySize; j++) {
                        pntsG.set(j, pntsG.get(j) + pntsGRegion.get(j));
                        pntsY.set(j, pntsY.get(j) + pntsYRegion.get(j));
                        pntsRa.set(j, pntsRa.get(j) + pntsRaRegion.get(j));
                    }
                }
            }
        }
        int size = set.size();
        if(size == 0) {
            avgRegion.setTimepntsG(null);
            avgRegion.setTimepntsY(null);
            avgRegion.setTimepntsRa(null);              
        }
        else {
            // normalite regions with ratio
            for(int i = 0; i < arraySize; i++) {
                pntsG.set(i, pntsG.get(i)/size);
                pntsY.set(i, pntsY.get(i)/size);
                pntsRa.set(i, pntsRa.get(i)/size);        
            }
            avgRegion.setTimepntsG(pntsG);
            avgRegion.setTimepntsY(pntsY);
            avgRegion.setTimepntsRa(pntsRa);        
        }
        this.setPercentage((float)(100*(set.size()/(double)numOfValidRegions)));
        
        jPanel.repaint();
    }//GEN-LAST:event_jMenuItemProcessActionPerformed

    /**
     * Exports graph in JPEG format.
     * @param evt 
     */
    private void jMenuItemJPEGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemJPEGActionPerformed
        // export as JPEG
        exportJPEG(parent.fc, jPanel, parent);
    }//GEN-LAST:event_jMenuItemJPEGActionPerformed

    /**
     * Exports graph in CSV format.
     * @param evt 
     */
    private void jMenuItemCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCSVActionPerformed
        // export region as CSV
        ImageData data = new ImageData();
        ArrayList<Region> regions = new ArrayList<>();
        regions.add(avgRegion);
        data.setRegions(regions);
        exportCSV(parent.fc, data, parent);
    }//GEN-LAST:event_jMenuItemCSVActionPerformed

    /**
     * Exports graph region of interest in JPEG format.
     * @param evt 
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // export ROI as JPEG
        roi = true;
        jPanel.repaint();
        //repaint jPanel
        exportJPEG(parent.fc, jPanel, parent);
        roi = false;
        jPanel.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimeLineWithWindowJForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimeLineWithWindowJForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimeLineWithWindowJForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimeLineWithWindowJForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimeLineWithWindowJForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuExportAs;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemCSV;
    private javax.swing.JMenuItem jMenuItemJPEG;
    private javax.swing.JMenuItem jMenuItemProcess;
    private javax.swing.JPanel jPanel;
    // End of variables declaration//GEN-END:variables
}
