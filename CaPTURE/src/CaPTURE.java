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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import loci.formats.FormatException;
import org.xml.sax.SAXException;

class Region{
    private int x;
    private int y;
    private int winSize;
    private ArrayList<Double> timepntsG;
    private ArrayList<Double> timepntsY;
    private ArrayList<Double> timepntsRa;
    private boolean red;

    /**
     * Getter for x coordinate.
     * @return x
     */
    public int getX() {
        return x;
    }

    public boolean isRed() {
        return this.red;
    }
    
    /**
     * Getter for Y coordinate.
     * @return  y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for winSize.
     * @return window size of Region
     */
    public int getWinSize() {
        return winSize;
    }

    /**
     * Getter for timepntsG.
     * @return ArrayList of intensities for 1st channel of all time points
     */
    public ArrayList<Double> getTimepntsG() {
        return timepntsG;
    }

    /**
     * Getter for timepntsY.
     * @return ArrayList of intensities for 2nd channel of all time points
     */
    public ArrayList<Double> getTimepntsY() {
        return timepntsY;
    }

    /**
     * Getter for timepntsRa.
     * @return ArrayList of intensity ratios of all time points
     */
    public ArrayList<Double> getTimepntsRa() {
        return timepntsRa;
    }

    /**
     * Setter for x coordinate.
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for y coordinate.
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter for winSize
     * @param winSize the window size of regions
     */
    public void setWinSize(int winSize) {
        this.winSize = winSize;
    }

    /**
     * Setter for timepntsG
     * @param timepntsG ArrayList of intensities for 1st channel of all time points
     */
    public void setTimepntsG(ArrayList<Double> timepntsG) {
        this.timepntsG = timepntsG;
    }

    /**
     * Setter for timepntsY
     * @param timepntsY ArrayList of intensities for 2nd channel of all time points
     */
    public void setTimepntsY(ArrayList<Double> timepntsY) {
        this.timepntsY = timepntsY;
    }

    /**
     * Setter for timepntsRa
     * @param timepntsRa ArrayList of intensity ratios of all time points
     */
    public void setTimepntsRa(ArrayList<Double> timepntsRa) {
        this.timepntsRa = timepntsRa;
    } 

    /**
     * Setter for red.
     * @param red 
     */
    public void setRed(boolean red) {
        this.red = red;
    }

}

class ImageData{
    private int winSize;
    private int frameId;
    private ArrayList<Region> regions;

    /**
     * Getter for winSize property.
     * @return winSize 
     */
    public int getWinSize() {
        return winSize;
    }

    /**
     * Getter for frameId property.
     * @return frameId
     */
    public int getFrameId() {
        return frameId;
    }

    /**
     * Getter for regions property.
     * @return regions
     */
    public ArrayList<Region> getRegions() {
        return regions;
    }

    /**
     * Setter for winSize property.
     * @param winSize the window size of region
     */
    public void setWinSize(int winSize) {
        this.winSize = winSize;
    }

    /**
     * Setter for frameId property.
     * @param frameId the id of frame
     */
    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    /**
     * Setter for regions property.
     * @param regions the ArrayList of Regions
     */
    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }    
}

class Point {
    int x;
    int y;

    /**
     * Creates new Point
     * @param x the x coordinate of point
     * @param y the y coordinate of point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for x.
     * @param x the x parameter of Point.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for y.
     * @param y the y parameter of Point
     */
    public void setY(int y) {
        this.y = y;
    }
}

public class CaPTURE extends javax.swing.JFrame {

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
     * Implements Comparator and overrides compare methods. This class is used for sorting ArrayList of ArrayLists of Doubles in descending order,
     * with respect to relative change in intensity ratio. 
     */
    private class MyComparatorInensityAmp implements Comparator<ArrayList<Double>> {
        /**
         * Compare method used for sorting in descending order.
         * @param o1 ArrayList of Doubles
         * @param o2 ArrayList of Doubles
         * @return result of Double.compare(o2.get(2), o1.get(2))
         */
        @Override
        public int compare(ArrayList<Double> o1, ArrayList<Double> o2) {
            return Double.compare(o2.get(2), o1.get(2));
        }
    }
    
    /**
     * Implements Comparator and overrides compare methods. This class is used for sorting ArrayList of ArrayLists of Doubles in ascending order,
     * with respect to relative change in intensity ratio.
     */
    private class MyComparatorInensityAtte implements Comparator<ArrayList<Double>> {
        /**
         * Compare method used for sorting in descending order.
         * @param o1 ArrayList of Doubles
         * @param o2 ArrayList of Doubles
         * @return result of Double.compare(o1.get(2), o2.get(2))
         */
        @Override
        public int compare(ArrayList<Double> o1, ArrayList<Double> o2) {
            return Double.compare(o1.get(2), o2.get(2));
        }
    }
    
    private String folderPath;
    private int index;
    private int maxIndex;
    private ArrayList<String> filenames1;
    private ArrayList<String> filenames2;  
    private Timer tim;
    private int windowSize;
    private double thresholdAmp;
    private double thresholdAtten;
    private double minIntensityHeatMap;
    private int stride;
    private int heatmapResolution;
    private int maxDisplay;
    private ArrayList<ArrayList<Double>> regionsAmp;
    private ArrayList<ArrayList<Double>> regionsAtte;
    private ArrayList<RawImage> imagesG;
    private ArrayList<RawImage> imagesY;
    private ArrayList<BufferedImage> heatMap;
    private ArrayList<ArrayList<ArrayList<Double>>> detectedResponses;
    private ArrayList<ImageData> imageData;
    private ArrayList<TimeLineJForm> currChilds;
    private Boolean prevMouseOnScreen;
    private int imageWidth;
    private ProcessImages singletonWorkerProcessImages;
    private ReadLifFile singletonWorkerReadLifFile;
    private LifReader lifReader;
    private AnalyseAllRegionData singletonWorkerAnalyseAllRegionData;
    private GIFexport singeltonWorkerGifExport;
    private BufferedImage display1stCh;
    private BufferedImage display2ndCh;
    private BufferedImage exportImage;
    private BufferedImage newImageG;
    private BufferedImage newImageY;
    private BufferedImage newImageHeat; 
    private int numOfValidRegions;
    private double heatMapSensitivity;
    private CaPTURE self;
    private ArrayList<Point> border;
    HashSet<List<Integer>> setOfRegions;
    private boolean validRegionOfInterest;
    private String filename;
    private String seriesName;
    private int exportRegionsGIF;
    private int exportRegionsCSV;
    private int exportControlsGIF;
    private int exportHeatMapGIF; 
    private int timelineExportCSV;
    private int timeLineExportJPEG;
    private ArrayList<Double> timePnts;
    private int frameDifference;
    
    double minIntensity;
    Boolean imagesInBuffer;
    
    JFileChooser fc;
    
    /**
     * Creates new CaPTURE with default parameters.
     */
    public CaPTURE() {
        initComponents();
        initVars(true);
        tim = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maxIndex > 0) {
                    index++;
                    currChilds = new ArrayList<>();
                    if(index >= maxIndex) {
                        index = maxIndex - 1;
                    }
                    displayImagesOnPane();
                }
            }
        });
        this.getContentPane().setBackground(new Color(102, 102, 102));
    }
    
    /**
     * Initializes parameters of CaPTURE.
     * @param initAll whether or not all parameters are initialized
     */
    public void initVars(boolean initAll) {
        index = 0;
        filenames1 = new ArrayList<>();
        filenames2 = new ArrayList<>();   
        regionsAmp = new ArrayList<>();
        regionsAtte = new ArrayList<>(); 
        imagesG = new ArrayList<>();
        imagesY = new ArrayList<>();
        heatMap = new ArrayList<>();
        detectedResponses = new ArrayList<>();
        imagesInBuffer = false;
        prevMouseOnScreen = false;
        //set progress bar to unvisible
        jProgressBar.setVisible(false);  
        jButtonCancel.setVisible(false);
        
        if (initAll) {
            jCheckBoxBlue.setSelected(true);
            jCheckBoxRed.setSelected(true);
            self = this;
            singletonWorkerProcessImages = null;
            singletonWorkerReadLifFile = null;
            singeltonWorkerGifExport = null;
            singletonWorkerAnalyseAllRegionData = null;
            windowSize = 16;
            stride = 16;
            thresholdAmp = 1.4;
            thresholdAtten = 0.65;
            maxDisplay = 0;
            minIntensity = 64;
            minIntensityHeatMap = 4;
            heatmapResolution = 8;
            imageWidth = 0;
            fc = new JFileChooser();
            folderPath = null;
            cmb1.setVisible(false);
            cmb2.setVisible(false);
            numOfValidRegions = 0;
            heatMapSensitivity = 0;
            validRegionOfInterest = false;
            frameDifference = 4;
        }
    }
    
    /**
     * Getter for currently opened file name.
     * @return filename
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * Getter for currently opened series name.
     * @return seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }
    
    /**
     * Getter for timelineExportCSV parameter. This parameter holds value for total number of exports.
     * @return timelineExportCSV
     */
    public int getTimeLineExportCSV() {
        return timelineExportCSV;
    }

    /**
     * Setter for timeLineExportCSV parameter.
     * @param val the number of CSV exports
     */
    public void setTimeLineExportCSV(int val) {
        timelineExportCSV = val;
    }   
    
    /**
     * Getter for timelineExportJPEG parameter. This parameter holds value for total number of exports.
     * @return timelineExportJPEG
     */
    public int getTimeLineExportJPEG() {
        return timeLineExportJPEG;
    }

    /**
     * Setter for timeLineExportJPEG parameter.
     * @param val the number of JPEG exports
     */
    public void setTimeLineExportJPEG(int val) {
        timeLineExportJPEG = val;
    }    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        group = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelAnalysis = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jButtonForward = new javax.swing.JButton();
        jButtonPlay = new javax.swing.JButton();
        jLabelImage1 = new javax.swing.JLabel();
        jLabelImage2 = new javax.swing.JLabel();
        jLabelIndex = new javax.swing.JLabel();
        jLabeltimestamp1 = new javax.swing.JLabel();
        jLabeltimestamp2 = new javax.swing.JLabel();
        jLabelHeatmap = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        jButtonShowAllRegions = new javax.swing.JButton();
        jButtonShowAverageRegions = new javax.swing.JButton();
        jSlider = new javax.swing.JSlider();
        jCheckBoxBlue = new javax.swing.JCheckBox();
        jCheckBoxRed = new javax.swing.JCheckBox();
        seriesNameLabel = new javax.swing.JLabel();
        jLabelXYcoordinates = new javax.swing.JLabel();
        jPanelSettings = new javax.swing.JPanel();
        jLabelWindowSize = new javax.swing.JLabel();
        jTextFieldWindowSize = new javax.swing.JTextField();
        jLabelStrideSize = new javax.swing.JLabel();
        jTextFieldStrideSize = new javax.swing.JTextField();
        jLabelThresholdAmp = new javax.swing.JLabel();
        jTextFieldThresholdAmp = new javax.swing.JTextField();
        jLabelMaxDisplay = new javax.swing.JLabel();
        jTextFieldMaxDisplay = new javax.swing.JTextField();
        jButtonConfirmSettings = new javax.swing.JButton();
        jLabelThresholdAtten = new javax.swing.JLabel();
        jTextFieldThresholdAtten = new javax.swing.JTextField();
        jLabelMinIntensity = new javax.swing.JLabel();
        jTextFieldMinIntensity = new javax.swing.JTextField();
        jLabelHeatmapResolution = new javax.swing.JLabel();
        jTextFieldHeatmapResolution = new javax.swing.JTextField();
        jLabelHeatMapSensitivity = new javax.swing.JLabel();
        jTextFieldHeatmapSensitivity = new javax.swing.JTextField();
        jLabelHelpWindowSize = new javax.swing.JLabel();
        jLabelHelpStrideSize = new javax.swing.JLabel();
        jLabelHelpThresholdAmplification = new javax.swing.JLabel();
        jLabelHelpThresholdAttenuation = new javax.swing.JLabel();
        jLabelHelpMaxDisplay = new javax.swing.JLabel();
        jLabelHelpMinIntensity = new javax.swing.JLabel();
        jLabelHelpheatmapResolution = new javax.swing.JLabel();
        jLabelHelpHeatmapSensitivity = new javax.swing.JLabel();
        jLabelFrameDifference = new javax.swing.JLabel();
        jTextFieldFrameDifference = new javax.swing.JTextField();
        jLabelHelpFrameDifference = new javax.swing.JLabel();
        jPanelSeries = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2ndChannel = new javax.swing.JPanel();
        jLabel2ndChannel = new javax.swing.JLabel();
        cmb2 = new javax.swing.JComboBox<>();
        jPanel1stChannel = new javax.swing.JPanel();
        jLabel1stChannel = new javax.swing.JLabel();
        cmb1 = new javax.swing.JComboBox<>();
        jPanelNames = new javax.swing.JPanel();
        jLabelExperimentData = new javax.swing.JLabel();
        jLabelFileName = new javax.swing.JLabel();
        jButtonSeriesConfirm = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jButtonCancel = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar(){@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(51, 51, 51));
                g2d.fillRect(0, 0, getWidth(), getHeight());

            };
        };
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemAnalysis = new javax.swing.JMenuItem();
        jMenuExportRegions = new javax.swing.JMenu();
        jMenuItemExportRegionsGIF = new javax.swing.JMenuItem();
        jMenuItemExportRegionsCSV = new javax.swing.JMenuItem();
        jMenuExportControls = new javax.swing.JMenu();
        jMenuItemExportControlsGIF = new javax.swing.JMenuItem();
        jMenuExportHeatmap = new javax.swing.JMenu();
        jMenuItemExportHeatmapGIF = new javax.swing.JMenuItem();
        jMenuRegionOfInterest = new javax.swing.JMenu();
        jMenuItemRegionOfInterestNew = new javax.swing.JMenuItem();
        jMenuItemRegionOfInterestRemove = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemSettings = new javax.swing.JMenuItem();
        jMenuItemSeries = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CaPTURE");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIcon());

        jTabbedPane.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane.setForeground(new java.awt.Color(51, 51, 51));
        jTabbedPane.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        jPanelAnalysis.setBackground(new java.awt.Color(204, 204, 204));
        jPanelAnalysis.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanelAnalysisComponentResized(evt);
            }
        });

        jButtonBack.setBackground(new java.awt.Color(51, 51, 51));
        jButtonBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setText("<");
        jButtonBack.setContentAreaFilled(false);
        jButtonBack.setOpaque(true);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonForward.setBackground(new java.awt.Color(51, 51, 51));
        jButtonForward.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonForward.setForeground(new java.awt.Color(255, 255, 255));
        jButtonForward.setText(">");
        jButtonForward.setContentAreaFilled(false);
        jButtonForward.setOpaque(true);
        jButtonForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonForwardActionPerformed(evt);
            }
        });

        jButtonPlay.setBackground(new java.awt.Color(51, 51, 51));
        jButtonPlay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonPlay.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPlay.setText(">>");
        jButtonPlay.setContentAreaFilled(false);
        jButtonPlay.setOpaque(true);
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });

        jLabelImage1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabelImage1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelImage1MouseMoved(evt);
            }
        });
        jLabelImage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelImage1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelImage1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelImage1MouseReleased(evt);
            }
        });

        jLabelIndex.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelIndex.setForeground(new java.awt.Color(51, 51, 51));

        jLabeltimestamp1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabeltimestamp1.setForeground(new java.awt.Color(51, 51, 51));

        jLabeltimestamp2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabeltimestamp2.setForeground(new java.awt.Color(51, 51, 51));

        jButtonReset.setBackground(new java.awt.Color(51, 51, 51));
        jButtonReset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonReset.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReset.setText("|>");
        jButtonReset.setContentAreaFilled(false);
        jButtonReset.setOpaque(true);
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonShowAllRegions.setBackground(new java.awt.Color(51, 51, 51));
        jButtonShowAllRegions.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonShowAllRegions.setForeground(new java.awt.Color(255, 255, 255));
        jButtonShowAllRegions.setText("Show all regions");
        jButtonShowAllRegions.setContentAreaFilled(false);
        jButtonShowAllRegions.setOpaque(true);
        jButtonShowAllRegions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowAllRegionsActionPerformed(evt);
            }
        });

        jButtonShowAverageRegions.setBackground(new java.awt.Color(51, 51, 51));
        jButtonShowAverageRegions.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonShowAverageRegions.setForeground(new java.awt.Color(255, 255, 255));
        jButtonShowAverageRegions.setText("Show average regions");
        jButtonShowAverageRegions.setContentAreaFilled(false);
        jButtonShowAverageRegions.setOpaque(true);
        jButtonShowAverageRegions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowAverageRegionsActionPerformed(evt);
            }
        });

        jSlider.setBackground(new java.awt.Color(51, 51, 51));
        jSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderStateChanged(evt);
            }
        });
        jSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jSliderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jSliderMouseExited(evt);
            }
        });

        jCheckBoxBlue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBoxBlue.setText("Display blue regions");
        jCheckBoxBlue.setOpaque(false);

        jCheckBoxRed.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBoxRed.setText("Display red regions");
        jCheckBoxRed.setOpaque(false);

        seriesNameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        seriesNameLabel.setForeground(new java.awt.Color(51, 51, 51));

        jLabelXYcoordinates.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelXYcoordinates.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanelAnalysisLayout = new javax.swing.GroupLayout(jPanelAnalysis);
        jPanelAnalysis.setLayout(jPanelAnalysisLayout);
        jPanelAnalysisLayout.setHorizontalGroup(
            jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                        .addComponent(jButtonReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPlay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonForward)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonShowAllRegions)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonShowAverageRegions)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIndex)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                        .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabeltimestamp2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabeltimestamp1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                        .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxBlue)
                            .addComponent(jCheckBoxRed)
                            .addComponent(seriesNameLabel)
                            .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                                .addComponent(jLabelImage1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelImage2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelHeatmap))
                            .addComponent(jLabelXYcoordinates))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelAnalysisLayout.setVerticalGroup(
            jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(seriesNameLabel)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelImage1)
                            .addComponent(jLabelImage2)
                            .addComponent(jLabelHeatmap))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelXYcoordinates)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                        .addComponent(jCheckBoxRed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                                .addComponent(jCheckBoxBlue)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelAnalysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonReset)
                                    .addComponent(jButtonBack)
                                    .addComponent(jButtonPlay)
                                    .addComponent(jButtonForward)
                                    .addComponent(jButtonShowAllRegions)
                                    .addComponent(jButtonShowAverageRegions)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAnalysisLayout.createSequentialGroup()
                                .addComponent(jLabeltimestamp1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabeltimestamp2))))
                    .addGroup(jPanelAnalysisLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        initializeSlider();

        jTabbedPane.addTab("Analysis", jPanelAnalysis);

        jPanelSettings.setBackground(new java.awt.Color(204, 204, 204));
        jPanelSettings.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanelSettingsFocusGained(evt);
            }
        });

        jLabelWindowSize.setBackground(new java.awt.Color(255, 255, 255));
        jLabelWindowSize.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelWindowSize.setForeground(new java.awt.Color(51, 51, 51));
        jLabelWindowSize.setText("Window size:");

        jTextFieldWindowSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldWindowSize.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldWindowSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldWindowSizeActionPerformed(evt);
            }
        });

        jLabelStrideSize.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelStrideSize.setForeground(new java.awt.Color(51, 51, 51));
        jLabelStrideSize.setText("Stride size:");

        jTextFieldStrideSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldStrideSize.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldStrideSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStrideSizeActionPerformed(evt);
            }
        });

        jLabelThresholdAmp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelThresholdAmp.setForeground(new java.awt.Color(51, 51, 51));
        jLabelThresholdAmp.setText("Threshold amplification:");

        jTextFieldThresholdAmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldThresholdAmp.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldThresholdAmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldThresholdAmpActionPerformed(evt);
            }
        });

        jLabelMaxDisplay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelMaxDisplay.setForeground(new java.awt.Color(51, 51, 51));
        jLabelMaxDisplay.setText("Max. display:");

        jTextFieldMaxDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldMaxDisplay.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldMaxDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMaxDisplayActionPerformed(evt);
            }
        });

        jButtonConfirmSettings.setBackground(new java.awt.Color(51, 51, 51));
        jButtonConfirmSettings.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonConfirmSettings.setForeground(new java.awt.Color(255, 255, 255));
        jButtonConfirmSettings.setText("Confirm");
        jButtonConfirmSettings.setContentAreaFilled(false);
        jButtonConfirmSettings.setOpaque(true);
        jButtonConfirmSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmSettingsActionPerformed(evt);
            }
        });

        jLabelThresholdAtten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelThresholdAtten.setForeground(new java.awt.Color(51, 51, 51));
        jLabelThresholdAtten.setText("Threshold attenuation:");

        jTextFieldThresholdAtten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldThresholdAtten.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldThresholdAtten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldThresholdAttenActionPerformed(evt);
            }
        });

        jLabelMinIntensity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelMinIntensity.setForeground(new java.awt.Color(51, 51, 51));
        jLabelMinIntensity.setText("Min. intensity:");

        jTextFieldMinIntensity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldMinIntensity.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldMinIntensity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMinIntensityActionPerformed(evt);
            }
        });

        jLabelHeatmapResolution.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelHeatmapResolution.setForeground(new java.awt.Color(51, 51, 51));
        jLabelHeatmapResolution.setText("Heatmap resolution:");

        jTextFieldHeatmapResolution.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldHeatmapResolution.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldHeatmapResolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHeatmapResolutionActionPerformed(evt);
            }
        });

        jLabelHeatMapSensitivity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelHeatMapSensitivity.setForeground(new java.awt.Color(51, 51, 51));
        jLabelHeatMapSensitivity.setText("Heatmap sensitivity:");

        jTextFieldHeatmapSensitivity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldHeatmapSensitivity.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldHeatmapSensitivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHeatmapSensitivityActionPerformed(evt);
            }
        });

        jLabelHelpWindowSize.setText(" ? ");
        jLabelHelpWindowSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpWindowSizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpWindowSizeMouseExited(evt);
            }
        });

        jLabelHelpStrideSize.setText(" ? ");
        jLabelHelpStrideSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpStrideSizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpStrideSizeMouseExited(evt);
            }
        });

        jLabelHelpThresholdAmplification.setText(" ? ");
        jLabelHelpThresholdAmplification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpThresholdAmplificationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpThresholdAmplificationMouseExited(evt);
            }
        });

        jLabelHelpThresholdAttenuation.setText(" ? ");
        jLabelHelpThresholdAttenuation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpThresholdAttenuationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpThresholdAttenuationMouseExited(evt);
            }
        });

        jLabelHelpMaxDisplay.setText(" ? ");
        jLabelHelpMaxDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpMaxDisplayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpMaxDisplayMouseExited(evt);
            }
        });

        jLabelHelpMinIntensity.setText(" ? ");
        jLabelHelpMinIntensity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpMinIntensityMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpMinIntensityMouseExited(evt);
            }
        });

        jLabelHelpheatmapResolution.setText(" ? ");
        jLabelHelpheatmapResolution.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpheatmapResolutionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpheatmapResolutionMouseExited(evt);
            }
        });

        jLabelHelpHeatmapSensitivity.setText(" ? ");
        jLabelHelpHeatmapSensitivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpHeatmapSensitivityMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpHeatmapSensitivityMouseExited(evt);
            }
        });

        jLabelFrameDifference.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFrameDifference.setForeground(new java.awt.Color(51, 51, 51));
        jLabelFrameDifference.setText("Frame difference:");

        jTextFieldFrameDifference.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldFrameDifference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFrameDifferenceActionPerformed(evt);
            }
        });

        jLabelHelpFrameDifference.setText(" ? ");
        jLabelHelpFrameDifference.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelHelpFrameDifferenceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelHelpFrameDifferenceMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelMaxDisplay)
                        .addComponent(jButtonConfirmSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelSettingsLayout.createSequentialGroup()
                            .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelWindowSize)
                                .addComponent(jLabelStrideSize)
                                .addComponent(jLabelThresholdAmp)
                                .addComponent(jLabelThresholdAtten)
                                .addComponent(jLabelMinIntensity)
                                .addComponent(jLabelFrameDifference))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldThresholdAtten)
                                .addComponent(jTextFieldWindowSize)
                                .addComponent(jTextFieldStrideSize)
                                .addComponent(jTextFieldThresholdAmp, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(jTextFieldMaxDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addComponent(jTextFieldMinIntensity)
                                .addComponent(jTextFieldHeatmapResolution)
                                .addComponent(jTextFieldHeatmapSensitivity)
                                .addComponent(jTextFieldFrameDifference))))
                    .addComponent(jLabelHeatmapResolution)
                    .addComponent(jLabelHeatMapSensitivity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelHelpWindowSize)
                    .addComponent(jLabelHelpStrideSize)
                    .addComponent(jLabelHelpThresholdAmplification)
                    .addComponent(jLabelHelpThresholdAttenuation)
                    .addComponent(jLabelHelpMaxDisplay)
                    .addComponent(jLabelHelpMinIntensity)
                    .addComponent(jLabelHelpheatmapResolution)
                    .addComponent(jLabelHelpHeatmapSensitivity)
                    .addComponent(jLabelHelpFrameDifference))
                .addContainerGap(423, Short.MAX_VALUE))
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelWindowSize)
                    .addComponent(jTextFieldWindowSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpWindowSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStrideSize)
                    .addComponent(jTextFieldStrideSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpStrideSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelThresholdAmp)
                    .addComponent(jTextFieldThresholdAmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpThresholdAmplification))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelThresholdAtten)
                    .addComponent(jTextFieldThresholdAtten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpThresholdAttenuation))
                .addGap(11, 11, 11)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMaxDisplay)
                    .addComponent(jTextFieldMaxDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpMaxDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMinIntensity)
                    .addComponent(jTextFieldMinIntensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpMinIntensity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHeatmapResolution)
                    .addComponent(jTextFieldHeatmapResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpheatmapResolution))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHeatMapSensitivity)
                    .addComponent(jTextFieldHeatmapSensitivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpHeatmapSensitivity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFrameDifference)
                    .addComponent(jTextFieldFrameDifference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelHelpFrameDifference))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jButtonConfirmSettings)
                .addContainerGap())
        );

        jTabbedPane.addTab("Settings", jPanelSettings);

        jPanelSeries.setBackground(new java.awt.Color(204, 204, 204));
        jPanelSeries.setAutoscrolls(true);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2ndChannel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2ndChannel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2ndChannel.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2ndChannel.setText("2nd. channel:");

        cmb2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb2.setForeground(new java.awt.Color(51, 51, 51));
        cmb2.setMaximumSize(new java.awt.Dimension(32767, 20));

        javax.swing.GroupLayout jPanel2ndChannelLayout = new javax.swing.GroupLayout(jPanel2ndChannel);
        jPanel2ndChannel.setLayout(jPanel2ndChannelLayout);
        jPanel2ndChannelLayout.setHorizontalGroup(
            jPanel2ndChannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmb2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2ndChannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2ndChannel, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2ndChannelLayout.setVerticalGroup(
            jPanel2ndChannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2ndChannelLayout.createSequentialGroup()
                .addComponent(jLabel2ndChannel)
                .addGap(18, 18, 18)
                .addComponent(cmb2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1stChannel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1stChannel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1stChannel.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1stChannel.setText("1st. channel:");

        cmb1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb1.setForeground(new java.awt.Color(51, 51, 51));
        cmb1.setMaximumSize(new java.awt.Dimension(32767, 20));

        javax.swing.GroupLayout jPanel1stChannelLayout = new javax.swing.GroupLayout(jPanel1stChannel);
        jPanel1stChannel.setLayout(jPanel1stChannelLayout);
        jPanel1stChannelLayout.setHorizontalGroup(
            jPanel1stChannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1stChannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1stChannel)
                .addContainerGap(40, Short.MAX_VALUE))
            .addComponent(cmb1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1stChannelLayout.setVerticalGroup(
            jPanel1stChannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1stChannelLayout.createSequentialGroup()
                .addComponent(jLabel1stChannel)
                .addGap(18, 18, 18)
                .addComponent(cmb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 319, Short.MAX_VALUE))
        );

        jPanelNames.setBackground(new java.awt.Color(204, 204, 204));

        jLabelExperimentData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelExperimentData.setForeground(new java.awt.Color(51, 51, 51));
        jLabelExperimentData.setText("Experiment data:");

        javax.swing.GroupLayout jPanelNamesLayout = new javax.swing.GroupLayout(jPanelNames);
        jPanelNames.setLayout(jPanelNamesLayout);
        jPanelNamesLayout.setHorizontalGroup(
            jPanelNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNamesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelExperimentData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelNamesLayout.setVerticalGroup(
            jPanelNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNamesLayout.createSequentialGroup()
                .addComponent(jLabelExperimentData)
                .addGap(0, 360, Short.MAX_VALUE))
        );

        jLabelFileName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFileName.setForeground(new java.awt.Color(51, 51, 51));

        jButtonSeriesConfirm.setBackground(new java.awt.Color(51, 51, 51));
        jButtonSeriesConfirm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonSeriesConfirm.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSeriesConfirm.setText("Confirm");
        jButtonSeriesConfirm.setContentAreaFilled(false);
        jButtonSeriesConfirm.setOpaque(true);
        jButtonSeriesConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeriesConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonSeriesConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1stChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2ndChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelFileName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabelFileName)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1stChannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2ndChannel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSeriesConfirm)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jPanelSeriesLayout = new javax.swing.GroupLayout(jPanelSeries);
        jPanelSeries.setLayout(jPanelSeriesLayout);
        jPanelSeriesLayout.setHorizontalGroup(
            jPanelSeriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        jPanelSeriesLayout.setVerticalGroup(
            jPanelSeriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane.addTab("Series", jPanelSeries);

        jProgressBar.setPreferredSize(new java.awt.Dimension(146, 20));

        jButtonCancel.setBackground(new java.awt.Color(255, 255, 255));
        jButtonCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonCancel.setForeground(new java.awt.Color(51, 51, 51));
        jButtonCancel.setText("Cancel");
        jButtonCancel.setContentAreaFilled(false);
        jButtonCancel.setOpaque(true);
        jButtonCancel.setPreferredSize(new java.awt.Dimension(65, 20));
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jMenuBar.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar.setBorderPainted(false);

        jMenuFile.setBackground(new java.awt.Color(51, 51, 51));
        jMenuFile.setBorder(null);
        jMenuFile.setForeground(new java.awt.Color(255, 255, 255));
        jMenuFile.setText("File");
        jMenuFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuFile.setOpaque(true);

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemNew.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemNew.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemNew.setText("New");
        jMenuItemNew.setOpaque(true);
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemAnalysis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemAnalysis.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemAnalysis.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemAnalysis.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemAnalysis.setText("Analysis");
        jMenuItemAnalysis.setOpaque(true);
        jMenuItemAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAnalysisActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemAnalysis);

        jMenuExportRegions.setBackground(new java.awt.Color(51, 51, 51));
        jMenuExportRegions.setForeground(new java.awt.Color(255, 255, 255));
        jMenuExportRegions.setText("Export regions as");
        jMenuExportRegions.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuExportRegions.setOpaque(true);

        jMenuItemExportRegionsGIF.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemExportRegionsGIF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemExportRegionsGIF.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemExportRegionsGIF.setText("animated GIF");
        jMenuItemExportRegionsGIF.setOpaque(true);
        jMenuItemExportRegionsGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportRegionsGIFActionPerformed(evt);
            }
        });
        jMenuExportRegions.add(jMenuItemExportRegionsGIF);

        jMenuItemExportRegionsCSV.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemExportRegionsCSV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemExportRegionsCSV.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemExportRegionsCSV.setText("comma seperated values CSV");
        jMenuItemExportRegionsCSV.setOpaque(true);
        jMenuItemExportRegionsCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportRegionsCSVActionPerformed(evt);
            }
        });
        jMenuExportRegions.add(jMenuItemExportRegionsCSV);

        jMenuFile.add(jMenuExportRegions);

        jMenuExportControls.setBackground(new java.awt.Color(51, 51, 51));
        jMenuExportControls.setForeground(new java.awt.Color(255, 255, 255));
        jMenuExportControls.setText("Export control as");
        jMenuExportControls.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuExportControls.setOpaque(true);

        jMenuItemExportControlsGIF.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemExportControlsGIF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemExportControlsGIF.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemExportControlsGIF.setText("animated GIF");
        jMenuItemExportControlsGIF.setOpaque(true);
        jMenuItemExportControlsGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportControlsGIFActionPerformed(evt);
            }
        });
        jMenuExportControls.add(jMenuItemExportControlsGIF);

        jMenuFile.add(jMenuExportControls);

        jMenuExportHeatmap.setBackground(new java.awt.Color(51, 51, 51));
        jMenuExportHeatmap.setForeground(new java.awt.Color(255, 255, 255));
        jMenuExportHeatmap.setText("Export heatmap as");
        jMenuExportHeatmap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuExportHeatmap.setOpaque(true);

        jMenuItemExportHeatmapGIF.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemExportHeatmapGIF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemExportHeatmapGIF.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemExportHeatmapGIF.setText("animated GIF");
        jMenuItemExportHeatmapGIF.setOpaque(true);
        jMenuItemExportHeatmapGIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportHeatmapGIFActionPerformed(evt);
            }
        });
        jMenuExportHeatmap.add(jMenuItemExportHeatmapGIF);

        jMenuFile.add(jMenuExportHeatmap);

        jMenuRegionOfInterest.setBackground(new java.awt.Color(51, 51, 51));
        jMenuRegionOfInterest.setForeground(new java.awt.Color(255, 255, 255));
        jMenuRegionOfInterest.setText("Region of interest");
        jMenuRegionOfInterest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuRegionOfInterest.setOpaque(true);

        jMenuItemRegionOfInterestNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRegionOfInterestNew.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemRegionOfInterestNew.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemRegionOfInterestNew.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemRegionOfInterestNew.setText("New");
        jMenuItemRegionOfInterestNew.setOpaque(true);
        jMenuItemRegionOfInterestNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRegionOfInterestNewActionPerformed(evt);
            }
        });
        jMenuRegionOfInterest.add(jMenuItemRegionOfInterestNew);

        jMenuItemRegionOfInterestRemove.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRegionOfInterestRemove.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemRegionOfInterestRemove.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemRegionOfInterestRemove.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemRegionOfInterestRemove.setText("Remove");
        jMenuItemRegionOfInterestRemove.setOpaque(true);
        jMenuItemRegionOfInterestRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRegionOfInterestRemoveActionPerformed(evt);
            }
        });
        jMenuRegionOfInterest.add(jMenuItemRegionOfInterestRemove);

        jMenuFile.add(jMenuRegionOfInterest);

        jMenuBar.add(jMenuFile);

        jMenuEdit.setBackground(new java.awt.Color(51, 51, 51));
        jMenuEdit.setBorder(null);
        jMenuEdit.setForeground(new java.awt.Color(255, 255, 255));
        jMenuEdit.setText("Edit");
        jMenuEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuEdit.setOpaque(true);

        jMenuItemSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSettings.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemSettings.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemSettings.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemSettings.setText("Settings");
        jMenuItemSettings.setOpaque(true);
        jMenuItemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSettingsActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemSettings);

        jMenuItemSeries.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSeries.setBackground(new java.awt.Color(51, 51, 51));
        jMenuItemSeries.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItemSeries.setForeground(new java.awt.Color(255, 255, 255));
        jMenuItemSeries.setText("Series");
        jMenuItemSeries.setOpaque(true);
        jMenuItemSeries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSeriesActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemSeries);

        jMenuBar.add(jMenuEdit);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /**
     * Creates new BufferedImage from RawImage
     * @param img the RawImage
     * @return new BufferedImage 
     */
    public BufferedImage getBufferedImage(RawImage img, BufferedImage buffImg) {
        int sizeX = img.getWidth();
        int sizeY = img.getHeight();
        String color = img.getLUTname();
        byte[] buff = img.getData();
        
        buffImg = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB); 
        
        int RGBcolor;
        
        //currently supported colors: Red, Green, Blue, Gray, Cyan, Magenta, Yellow 
        for(int h=0;h<sizeY;h++) {
            for(int w=0;w<sizeX;w++)
            {
                RGBcolor = buff[h*sizeX + w] & 0xFF;
                switch (color) {
                    case "Red":
                        RGBcolor = RGBcolor << 16;
                        break;
                    case "Green":
                        RGBcolor = RGBcolor << 8;
                        break;
                    case "Gray":
                        RGBcolor = RGBcolor << 16 | RGBcolor << 8 | RGBcolor;
                        break;
                    case "Yellow":
                        RGBcolor = RGBcolor << 16 | RGBcolor << 8;
                        break;
                    case "Cyan":
                        RGBcolor =RGBcolor << 8 | RGBcolor;
                        break;
                    case "Magenta":
                        RGBcolor = RGBcolor << 16 | RGBcolor;
                        break;
                    default:
                        //(color.equals("Blue"))
                        break;
                }
                buffImg.setRGB(w,h, RGBcolor);
            } 
        }
        return buffImg;
    }    
    
    /**
     * Returns median intensity of given array. 
     * @param rgbArray the array of colors from some BufferedImage region
     * @return median intensity of given array.
     */
    public double getMedianIntensity(int[] rgbArray) {
        double intensity = 0;
        for(int i = 0; i < rgbArray.length; i++) {
            intensity += rgbArray[i];
        }
        return intensity/(windowSize*windowSize);
    }
    
    /**
     * Calculates the intensities of the given region and saves them in array.
     * @param img the image of which the intensity is calculated
     * @param x the x coordinate of region
     * @param y the y coordinate of region
     * @param width the width and height of region
     * @param arr the array calculated intensities are stored in
     */
    public void getIntensityValues(RawImage img, int x, int y, int width, int[] arr) {
        byte[] buff = img.getData();
        int sizeX = img.getWidth();
        int i = 0;
        for(int h=y;h < y + width; h++) {
            for(int w=x;w < x + width; w++)
            {
                arr[i] = buff[h*sizeX + w] & 0xFF;        
                i++;
            }
        }
    }
    
    /**
     * Draws rectangle on Buffered image with specified parameters.
     * @param img the Buffered image to be modified
     * @param x the x coordinate of rectangle
     * @param y the y coordinate of rectangle
     * @param width the width of the rectangle
     * @param col the RGB color of rectangle
     */
    public void drawRectangle(BufferedImage img, int x, int y, int width, int col) {
        for(int i = x; i < x + width; i++) {
            img.setRGB(i, y, col);
            img.setRGB(i, y + 1, col);
            img.setRGB(i, y + width - 1, col);
            img.setRGB(i, y + width - 2, col);
        }
        for(int i = y; i < y + width; i++) {
            img.setRGB(x, i, col);
            img.setRGB(x + 1, i, col);
            img.setRGB(x + width - 1, i, col);
            img.setRGB(x + width - 2, i, col);
        }
    }
    
    /**
     * Calculates if ray starting at the given point intersects with the given line segment. 
     * @param px the x coordinate of the point
     * @param py the y coordinate of the point
     * @param A the starting point of the line segment
     * @param B the ending point of the line segment
     * @return true if ray intersects with line segment, else returns false
     */
    public boolean intersects(double px, double py, Point A, Point B) {
        double vx = 1.0;
        double vy = Math.PI;
        
        double ax = (double)A.getX();
        double ay = (double)A.getY();

        double bx = (double)B.getX();
        double by = (double)B.getY();        
        
        double B_Ax = bx - ax;
        double B_Ay = by - ay;

        double A_px = ax - px;
        double A_py = ay - py;
        
        double p_Ax = px - ax;
        double p_Ay = py - ay;
        
        double t = (A_px*B_Ay - A_py*B_Ax)/(vx*B_Ay - vy*B_Ax);
        double s = (p_Ax*vy - p_Ay*vx)/(B_Ax*vy - B_Ay*vx);
        
        return t >= 0 && s > 0 && s <= 1;
    }
    
    /**
     * Counts the number of intersections between the ray starting at the given point and surface define with border.  
     * @param x the x coordinate of the starting point
     * @param y the y coordinate of the starting point
     * @return the number of intersections
     */
    public int countIntersections(double x, double y) {
        int interSections = 0;
        int size = border.size();
        for(int i = 0; i < size; i++) {
            if(intersects(x, y, border.get(i), border.get((i+1)%size))) {
                interSections++;
            }
        }
        return interSections;
    }   
    
    /**
     * Calculates if the given point lies in the region of interest.
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param winSize the window  size of regions
     * @return true if point(x, y) lies in the region of interest
     */
    public boolean liesInRegionOfInterest(double x, double y, double winSize) {
        if(border == null || border.size() < 2) {
            return true;
        }        
        
        double c = winSize*Math.sqrt(2.0);
        int inter1 = countIntersections(x + c/2.0, y + c/2.0);      
        return inter1%2 == 1;
    }
    
    /**
     * Scans through given BufferedImage images and detects regions where relative change of intensity ratio is greater than thresholdAmp or less than thresholdAtten.
     * @param prevG the Buffered image for 1st channel in previous time point 
     * @param currG the Buffered image for 1st channel in current time point
     * @param prevY the Buffered image for 2nd channel in previous time point
     * @param currY the Buffered image for 2nd channel in current time point
     * @param mySet the ArrayLsit where detected regions are stored
     * @param rgbArray the array of predefined size for RGB pixels 
     * @return the greatest change in intensity ratio detected in current scan
     */
    public double getRegions(RawImage prevG, RawImage currG, RawImage prevY, RawImage currY, ArrayList<ArrayList<Double>> mySet, int[] rgbArray) {
        regionsAmp = new ArrayList<>();
        regionsAtte = new ArrayList<>();
        double maxRatio = 0;   
        for(int x = 0; x  <= prevG.getWidth() - windowSize; x = x + stride) {
            for(int y = 0; y <= prevG.getHeight() - windowSize; y = y + stride) {
                //for every region check if it lies in the bounded surface
                if(liesInRegionOfInterest((double)x, (double)y, (double)windowSize)) {

                    double[] ratioData = getRatio(prevG, currG, prevY, currY, x, y, windowSize, rgbArray);
                    double ratio = ratioData[0];
                    double intensity = ratioData[1];
                    if(ratio > maxRatio) {
                        maxRatio = ratio;
                    }
                    ArrayList<Double> reg = new ArrayList<>();
                    if(intensity > minIntensity) {
                        setOfRegions.add(Arrays.asList(x,y));
                        reg.add((double)x);
                        reg.add((double)y);
                        reg.add(ratio);
                        if(ratio > thresholdAmp) {
                            reg.add(1.0);
                            reg.add((double)windowSize);
                            regionsAmp.add(reg);
                        }
                        if(ratio < thresholdAtten) {
                            reg.add(-1.0);
                            reg.add((double)windowSize);
                            regionsAtte.add(reg);
                        }
                    }
                }
            }
        }
        Collections.sort(regionsAmp, new MyComparatorInensityAmp());
        Collections.sort(regionsAtte, new MyComparatorInensityAtte());

        int max = Math.min(maxDisplay, regionsAmp.size());
        if(maxDisplay == 0) {
            max = regionsAmp.size();
        }
        for(int i = 0; i < max; i++) {
            mySet.add(regionsAmp.get(i));
        }    
        max = Math.min(maxDisplay, regionsAtte.size());
        if(maxDisplay == 0) {
            max = regionsAtte.size();
        }
        for(int i = 0; i < max; i++) {
            mySet.add(regionsAtte.get(i));          
        } 
        return maxRatio;
    }
    
    /**
     * Iterates through detected responses in time ind and draws them on BufferedImage.
     * @param ind the time point index
     * @param img the BufferedImage to be modified
     * @param detectedResponsesc the ArrayList of detected responses for every time point
     */
    public void drawRegions(int ind, BufferedImage img, ArrayList<ArrayList<ArrayList<Double>>> detectedResponsesc) {
        int red = 255 << 16;
        int blue = 255;
        ArrayList<ArrayList<Double>> setOfCoordinates = detectedResponsesc.get(ind);
        for (ArrayList<Double> point: setOfCoordinates) {
            int x = point.get(0).intValue();
            int y = point.get(1).intValue();
            double ratio = point.get(2);
            int width = point.get(4).intValue();
            if(point.get(3) > 0.0 && jCheckBoxRed.isSelected()) {
                //display region in red
                drawRectangle(img , x, y, width, red);
            }
            else if(point.get(3) <= 0.0 && jCheckBoxBlue.isSelected()) {
                //display region blue
                drawRectangle(img , x, y, width, blue);
            }
        }     
    }
    
    /**
     * Draws the border of the region if interest in pixels.
     * @param img the image on which the border is being drawn
     */
    public void drawBorder(BufferedImage img) {
        int white = 255 | (255 << 8) | (255 << 16);
        int red = 255 << 16;
        int blue = 255;
        
        
        if(border != null) {
            for(Point p : border) {
                if(p.getX() > img.getWidth() || p.getY() > img.getHeight() ) {
                    System.out.println(p.getX() + " " + p.getY() + " : " + img.getWidth() + " " + img.getHeight());
                }
                try {
                    img.setRGB(p.getX(), p.getY(), white);
                    img.setRGB(p.getX() + 1, p.getY(), white);
                    img.setRGB(p.getX(), p.getY() + 1, white);
                    img.setRGB(p.getX() - 1, p.getY(), white);
                    img.setRGB(p.getX(), p.getY() - 1, white);
                    img.setRGB(p.getX() + 1, p.getY() + 1, white);
                    img.setRGB(p.getX() + 1, p.getY() - 1, white);
                    img.setRGB(p.getX() - 1, p.getY() + 1, white);
                    img.setRGB(p.getX() - 1, p.getY() - 1, white);                    
                }
                catch(Exception e) {}
            }        
        }
    }
    
    /**
     * Displays BufferedImage for first channel with marked regions, BufferedImage for second channel and heat map on jPanel. 
     */
    public void display(){
        if(imagesG != null && !imagesG.isEmpty()) {
            
            display1stCh = getBufferedImage(imagesG.get(index), display1stCh);
            getImageWithRegions(display1stCh, index);
            
            drawBorder(display1stCh);
            
            display2ndCh = getBufferedImage(imagesY.get(index), display2ndCh);
            BufferedImage heatImage = heatMap.get(index);
            int type = display1stCh.getType();

            int panelWidth = jPanelAnalysis.getWidth();
            int imageWidthl = (int)((panelWidth - 50)/3.0);
            int currWidth = display1stCh.getWidth();
            int imageHeight = (int)(display1stCh.getHeight()*(double)imageWidthl/(double)currWidth);

            newImageG = new BufferedImage(imageWidthl, imageHeight, BufferedImage.TYPE_INT_RGB);
            newImageY = new BufferedImage(imageWidthl, imageHeight, BufferedImage.TYPE_INT_RGB);
            newImageHeat = new BufferedImage(imageWidthl, imageHeight, BufferedImage.TYPE_INT_RGB);
            
            //call garbage colector
            Graphics g = newImageG.createGraphics();
            g.drawImage(display1stCh, 0, 0, imageWidthl, imageHeight, null);
            g.dispose();   
            g = newImageY.createGraphics();
            g.drawImage(display2ndCh, 0, 0, imageWidthl, imageHeight, null);     
            g.dispose();
            g = newImageHeat.createGraphics();
            g.drawImage(heatImage, 0, 0, imageWidthl, imageHeight, null);
            g.dispose();        

            //display images
            jLabeltimestamp1.setText(filenames1.get(index));
            jLabeltimestamp2.setText(filenames2.get(index));
            jLabelImage1.setIcon(new ImageIcon(newImageG));
            jLabelImage2.setIcon(new ImageIcon(newImageY));
            jLabelHeatmap.setIcon(new ImageIcon(newImageHeat));
         
        }
    }
    
    /**
     * Calls display method if ArrayList imagesWithRegions is not empty, otherwise resets jPanelFile.
     */
    @SuppressWarnings("empty-statement")
    public void displayImagesOnPane() {
        try {
            if(maxIndex > 0) {
                double time = (double)timePnts.get(index);
                jLabelIndex.setText("Time: " + String.format(Locale.US, "%.2f", time) + " s");
                try {
                    display();
                    //update slider
                    if(jSlider.getValue() != index) {
                        jSlider.setValue(index);
                    }
                    //call garbage collector
                    //System.gc();
                }catch(Exception e){
                    System.err.println(e);
                }
            }
            else {
                jLabelIndex.setText("");
                jLabeltimestamp1.setText("");
                jLabeltimestamp2.setText("");
                jLabelImage1.setIcon(new ImageIcon());
                jLabelImage2.setIcon(new ImageIcon());
                jLabelHeatmap.setIcon(new ImageIcon());
            }        
        }
        catch(Exception e) {}
    }
    
    /**
     * Populates jPanelSeries with radio buttons. 
     * Every button corresponds to image series obtained from .lif file.
     */
    public void populatePaneWithSeries() {
        jPanelNames.setLayout(new BoxLayout(jPanelNames, BoxLayout.Y_AXIS));
        ArrayList<SeriesData> allSeriesData = lifReader.getAllSeriesData();
        if(group != null) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                jPanelNames.remove(button);
            }        
        }
        group = new ButtonGroup();
        int i = 0;
        for(SeriesData data: allSeriesData) {
            if(data.getSeriesCount() > 1) {
                JRadioButton btn = new JRadioButton(data.getSeriesName());
                btn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                btn.setForeground(new java.awt.Color(51, 51, 51));
                btn.setName(Integer.toString(i));
                btn.setBackground(new Color(204, 204, 204));
                group.add(btn);
                jPanelNames.add(btn);
                
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cmb1.removeAllItems();
                        cmb2.removeAllItems();
                        JRadioButton btn = (JRadioButton)(e.getSource());
                        int id = Integer.parseInt(btn.getName());
                        int size = lifReader.getAllSeriesData().get(id).getChannels().size();
                        cmb1.setSize(jPanel1stChannel.getWidth(), btn.getHeight());
                        cmb2.setSize(jPanel2ndChannel.getWidth(), btn.getHeight());
                        for(int j = 1; j <= size; j++) {
                            cmb1.addItem(j);
                            cmb2.addItem(j);
                        }
                        cmb1.setSelectedIndex(0);
                        cmb2.setSelectedIndex(1);
                        cmb1.setBounds(0, btn.getY(), jPanel1stChannel.getWidth(), btn.getHeight());
                        cmb2.setBounds(0, btn.getY(), jPanel2ndChannel.getWidth(), btn.getHeight());
                        jPanel1stChannel.add(cmb1);
                        jPanel2ndChannel.add(cmb2);
                        cmb1.setVisible(true);
                        cmb2.setVisible(true);
                    }
                });
            }
            i++;
        }
    }
    
    /**
     * This method is called when jMenuItemNew is clicked. 
     * Opens file chooser and when .lif file is selected reads the header.
     * @param evt 
     */
    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
       
        //open file explorer and select source folder
        if(singletonWorkerReadLifFile == null) {
            cmb1.setVisible(false);
            cmb2.setVisible(false);
            
            fc.setDialogTitle("Open");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setSelectedFile(null);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("LIF files (*.lif)", "lif");
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(filter);
            
            int returnValue = fc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {               
                jTabbedPane.setSelectedIndex(2);
                try {
                    lifReader = null;
                    System.gc();
                    lifReader = new LifReader(fc.getSelectedFile().getAbsolutePath());
                    //populate jTabebed pane with series
                    folderPath = fc.getSelectedFile().getAbsolutePath();
                    filename = fc.getSelectedFile().getName();
                    populatePaneWithSeries();
                    imagesInBuffer = false;
                    jLabelFileName.setText(filename);
                } catch (IOException | ParserConfigurationException | SAXException | FormatException e) {
                    System.err.println(e);
                }
            }
        }
    }//GEN-LAST:event_jMenuItemNewActionPerformed
    
    /**
     * This method is called when jMenuItemSettings is clicked.
     * Changes the selected index of jTabbedPane to settings panel.
     * @param evt 
     */
    private void jMenuItemSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSettingsActionPerformed
        //change tab in tabbed pane
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItemSettingsActionPerformed
    
    /**
     * This method is called when jButtonPlay is clicked.
     * Stops the timer if tim is running otherwise starts it.
     * @param evt 
     */
    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayActionPerformed
        // do timeLapse
        //start from current index
        if(maxIndex > 0) {
            if(tim.isRunning()) {
                tim.stop();
            }
            else {
                tim.start();          
            }
        }
    }//GEN-LAST:event_jButtonPlayActionPerformed
    
    /**
     * This method is called when jButtonForward is clicked. 
     * Stops the timer and increments the index property. At the end calls displayImagesOnPane method.
     * @param evt 
     */
    private void jButtonForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonForwardActionPerformed
        tim.stop();
        index++;
        currChilds = new ArrayList<>();
        if(index >= maxIndex) {
            index = maxIndex - 1;
        }
        displayImagesOnPane();
    }//GEN-LAST:event_jButtonForwardActionPerformed
    
    /**
     * This method is called when jButtonBack is clicked.
     * Stops the timer and decrement the index property. At the end calls displayImagesOnPane method.
     * @param evt 
     */
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        tim.stop();
        index--;
        currChilds = new ArrayList<>();
        if (index < 0) {
            index = 0;
        }
        displayImagesOnPane();
    }//GEN-LAST:event_jButtonBackActionPerformed
    
    /**
     * Sets the windowSize property.
     * @param evt 
     */
    private void jTextFieldWindowSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldWindowSizeActionPerformed
        try {
            windowSize = Integer.parseInt(jTextFieldWindowSize.getText());
        }
        catch(Exception e){}
    }//GEN-LAST:event_jTextFieldWindowSizeActionPerformed

    private void jPanelSettingsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanelSettingsFocusGained

    }//GEN-LAST:event_jPanelSettingsFocusGained
    
    /**
     * Sets text fields for all avaliable properties in settings.
     * @param evt 
     */
    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        jTextFieldWindowSize.setText(Integer.toString(windowSize));
        jTextFieldStrideSize.setText(Integer.toString(stride));
        jTextFieldThresholdAmp.setText(Double.toString(thresholdAmp));
        jTextFieldMaxDisplay.setText(Integer.toString(maxDisplay));
        jTextFieldThresholdAtten.setText(Double.toString(thresholdAtten));
        jTextFieldMinIntensity.setText(Double.toString(minIntensity));
        jTextFieldHeatmapResolution.setText(Integer.toString(heatmapResolution));
        jTextFieldHeatmapSensitivity.setText(Double.toString(heatMapSensitivity));
        jTextFieldFrameDifference.setText(Integer.toString(frameDifference));
    }//GEN-LAST:event_jTabbedPaneStateChanged
    
    /**
     * Sets the thresholdAmp property.
     * @param evt 
     */
    private void jTextFieldThresholdAmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldThresholdAmpActionPerformed
        try {
            thresholdAmp = Double.parseDouble(jTextFieldThresholdAmp.getText());
        }
        catch(Exception e){}
    }//GEN-LAST:event_jTextFieldThresholdAmpActionPerformed
    
    /**
     * Sets the stride property.
     * @param evt 
     */
    private void jTextFieldStrideSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStrideSizeActionPerformed
        try {
            stride = Integer.parseInt(jTextFieldStrideSize.getText());
        }
        catch(Exception e) {}
    }//GEN-LAST:event_jTextFieldStrideSizeActionPerformed
    
    /**
     * Sets the maxDisplay property
     * @param evt 
     */
    private void jTextFieldMaxDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMaxDisplayActionPerformed
        try {
            maxDisplay = Integer.parseInt(jTextFieldMaxDisplay.getText());
        }
        catch(Exception e) {}
    }//GEN-LAST:event_jTextFieldMaxDisplayActionPerformed
    
    /**
     * This method is called when jButtonConfirmSettings is clicked.
     * Saves newly set properties and recalculates new regions and heat map.
     * @param evt 
     */
    private void jButtonConfirmSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmSettingsActionPerformed
        //button confirmed         
        //save current cells
        try {
            windowSize = Integer.parseInt(jTextFieldWindowSize.getText());
        }
        catch(Exception e){}       
        try {
            thresholdAmp = Double.parseDouble(jTextFieldThresholdAmp.getText());
        }
        catch(Exception e){} 
        try {
            stride = Integer.parseInt(jTextFieldStrideSize.getText());
        }
        catch(Exception e) {}        
        try {
            maxDisplay = Integer.parseInt(jTextFieldMaxDisplay.getText());
        }
        catch(Exception e) {}  
        try {
            thresholdAtten = Double.parseDouble(jTextFieldThresholdAtten.getText());
        }
        catch(Exception e){}  
        try {
            minIntensity = Double.parseDouble(jTextFieldMinIntensity.getText());
        }
        catch(Exception e){} 
        try {
            heatmapResolution = Integer.parseInt(jTextFieldHeatmapResolution.getText());
        }
        catch(Exception e) {}
        try {
            heatMapSensitivity = Double.parseDouble(jTextFieldHeatmapSensitivity.getText());
        }
        catch(Exception e) {}
        try {
            frameDifference = Integer.parseInt(jTextFieldFrameDifference.getText());
        }
        catch(Exception e) {}
        
        //excecute worker
        //go on first pane
        jTabbedPane.setSelectedIndex(0);
        
        if(singletonWorkerProcessImages == null) {
            singletonWorkerProcessImages = new ProcessImages();
            singletonWorkerProcessImages.execute();         
        } 
    }//GEN-LAST:event_jButtonConfirmSettingsActionPerformed
    
    /**
     * Sets the thresholdAtten property.
     * @param evt 
     */
    private void jTextFieldThresholdAttenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldThresholdAttenActionPerformed
        try {
            thresholdAtten = Double.parseDouble(jTextFieldThresholdAtten.getText());
        }
        catch(Exception e){}       
        
    }//GEN-LAST:event_jTextFieldThresholdAttenActionPerformed
    
    /**
     * Sets the minIntensity property.
     * @param evt 
     */
    private void jTextFieldMinIntensityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMinIntensityActionPerformed
        try {
            minIntensity = Double.parseDouble(jTextFieldMinIntensity.getText());
        }
        catch(Exception e){}   
    }//GEN-LAST:event_jTextFieldMinIntensityActionPerformed
    
    /**
     * This method is called when mouse cursor enters jLabelImage1 label.
     * Sets the mouse cursor to hand.
     * @param evt 
     */
    private void jLabelImage1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImage1MouseEntered
        //change mouse cursor to hand
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabelImage1MouseEntered
    
    /**
     * This method is called when mouse exits jLabelImage1 label.
     * Sets the mouse cursor to default cursor.
     * @param evt 
     */
    private void jLabelImage1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImage1MouseExited
        //change mouse cursor back to default cursor
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabelImage1MouseExited
    
    /**
     * Returns index of Region mouse is hovering.
     * @param x the x position of mouse over jLabelImage1
     * @param y the y position of mouse over jLabelImage1
     * @param regions the ArrayList of regions
     * @return index of Region mouse is hovering over or -1 otherwise
     */
    public int hoveringOverRegion(int x, int y, ArrayList<Region> regions) {
        int i = 0;
        //imageWidth
        int width = jLabelImage1.getWidth();
        double scale = (double)imageWidth/(double)width;
        x = (int)(x*scale);
        y = (int)(y*scale);
        
        for(Region region: regions) {
            if(x >= region.getX() && x <= region.getX() + region.getWinSize() && y >= region.getY() && y <= region.getY() + region.getWinSize()) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    /**
     * This method is called when mouse is moved over jLabelImage1.
     * If mouse is hovering over region sets GraphData flashyCurveId property for all TimeLineJForm children and repaints them.
     * Line on graph that is corresponded to hovering region will be highlighted.
     * @param evt 
     */
    private void jLabelImage1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImage1MouseMoved
        //mouse is moved
        int x = evt.getX();
        int y = evt.getY();
        try {
            int regionId = hoveringOverRegion(x,y, imageData.get(index).getRegions());
            if(regionId >= 0) {             
                jLabelXYcoordinates.setText("Region coordinate: (X: " + imageData.get(index).getRegions().get(regionId).getX() + ", Y: " + imageData.get(index).getRegions().get(regionId).getY() + ")");   
            }
            else {
                jLabelXYcoordinates.setText("");
            }
            if(regionId >= 0 &&  !prevMouseOnScreen) {
                //repaint all childs with black curvs
                for(TimeLineJForm child: currChilds) {               
                    child.getData().setFlashyCurveId(regionId);
                    child.repaint();            
                }
                prevMouseOnScreen = true;
            }
            else if(regionId < 0 && prevMouseOnScreen) {
                //repaint all childs with no black curvs
                for(TimeLineJForm child: currChilds) {
                    child.getData().setFlashyCurveId(-1);
                    child.repaint();
                }
                prevMouseOnScreen = true;
            }
            else if(regionId < 0 && !prevMouseOnScreen){             
                prevMouseOnScreen = false;
                for(TimeLineJForm child: currChilds) {
                    child.getData().setFlashyCurveId(-1);;
                    child.repaint();
                }
                prevMouseOnScreen = false; 
            }
            else {             
                prevMouseOnScreen = false;  
            }
        }
        catch(Exception e) {        
            System.err.println(e);
        }
    }//GEN-LAST:event_jLabelImage1MouseMoved
    
    /**
     * Sets the heatmapResolution property.
     * @param evt 
     */
    private void jTextFieldHeatmapResolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHeatmapResolutionActionPerformed
        //save heatmap resolution parameter
        try {
            heatmapResolution = Integer.parseInt(jTextFieldHeatmapResolution.getText());
        }
        catch(Exception e) {}
    }//GEN-LAST:event_jTextFieldHeatmapResolutionActionPerformed
    
    /**
     * Opens file chooser and exports imagesWithRegions or hetMap to animated GIF. 
     * Creates new GIFexport object.
     * @param exportRegions if true exports imagesWithRegions else exports hetMap
     */
    private void openFileChooserAndExport(boolean exportRegions, boolean exportControls) {
        //if all background workers are free
        if(singletonWorkerAnalyseAllRegionData == null && singletonWorkerProcessImages == null && singletonWorkerReadLifFile == null && singeltonWorkerGifExport == null) {        
            fc = new JFileChooser(fc.getCurrentDirectory());
            fc.setDialogTitle("Select folder");
            fc.setSelectedFile(new File(filename + "_" + seriesName + "_" + "HeatMapAnimatedGIF_" + exportHeatMapGIF));
            if(exportRegions) {
                fc.setSelectedFile(new File(filename + "_" + seriesName + "_" + "RegionsAnimatedGIF_" + exportRegionsGIF));       
            }
            if(exportControls) {
                fc.setSelectedFile(new File(filename + "_" + seriesName + "_" + "ControlsAnimatedGIF_" + exportControlsGIF));            
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter("GIF files (*.gif)", "*.gif");
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(filter);

            int sf = fc.showSaveDialog(fc);
            if (sf == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();
                    String filename = file.getAbsolutePath();
                    if(!filename.endsWith(".gif")) {
                        file = new File(filename + ".gif");
                    }
                    //now export as gif from imagesWithRegions
                    if(singeltonWorkerGifExport == null) {
                        singeltonWorkerGifExport = new GIFexport(exportRegions, exportControls, file);
                        singeltonWorkerGifExport.execute();
                    }
                }
                catch(Exception e){}
            }
        }
    }
    
    /**
     * Calls openFileChooserAndExport with exportRegions set to true.
     * @param evt 
     */
    private void jMenuItemExportRegionsGIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportRegionsGIFActionPerformed
        //export as animated gif
        openFileChooserAndExport(true, false);    
    }//GEN-LAST:event_jMenuItemExportRegionsGIFActionPerformed
    
    /**
     * Calls openFileChooserAndExport with exportRegions set to false.
     * @param evt 
     */
    private void jMenuItemExportHeatmapGIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportHeatmapGIFActionPerformed
        //export heatmap as animated gif
        openFileChooserAndExport(false, false);              
    }//GEN-LAST:event_jMenuItemExportHeatmapGIFActionPerformed
    
    /**
     * Exports all regions in CSV format.
     * @param evt 
     */
    private void jMenuItemExportRegionsCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportRegionsCSVActionPerformed
        //export as csv
        fc = new JFileChooser(fc.getCurrentDirectory());
        fc.setDialogTitle("Select folder");
        fc.setSelectedFile(new File(filename + "_" + seriesName + "_" + "regionsData_" + exportRegionsCSV));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*.csv)", "*.csv");
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);
        exportRegionsCSV++;
        int sf = fc.showSaveDialog(fc);
        if (sf == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                String filename = file.getAbsolutePath();
                if(!filename.endsWith(".csv")) {
                    file = new File(filename + ".csv");
                }
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.println("timepoint,x,y,width,1st region intensity, 2nd region intensity, region ratio");
                int timpnt = 0;
                for(ImageData frame: imageData) {
                    for(Region region: frame.getRegions()) {
                        writer.println(Integer.toString(timpnt) + "," + Integer.toString(region.getX()) + "," + Integer.toString(region.getY()) + "," + Integer.toString(region.getWinSize()) + "," + Double.toString(region.getTimepntsG().get(timpnt)) + "," + Double.toString(region.getTimepntsY().get(timpnt)) + "," + Double.toString(region.getTimepntsRa().get(timpnt)));
                    }
                    timpnt++;
                }
                writer.close();                                  
            }catch(FileNotFoundException | UnsupportedEncodingException e){
                System.err.println(e);
                exportRegionsCSV--;
            }
        }
    }//GEN-LAST:event_jMenuItemExportRegionsCSVActionPerformed
    
    /**
     * This method is called when jButtonReset is clicked.
     * Stops timer and sets index to 0.
     * At the end calls displayImagesOnPane method.
     * @param evt 
     */
    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        //jump to beggining
        tim.stop();
        try {
            index = 0;
            displayImagesOnPane();             
        }catch(Exception e){}
    }//GEN-LAST:event_jButtonResetActionPerformed
    
    /**
     * This method is called when jPanelFile is resized.
     * Calls displayImagesOnPane method. 
     * @param evt 
     */
    private void jPanelAnalysisComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelAnalysisComponentResized
        // jpanel component is resized
        displayImagesOnPane();
    }//GEN-LAST:event_jPanelAnalysisComponentResized
    
    /**
     * This method is called when jButtonCancel is clicked.
     * Cancels all singletonWorkers and calls displayImagesOnPane method.
     * @param evt 
     */
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        //cancel current swing worker
        if(singletonWorkerProcessImages != null) {
            singletonWorkerProcessImages.cancel(true);
            singletonWorkerProcessImages = null;
        }
        if(singletonWorkerReadLifFile != null) {
            singletonWorkerReadLifFile.cancel(false);
            singletonWorkerReadLifFile = null;
        }
        if(singletonWorkerAnalyseAllRegionData != null) {
            singletonWorkerAnalyseAllRegionData.cancel(true);
            singletonWorkerAnalyseAllRegionData = null;
        }
        if(singeltonWorkerGifExport != null) {
            singeltonWorkerGifExport.cancel(true);
            singeltonWorkerGifExport = null;
        }
        displayImagesOnPane();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    /**
     * This method is called when jButtonSeriesConfirm button is clicked.
     * Iterates over all radio buttons in ButtonGroup group. If any button is selected 
     * starts new singletonWorkerReadLifFile with id parameter set to name of selected button.
     * @param evt 
     */
    private void jButtonSeriesConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeriesConfirmActionPerformed
        //get selected radio button id
        if(group != null) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    //get images based on name
                    int id = Integer.parseInt(button.getName());
                    try {
                        if(singletonWorkerProcessImages == null && singletonWorkerReadLifFile == null) {
                            singletonWorkerReadLifFile = new ReadLifFile(id, cmb1.getSelectedIndex(), cmb2.getSelectedIndex(), button.getText());
                            singletonWorkerReadLifFile.execute();
                            jTabbedPane.setSelectedIndex(0);
                            imagesInBuffer = false;
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                }
            }
        }
    }//GEN-LAST:event_jButtonSeriesConfirmActionPerformed
    
    /**
     * This method is called when jButtonShowAllRegions is clicked.
     * Calculates data for all regions in current time point.
     * Creates new TimeLineJForm and saves it to ArrayList currChilds.
     * Saves calculated data in created TimeLineJForm.
     * @param evt 
     */
    private void jButtonShowAllRegionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowAllRegionsActionPerformed
        //calculate timeline for current time point and display in new jFrame       
        if(imageData != null && !imageData.isEmpty() && imageData.get(index) != null) {
            ImageData currData = imageData.get(index);
            if(currData.getRegions().isEmpty()) {
                int[] rgbArray = new int[currData.getWinSize()*currData.getWinSize()];
                ArrayList<ArrayList<Double>> regionsInFrame = detectedResponses.get(index);
                for(ArrayList<Double> myRegion: regionsInFrame) {
                    //go through all images and save timeline
                    Region newRegion = new Region();
                    currData.getRegions().add(newRegion);
                    newRegion.setRed((myRegion.get(3) > 0.0));
                    analyseRegion(myRegion.get(0).intValue(), myRegion.get(1).intValue(), myRegion.get(4).intValue(), newRegion, imagesG, imagesY, rgbArray);
                } 
            }
            if(!currData.getRegions().isEmpty()) {
                TimeLineJForm frame = new TimeLineJForm();
                frame.setResizable(true);
                frame.setParent(this);
                frame.setTitle("Response timeline");
                frame.getData().setDrawingData(imageData.get(index), timePnts);           
                frame.setVisible(true);
                currChilds.add(frame); //save frame to curr childs, it is updated when mouse hovers over a region
            }
        }
    }//GEN-LAST:event_jButtonShowAllRegionsActionPerformed

    /**
     * This method is called when jButtonShowAverageRegions is clicked.
     * Calculates data for all regions in all time point.
     * Creates new TimeLineWithWindowJForm and saves it to ArrayList currChilds.
     * Saves calculated data in created TimeLineJForm.
     * @param evt 
     */
    private void jButtonShowAverageRegionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowAverageRegionsActionPerformed
        if(imageData != null && !imageData.isEmpty()) {
            if(singletonWorkerAnalyseAllRegionData == null) {
                singletonWorkerAnalyseAllRegionData = new AnalyseAllRegionData();
                singletonWorkerAnalyseAllRegionData.execute();
            }
        }
    }//GEN-LAST:event_jButtonShowAverageRegionsActionPerformed

    private void jMenuItemSeriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSeriesActionPerformed
        //switch to series
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_jMenuItemSeriesActionPerformed

    private void jMenuItemAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAnalysisActionPerformed
        //switch to analysis
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jMenuItemAnalysisActionPerformed

    private void jSliderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderMouseEntered
        // set mouse cursor to hand
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jSliderMouseEntered

    private void jSliderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderMouseExited
        // set mouse cursor to default
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jSliderMouseExited

    /**
     * This method is invoked when position of the slider changes.
     * @param evt 
     */
    private void jSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderStateChanged
        // slider state changed
        currChilds = new ArrayList<>();
        index = jSlider.getValue();
        displayImagesOnPane();
    }//GEN-LAST:event_jSliderStateChanged

    /**
     * Exports channel data of first channel in GIF format. 
     * @param evt 
     */
    private void jMenuItemExportControlsGIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportControlsGIFActionPerformed
        // export 2nd channel (control) as animated GIF
        openFileChooserAndExport(true, true);  
    }//GEN-LAST:event_jMenuItemExportControlsGIFActionPerformed

    private void jTextFieldHeatmapSensitivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHeatmapSensitivityActionPerformed
        // setHeatmap sensitivity
        try {
            heatMapSensitivity = Double.parseDouble(jTextFieldHeatmapSensitivity.getText());
        }        
        catch(Exception e) {}
    }//GEN-LAST:event_jTextFieldHeatmapSensitivityActionPerformed

    private void jMenuItemRegionOfInterestNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRegionOfInterestNewActionPerformed
        //create new region of interest
        border = new ArrayList<>();
        validRegionOfInterest = true;
        displayImagesOnPane();          
    }//GEN-LAST:event_jMenuItemRegionOfInterestNewActionPerformed

    private void jLabelImage1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImage1MouseDragged
        //mouse draged event
        if(border != null && validRegionOfInterest && maxIndex > 0) {
            double width = jLabelImage1.getWidth();

            double scale = (double)imageWidth/(double)width;
            int x = (int)(evt.getX()*scale);
            int y = (int)(evt.getY()*scale);
            
            if(x < imageWidth && y < imageWidth) {
                border.add(new Point(x, y));
            }

            displayImagesOnPane();        
        }       
    }//GEN-LAST:event_jLabelImage1MouseDragged

    private void jMenuItemRegionOfInterestRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRegionOfInterestRemoveActionPerformed
        // remove region of interest
        if(border != null && border.size() > 0) {
            border = new ArrayList<>();
            validRegionOfInterest = false;
            displayImagesOnPane();  
            if(singletonWorkerProcessImages == null) {
                singletonWorkerProcessImages = new ProcessImages();
                singletonWorkerProcessImages.execute();         
            }                   
        }
    }//GEN-LAST:event_jMenuItemRegionOfInterestRemoveActionPerformed

    private void jLabelImage1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelImage1MouseReleased
        // mouse is released
        if(validRegionOfInterest && border != null && border.size() > 0) {
            validRegionOfInterest = false;
            if(singletonWorkerProcessImages == null) {
                singletonWorkerProcessImages = new ProcessImages();
                singletonWorkerProcessImages.execute();         
            }         
        }
    }//GEN-LAST:event_jLabelImage1MouseReleased

    private void jLabelHelpWindowSizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpWindowSizeMouseEntered
        // mouse entered
        jLabelHelpWindowSize.setText("Width and height of regions in pixels.");
    }//GEN-LAST:event_jLabelHelpWindowSizeMouseEntered

    private void jLabelHelpWindowSizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpWindowSizeMouseExited
        // mouse exited
        jLabelHelpWindowSize.setText(" ? ");        
    }//GEN-LAST:event_jLabelHelpWindowSizeMouseExited

    private void jLabelHelpStrideSizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpStrideSizeMouseEntered
        // mouse enetered
        jLabelHelpStrideSize.setText("The distance between two adjacent regions in pixels.");
    }//GEN-LAST:event_jLabelHelpStrideSizeMouseEntered

    private void jLabelHelpStrideSizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpStrideSizeMouseExited
        // mouse exited
        jLabelHelpStrideSize.setText(" ? ");
    }//GEN-LAST:event_jLabelHelpStrideSizeMouseExited

    private void jLabelHelpThresholdAmplificationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpThresholdAmplificationMouseEntered
        // mouse entered
        jLabelHelpThresholdAmplification.setText("The threshold a region must exceed in order to be considered valid. (e.g. 1.4 means 40% increase in intensity)");
    }//GEN-LAST:event_jLabelHelpThresholdAmplificationMouseEntered

    private void jLabelHelpThresholdAmplificationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpThresholdAmplificationMouseExited
        // mouse exited
        jLabelHelpThresholdAmplification.setText(" ? ");        
    }//GEN-LAST:event_jLabelHelpThresholdAmplificationMouseExited

    private void jLabelHelpThresholdAttenuationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpThresholdAttenuationMouseEntered
        // mouse entered
        jLabelHelpThresholdAttenuation.setText("The threshold a region must exceed in order to be considered valid. (e.g. 0.65 means 35% decrease in intensity)");
    }//GEN-LAST:event_jLabelHelpThresholdAttenuationMouseEntered

    private void jLabelHelpThresholdAttenuationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpThresholdAttenuationMouseExited
        // mouse exited
        jLabelHelpThresholdAttenuation.setText(" ? ");        
    }//GEN-LAST:event_jLabelHelpThresholdAttenuationMouseExited

    private void jLabelHelpMaxDisplayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpMaxDisplayMouseEntered
        // mouse entered
        jLabelHelpMaxDisplay.setText("The maximum number of regions to be considered valid. If value is zero all regions are considered.");
    }//GEN-LAST:event_jLabelHelpMaxDisplayMouseEntered

    private void jLabelHelpMaxDisplayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpMaxDisplayMouseExited
        // mouse exited
        jLabelHelpMaxDisplay.setText(" ? ");        
    }//GEN-LAST:event_jLabelHelpMaxDisplayMouseExited

    private void jLabelHelpMinIntensityMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpMinIntensityMouseEntered
        // mouse entered
        jLabelHelpMinIntensity.setText("The intensity a region must exceed in order to be considered valid.");
    }//GEN-LAST:event_jLabelHelpMinIntensityMouseEntered

    private void jLabelHelpMinIntensityMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpMinIntensityMouseExited
        // mouse exited
        jLabelHelpMinIntensity.setText(" ? ");
    }//GEN-LAST:event_jLabelHelpMinIntensityMouseExited

    private void jLabelHelpheatmapResolutionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpheatmapResolutionMouseEntered
        // mouse entered
        jLabelHelpheatmapResolution.setText("The resolution of a heatmap in pixels.");
    }//GEN-LAST:event_jLabelHelpheatmapResolutionMouseEntered

    private void jLabelHelpheatmapResolutionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpheatmapResolutionMouseExited
        // mouse exited
        jLabelHelpheatmapResolution.setText(" ? ");
    }//GEN-LAST:event_jLabelHelpheatmapResolutionMouseExited

    private void jLabelHelpHeatmapSensitivityMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpHeatmapSensitivityMouseEntered
        // mouse entered
        jLabelHelpHeatmapSensitivity.setText("The maximal value of heatmap, when set to zero the value is obtained automatically based on the largest response.");
    }//GEN-LAST:event_jLabelHelpHeatmapSensitivityMouseEntered

    private void jLabelHelpHeatmapSensitivityMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpHeatmapSensitivityMouseExited
        // mouse exited
        jLabelHelpHeatmapSensitivity.setText(" ? "); 
    }//GEN-LAST:event_jLabelHelpHeatmapSensitivityMouseExited

    private void jLabelHelpFrameDifferenceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpFrameDifferenceMouseEntered
        // mouse entered
        jLabelHelpFrameDifference.setText("The number of frames between the both compared frames.");
    }//GEN-LAST:event_jLabelHelpFrameDifferenceMouseEntered

    private void jTextFieldFrameDifferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFrameDifferenceActionPerformed
        // saves the frameDifference
        try {
            frameDifference = Integer.parseInt(jTextFieldFrameDifference.getText());
        }
        catch(Exception e) {}
    }//GEN-LAST:event_jTextFieldFrameDifferenceActionPerformed

    private void jLabelHelpFrameDifferenceMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHelpFrameDifferenceMouseExited
        // mouse exited
        jLabelHelpFrameDifference.setText(" ? ");
    }//GEN-LAST:event_jLabelHelpFrameDifferenceMouseExited
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the default os look and feel if exception set Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());   
        }
        catch(Exception e) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        //break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(CaPTURE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(CaPTURE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(CaPTURE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(CaPTURE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }        
        }

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaPTURE().setVisible(true);
            }
        });
    }
    
    /**
     * Sets the defaults parameters to slider.
     */
    public void initializeSlider() {
        jSlider.setMinimum(0);
        jSlider.setMaximum(maxIndex - 1);
        jSlider.setValue(index);
    }
    
    /**
     * Displays progress on jProgressBar.
     * @param i current index 
     * @param imagesSize maximal index
     */
    public void displayProgressBar(int i, int imagesSize){
        //sets the progress bar
        int progress = (int)(i*100.0)/imagesSize;
        jProgressBar.setValue(progress);
    }
    /**
     * Calculates and returns intensity data and intensity ratio for 1st and 2nd channel.
     * @param prevG the BufferedImage for 1st channel of previous time point
     * @param currG the BufferedImage for 1st channel of current time point
     * @param prevY the BufferedImage for 2nd channel of previous time point
     * @param currY the BufferedImage for 2nd channel of current time point
     * @param x the x coordinate of region
     * @param y the y coordinate of region
     * @param winSize the size of window
     * @param rgbArray the array of predefined size for RGB colors 
     * @return double[]{ratio, intensity}, where ratio is relative change in intensity ratio and intensity is average intensity of region
     */
    public double[] getRatio(RawImage prevG, RawImage currG, RawImage prevY, RawImage currY, int x, int y, int winSize, int[] rgbArray) {
        //compare previous intensity ration with current intensity ratio        
        getIntensityValues(prevG, x, y, winSize, rgbArray);
        double prevGInt = getMedianIntensity(rgbArray);
        getIntensityValues(prevY, x, y, winSize, rgbArray);
        double prevYInt = getMedianIntensity(rgbArray);

        double prevRatio = prevGInt/prevYInt;

        getIntensityValues(currG, x, y, winSize, rgbArray);        
        double currGInt = getMedianIntensity(rgbArray);
        getIntensityValues(currY, x, y, winSize, rgbArray);  
        double currYInt = getMedianIntensity(rgbArray);

        double currRatio = currGInt/currYInt;

        double intensity = (currGInt + currYInt)/2;

        double ratio = currRatio/prevRatio;    
        return new double[]{ratio, intensity};
    }
    /**
     * Returns maximum of two given numbers.
     * @param x1
     * @param x2
     * @return maximum of given parameters
     */
    private double max(double x1, double x2) {
        if(x2 > x1) {
            return x2;
        }
        return x1;
    }
    /**
     * Calculate RGB color for heat map. Color changes from blue to red when ratio goes from 0 to maxRatio.
     * @param maxRatio the maximal ratio 
     * @param ratio the ratio to be converted to RGB color
     * @return RGB color
     */
    public int getRGB(double maxRatio, double ratio) {
        if(heatMapSensitivity != 0) {
            maxRatio = heatMapSensitivity;
        }
        double h = (maxRatio != 0) ? (ratio/maxRatio) : 0;
        double heat = h > 1.0 ? 1.0 : h;
        
        int blue = (int)max(((1 - 2*heat)*255.0), 0);
        int red = (int)max((255.0*(2*heat - 1)), 0);
        
        int green = (255 - blue - red) << 8;
        int rgb = red << 16;
        rgb = rgb | green;
        rgb = rgb | blue;
        return rgb;
    }
    
    /**
     * Disables controls when progress bar is active.
     */
    public void disableControls() {
        jProgressBar.setVisible(true);
        jButtonCancel.setVisible(true);
        jProgressBar.setValue(0);
        jButtonShowAllRegions.setEnabled(false);
        jButtonShowAverageRegions.setEnabled(false);
    }
    
    /**
     * Disables controls when progress bar is active and GIF is being exported.
     */
    public void disableControlsLoadGif() {
        jProgressBar.setVisible(true);
        jButtonCancel.setVisible(true);
        jProgressBar.setValue(0);
        jButtonShowAllRegions.setEnabled(false);
        jButtonShowAverageRegions.setEnabled(false); 
    }
    
    /**
     * Enables controls.
     */
    public void enableControls() {
        jProgressBar.setVisible(false);
        jButtonCancel.setVisible(false);
        jProgressBar.setValue(0);            
        jButtonShowAllRegions.setEnabled(true);
        jButtonShowAverageRegions.setEnabled(true);
    }
    
    /**
     * Enables controls when GIS is exported.
     */
    public void enableControlsLoadGif() {
        jProgressBar.setVisible(false);
        jButtonCancel.setVisible(false);
        jProgressBar.setValue(0);
        jButtonShowAllRegions.setEnabled(true);
        jButtonShowAverageRegions.setEnabled(true);     
    }
    
    /**
     * Draws regions on the given image.
     * @param orig the BufferedImage regions are drawn to
     * @param ind the index of given image
     */
    public void getImageWithRegions(BufferedImage orig, int ind) {
        drawRegions(ind, orig, detectedResponses);
    }
    
    private class GIFexport extends SwingWorker<Void, Object> {
        
        private boolean exportRegions;
        private boolean exportControls;
        private File file;
        
        /**
         * Creates new GIFexport.
         * @param exportRegions if true regions are exported else heat map is exported.
         * @param file the File to which data is exported
         */
        public GIFexport(boolean exportRegions, boolean exportControls,  File file) {
            this.exportRegions = exportRegions;
            this.exportControls = exportControls;
            this.file = file;
        }
        
        /**
         * Converts the RawImage to BufferedImagen
         * @param img the RawImage that is converted.
         * @param i the index of the raw image
         * @return BufferedImage
         */
        private BufferedImage getExportImage(RawImage img, int i) {
            exportImage = getBufferedImage(img, exportImage);
            getImageWithRegions(exportImage, i);    
            return exportImage;
        }
        
        /**
         * Exports selected data to selected file.
         * @param gifFile file to which data gets exported
         * @param exportRegions if true imagesWithRegions gets exported else heatMap gets exported.
         * @throws IOException 
         */
        private void exportGif(File gifFile, boolean exportRegions) throws IOException {            
            BufferedImage firstImage = exportRegions ? exportControls ? getBufferedImage(imagesY.get(0), exportImage) : getExportImage(imagesG.get(0), 0)  : heatMap.get(0); 
            if(exportRegions && !exportControls && (jCheckBoxRed.isSelected() || jCheckBoxBlue.isSelected())) {
                drawBorder(firstImage);
            }
            
            // create a new BufferedOutputStream with the last argument
            ImageOutputStream output = new FileImageOutputStream(gifFile);

            // create a gif sequence with the type of the first image, 300 miliseconds between frames
            GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), 300, true);

            // write out the first image to our sequence...
            writer.writeToSequence(firstImage);
            int size = imagesG.size();
            for(int i=1; i < size; i++) {
                if(this.isCancelled()) {
                    //close writer
                    //close output
                    writer.close();
                    output.close(); 
                    return;
                }
                exportImage = exportRegions ? exportControls ? getBufferedImage(imagesY.get(i), exportImage) : getExportImage(imagesG.get(i), i) : heatMap.get(i);
                if(exportRegions && !exportControls && (jCheckBoxRed.isSelected() || jCheckBoxBlue.isSelected())) {
                    drawBorder(exportImage);
                }
                writer.writeToSequence(exportImage);
                if(i%20 == 0) {
                    displayProgressBar(i, size);
                }
            }

            writer.close();
            output.close();
            writer = null;
            output = null;
            System.gc();
        }        
        
        /**
         * This method is called when execute method is invoked.
         * Calls exportGif method. 
         * @throws Exception 
         */
        @Override
        protected Void doInBackground() throws Exception {
            disableControlsLoadGif();           
            exportGif(file, exportRegions);
            return null;
        }
        /**
         * This method is called when doInBackground method is done or cancel method is invoked.
         */
        @Override
        protected void done() {
            try {
                if(!this.isCancelled()) {
                    if(exportRegions && !exportControls) {
                        exportRegionsGIF++;
                    }
                    else if(exportControls) {
                        exportControlsGIF++;
                    }
                    else {
                        exportHeatMapGIF++;
                    }                
                }
                enableControlsLoadGif();
                singeltonWorkerGifExport = null;
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    private class ReadLifFile extends SwingWorker<Void, Object> {

        private int id;
        private int channel1;
        private int channel2;
        private String name;
        
        private WrapedSeries imagesInSeries;
        
        /**
         * Creates new ReadLifFile.
         * @param id the id of series in .lif file
         * @param channel1 the number of 1st channel
         * @param channel2 the number of 2nd channel
         */
        public ReadLifFile(int id, int channel1, int channel2, String name) {
            super();
            this.name = name;
            this.id = id;
            this.channel1 = channel1;
            this.channel2 = channel2;
        }       
        /**
         * Getter for WrapedSeries.
         * @return imagesInSeries
         */
        public WrapedSeries getWrapedSeries() {
            return this.imagesInSeries;
        }
        /**
         * This method is called when execute method is invoked.
         * Calls getSeries method on lifReader and saves data to imagesInSeries.
         * @throws Exception 
         */
        @Override
        protected Void doInBackground() throws Exception {
            //disable or enable controls
            disableControls();

            try {
                this.imagesInSeries = lifReader.getSeries(id, CaPTURE.this, channel1, channel2);
            }catch(FormatException | IOException e) {
                System.err.println(e);
            }
            catch(Exception e) {
                System.err.println(e);                
            }
            return null;
        }
        /**
         * This method is called when doInBackground method is done or cancel method is invoked.
         * If doInBackground is not canceled executes new ProcessImages with imagesInSeries parameter 
         * in order to detect regions and calculate heat map.
         */
        @Override
        protected void done() {
            try {
                jProgressBar.setVisible(false);
                jButtonCancel.setVisible(false);
                if(this.isCancelled()) {
                    lifReader.setIsInterrupted(true);
                }
                else {
                    timePnts = imagesInSeries.getTmpnts();
                    seriesName = name;
                    exportRegionsGIF = 0;
                    exportRegionsCSV = 0;
                    exportControlsGIF = 0;
                    exportHeatMapGIF = 0;
                    timelineExportCSV = 0;
                    timeLineExportJPEG = 0;
                    singletonWorkerProcessImages = new ProcessImages(singletonWorkerReadLifFile.getWrapedSeries(), name);
                    singletonWorkerProcessImages.execute(); 
                }
                enableControls();
                singletonWorkerReadLifFile = null;             
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    private class ProcessImages extends SwingWorker<Void, Object> {
        private ArrayList<RawImage> imagesGc;
        private ArrayList<RawImage> imagesYc;                                      
        private ArrayList<String> filenames1c;
        private ArrayList<String> filenames2c;
        private ArrayList<BufferedImage> heatMapc;
        private ArrayList<ArrayList<ArrayList<Double>>> detectedResponsesc;
        private ArrayList<ImageData> imageDatac;
        private int maxIndexc;
        private String sname;
        private WrapedSeries imagesInSeries;
        
        /**
         * Creates new ProcessImages.
         */
        public ProcessImages() {
            super();
            initProcessImages();     
        }      
        /**
         * Creates new ProcessImages.
         * @param imagesInSeries the images to be processed
         */
        public ProcessImages(WrapedSeries imagesInSeries, String sname) {
            super();
            initProcessImages();
            this.sname = sname;
            this.imagesInSeries = imagesInSeries;
        }
        /**
         * Initializes temporary variables.
         */
        private void initProcessImages(){
            imagesGc = new ArrayList<>();
            imagesYc = new ArrayList<>();                                      
            filenames1c = new ArrayList<>();
            filenames2c = new ArrayList<>();
            heatMapc = new ArrayList<>();
            detectedResponsesc = new ArrayList<>();
            imageDatac = new ArrayList<>();
            setOfRegions = new HashSet<>();        
        }
        
        /**
         * This method is called when doInBackground method is done or cancel method is invoked.
         * Processes images in series. Detects regions and calculates heat map. 
         * @throws Exception 
         */
        @Override
        protected Void doInBackground() throws Exception {
            //disable or enable controls
            disableControls();
            double ratio;
            double timespan = timePnts.get(1);

            //go through all images and save them in array
            if(!imagesInBuffer) {
                imagesGc = imagesInSeries.getImgCh00();
                imagesYc = imagesInSeries.getImgCh01();                                      
                filenames1c = imagesInSeries.getNmsCh00();
                filenames2c = imagesInSeries.getNmsCh01();
                maxIndexc = filenames1c.size();
            }
            else {
                imagesGc = imagesG;
                imagesYc = imagesY;
                filenames1c = filenames1;
                filenames2c = filenames2;
                maxIndexc = maxIndex;
            }
            jProgressBar.setValue(0);
            //go through all images and remember red and blure regions
            int imagesSize = maxIndexc;
            detectedResponsesc.add(new ArrayList<>());
            int[] rgbArray = new int[windowSize*windowSize];
            double maxRatio = 0;
            for (int i = 1; i < imagesSize; i++) {
                if(Thread.currentThread().isInterrupted()){ 
                    return null;
                }
                //add coordinates like this  
                detectedResponsesc.add(new ArrayList<>());
                try {
                    if(i >= frameDifference) {
                        ratio = getRegions(imagesGc.get(i - frameDifference), imagesGc.get(i), imagesYc.get(i - frameDifference), imagesYc.get(i), detectedResponsesc.get(i), rgbArray);
                        if(ratio > maxRatio) {
                            maxRatio = ratio;
                        }
                    }
                }
                catch(Exception e) {}
                if(i%20 == 0) {
                    displayProgressBar(i, imagesSize);
                }
            }
            jProgressBar.setValue(0);
            //go through all images and save intensity graphs timeline 
            double[] ratioData;
            jProgressBar.setValue(0);
            //calculate heatMapc and save images to be displayed and exported
            int[] heatCube = new int[heatmapResolution*heatmapResolution];
            rgbArray = new int[heatmapResolution*heatmapResolution];
            for(int i = 0; i < imagesSize; i++) {
                if(Thread.currentThread().isInterrupted()){ 
                    return null;
                }
                ImageData currData = new ImageData();
                currData.setWinSize(windowSize);
                currData.setFrameId(i);
                //for every region
                currData.setRegions(new ArrayList<>());
                imageDatac.add(currData);

                int width = imagesGc.get(i).getWidth();
                int widthH = width - width%heatmapResolution;
                int height = imagesGc.get(i).getHeight();
                int heightH = height - height%heatmapResolution;
                BufferedImage heatMapcImage = new BufferedImage(widthH , heightH , BufferedImage.TYPE_INT_RGB);               

                //iterate over G and Y images and calculate heat based on max heat 
                //max value is maxRatio
                for(int x = 0; x < widthH; x = x + heatmapResolution) {
                    for(int y = 0; y < heightH; y = y + heatmapResolution) {
                        try {
                            ratioData = getRatio(imagesGc.get(i - frameDifference), imagesGc.get(i), imagesYc.get(i - frameDifference), imagesYc.get(i), x, y, heatmapResolution, rgbArray);
                        }catch(Exception e) {
                            ratioData = new double[]{0,0};
                        }
                        ratio = ratioData[0];
                        double intensity = ratioData[1];
                        if(intensity < minIntensityHeatMap) {
                            ratio = 0;
                        }
                        int rgb = getRGB(maxRatio, ratio);
                        Arrays.fill(heatCube, rgb);
                        heatMapcImage.setRGB(x, y, heatmapResolution, heatmapResolution, heatCube, 0, heatmapResolution);                       
                    }
                }              
                if(i%20 == 0) {
                    displayProgressBar(i, imagesSize);
                }
                heatMapc.add(heatMapcImage);
            }
            if (imagesSize > 0) {
                imageWidth = imagesGc.get(0).getWidth();
            }
            return null;
        }
        /**
         * This method is called when doInBackground method is done or cancel method is invoked.
         * If doInBackground is not canceled rewrites all necessary data with temporary data.
         */
        @Override
        protected void done() {
            try {  
                //index = 0;
                if(!this.isCancelled()) {
                    
                    //set label name (sname)
                    if(sname != null) {
                        seriesNameLabel.setText(sname);
                    }
                    
                    numOfValidRegions = setOfRegions.size();
                    
                    imagesG = null;
                    imagesY = null;
                    filenames1 = null;
                    filenames2 = null;
                    heatMap = null;
                    detectedResponses = null;
                    imageData = null;
                    
                    System.gc();
                    
                    imagesG = imagesGc;
                    imagesY = imagesYc;                                      
                    filenames1 = filenames1c;
                    filenames2 = filenames2c;
                    heatMap = heatMapc;
                    detectedResponses = detectedResponsesc;
                    imageData = imageDatac;
                    maxIndex = maxIndexc; 
                    
                    initializeSlider();      
                    
                }
                else {
                    imagesGc = null;
                    imagesYc = null;
                    filenames1c = null;
                    filenames2c = null;
                    heatMapc = null;
                    detectedResponsesc = null;
                    imageDatac = null;
                    
                    System.gc();
                }
                if(maxIndex > 0) {
                    imagesInBuffer = true;
                }
                
                enableControls();
                singletonWorkerProcessImages = null;
                displayImagesOnPane(); 
            } catch (Exception e) {
                System.err.println(e);
            }   
       }
    }
    
    private class AnalyseAllRegionData extends SwingWorker<Void, Object>{

        @Override
        protected Void doInBackground() throws Exception {
            //disable or enable controls
            disableControls();
            if(imageData != null && !imageData.isEmpty()) {
                int i = 0;
                //go through all ImageData objects and calculate regions
                for(ImageData currData : imageData) {
                    if(this.isCancelled()) {
                        return null;
                    }
                    if(currData.getRegions().isEmpty()) {
                        int[] rgbArray = new int[currData.getWinSize()*currData.getWinSize()];
                        ArrayList<ArrayList<Double>> regionsInFrame = detectedResponses.get(i);
                        for(ArrayList<Double> myRegion: regionsInFrame) {
                            //go through all images and save timeline
                            Region newRegion = new Region();
                            currData.getRegions().add(newRegion);
                            newRegion.setRed((myRegion.get(3) > 0.0));
                            analyseRegion(myRegion.get(0).intValue(), myRegion.get(1).intValue(), myRegion.get(4).intValue(), newRegion, imagesG, imagesY, rgbArray);
                        } 
                    } 
                    if(i%20 == 0) {
                        displayProgressBar(i, imageData.size());
                    }
                    i++;
                }         
            }            
            
            return null;
        }
        
        /**
         * This method is called when doInBackground method is done or cancel method is invoked.
         * If doInBackground is not canceled creates new TimeLineWithWindowJForm.
         */        
        @Override 
        protected void done() {
            
            
            if(!this.isCancelled()) {
                TimeLineWithWindowJForm frame = new TimeLineWithWindowJForm();
                frame.setResizable(true);
                frame.setTimePnts(timePnts);
                frame.setParent(self);
                frame.setTitle("Response timeline");
                frame.setImageData(imageData);
                frame.setNumOfValidRegions(numOfValidRegions);
                frame.setVisible(true);                     
            }
            
            enableControls();
            singletonWorkerAnalyseAllRegionData = null;
        }
    }
    
    /**
     * Iterates over all time points and analyzes specified region.
     * @param x the x coordinate of region
     * @param y the y coordinate of region
     * @param width the width of region
     * @param myRegion the Region to which data is stored
     * @param green the ArrayList of BufferedImages in 1st channel
     * @param yellow the ArrayList of BufferedImages in 2nd channel
     * @param rgbArray the int[] array of predefined size to which RGB colors are stored 
     */
    public void analyseRegion(int x, int y, int width, Region myRegion, ArrayList<RawImage> green, ArrayList<RawImage> yellow, int[] rgbArray){
        myRegion.setX(x);
        myRegion.setY(y);
        myRegion.setWinSize(width);
        
        ArrayList<Double> timepointsInteG = new ArrayList<>();
        ArrayList<Double> timepointsInteY = new ArrayList<>(); 
        ArrayList<Double> timepointsRatio = new ArrayList<>();
        
        //go through al images 
        for(int j = 0; j < green.size(); j++) {
            getIntensityValues(green.get(j), x, y, width, rgbArray);
            double currGInt = getMedianIntensity(rgbArray);
            getIntensityValues(yellow.get(j), x, y, width, rgbArray);
            double currYInt = getMedianIntensity(rgbArray);
                
            timepointsInteG.add(currGInt);
            timepointsInteY.add(currYInt);
            timepointsRatio.add(currGInt/currYInt);
        }
        myRegion.setTimepntsG(timepointsInteG);
        myRegion.setTimepntsY(timepointsInteY);
        myRegion.setTimepntsRa(timepointsRatio);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Integer> cmb1;
    private javax.swing.JComboBox<Integer> cmb2;
    private javax.swing.ButtonGroup group;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonConfirmSettings;
    private javax.swing.JButton jButtonForward;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSeriesConfirm;
    private javax.swing.JButton jButtonShowAllRegions;
    private javax.swing.JButton jButtonShowAverageRegions;
    private javax.swing.JCheckBox jCheckBoxBlue;
    private javax.swing.JCheckBox jCheckBoxRed;
    private javax.swing.JLabel jLabel1stChannel;
    private javax.swing.JLabel jLabel2ndChannel;
    private javax.swing.JLabel jLabelExperimentData;
    private javax.swing.JLabel jLabelFileName;
    private javax.swing.JLabel jLabelFrameDifference;
    private javax.swing.JLabel jLabelHeatMapSensitivity;
    private javax.swing.JLabel jLabelHeatmap;
    private javax.swing.JLabel jLabelHeatmapResolution;
    private javax.swing.JLabel jLabelHelpFrameDifference;
    private javax.swing.JLabel jLabelHelpHeatmapSensitivity;
    private javax.swing.JLabel jLabelHelpMaxDisplay;
    private javax.swing.JLabel jLabelHelpMinIntensity;
    private javax.swing.JLabel jLabelHelpStrideSize;
    private javax.swing.JLabel jLabelHelpThresholdAmplification;
    private javax.swing.JLabel jLabelHelpThresholdAttenuation;
    private javax.swing.JLabel jLabelHelpWindowSize;
    private javax.swing.JLabel jLabelHelpheatmapResolution;
    private javax.swing.JLabel jLabelImage1;
    private javax.swing.JLabel jLabelImage2;
    private javax.swing.JLabel jLabelIndex;
    private javax.swing.JLabel jLabelMaxDisplay;
    private javax.swing.JLabel jLabelMinIntensity;
    private javax.swing.JLabel jLabelStrideSize;
    private javax.swing.JLabel jLabelThresholdAmp;
    private javax.swing.JLabel jLabelThresholdAtten;
    private javax.swing.JLabel jLabelWindowSize;
    private javax.swing.JLabel jLabelXYcoordinates;
    private javax.swing.JLabel jLabeltimestamp1;
    private javax.swing.JLabel jLabeltimestamp2;
    private transient javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuExportControls;
    private javax.swing.JMenu jMenuExportHeatmap;
    private javax.swing.JMenu jMenuExportRegions;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemAnalysis;
    private javax.swing.JMenuItem jMenuItemExportControlsGIF;
    private javax.swing.JMenuItem jMenuItemExportHeatmapGIF;
    private javax.swing.JMenuItem jMenuItemExportRegionsCSV;
    private javax.swing.JMenuItem jMenuItemExportRegionsGIF;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemRegionOfInterestNew;
    private javax.swing.JMenuItem jMenuItemRegionOfInterestRemove;
    private javax.swing.JMenuItem jMenuItemSeries;
    private javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JMenu jMenuRegionOfInterest;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel1stChannel;
    private javax.swing.JPanel jPanel2ndChannel;
    private javax.swing.JPanel jPanelAnalysis;
    private javax.swing.JPanel jPanelNames;
    private javax.swing.JPanel jPanelSeries;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldFrameDifference;
    private javax.swing.JTextField jTextFieldHeatmapResolution;
    private javax.swing.JTextField jTextFieldHeatmapSensitivity;
    private javax.swing.JTextField jTextFieldMaxDisplay;
    private javax.swing.JTextField jTextFieldMinIntensity;
    private javax.swing.JTextField jTextFieldStrideSize;
    private javax.swing.JTextField jTextFieldThresholdAmp;
    private javax.swing.JTextField jTextFieldThresholdAtten;
    private javax.swing.JTextField jTextFieldWindowSize;
    private javax.swing.JLabel seriesNameLabel;
    // End of variables declaration//GEN-END:variables
}
