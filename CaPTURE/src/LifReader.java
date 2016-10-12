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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import loci.formats.FormatException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import loci.formats.ImageReader;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author ziga
 */


class RawImage {
    private String LUTname;
    private byte[] data;
    private int width;
    private int height;

    public RawImage(String LUTname, byte[] data, int width, int height) {
        this.LUTname = LUTname;
        this.data = data;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter for LUTname.
     * @return the LUTname (lookup table name for color)
     */
    public String getLUTname() {
        return LUTname;
    }

    /**
     * Getter for data.
     * @return the pixel data of RawImage.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Getter for width.
     * @return the width of RawImage
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for height.
     * @return the height of RawImage.
     */
    public int getHeight() {
        return height;
    }     
}

class WrapedSeries {
    private ArrayList<RawImage> imgCh00;
    private ArrayList<RawImage> imgCh01;
    private ArrayList<String> nmsCh00;
    private ArrayList<String> nmsCh01;
    private ArrayList<Double> tmpnts;
    
    /**
     * Creates new WrapedSeries with empty ArrayList.
     */
    public WrapedSeries() {
        
        imgCh00 = new ArrayList<>();
        imgCh01 = new ArrayList<>();
        nmsCh00 = new ArrayList<>();
        nmsCh01 = new ArrayList<>();
        tmpnts = new ArrayList<>();
    }
    
    /**
     * Adds new time point data to WrapedSeries array.
     * @param img00 the BufferedImage for 1st channel
     * @param img01 the BufferedImage for 2nd channel
     * @param name1 the name of 1st BuffredImage
     * @param name2 the name of 2nd BufferedImage
     */
    public void addImageData(RawImage img00, RawImage img01, String name1, String name2) {
        imgCh00.add(img00);
        imgCh01.add(img01);
        nmsCh00.add(name1);
        nmsCh01.add(name2);     
    } 
    
    /**
     * Sets the time points array.
     * @param times the ArrayList of time points. 
     */
    public void setTmpnts(ArrayList<Double> times) {
        this.tmpnts = times;
    }
    
    /**
     * Getter for ArrayLsit time points.
     * @return tmpnts
     */
    public ArrayList<Double> getTmpnts() {
        return tmpnts;
    }
    
    /**
     * Getter for ArrayList<BufferedImage> getImgCh00.
     * @return imgCh00
     */
    public ArrayList<RawImage> getImgCh00() {
        return imgCh00;
    }

    /**
     * Getter for ArrayList<BufferedImage> getImgCh01.
     * @return imgCh01
     */
    public ArrayList<RawImage> getImgCh01() {
        return imgCh01;
    }

    /**
     * Getter for ArrayList<String> getNmsCh00.
     * @return nmsCh00
     */
    public ArrayList<String> getNmsCh00() {
        return nmsCh00;
    }

    /**
     * Getter for ArrayList<String> getNmsCh01.
     * @return 
     */
    public ArrayList<String> getNmsCh01() {
        return nmsCh01;
    }

    /**
     * Setter for ArrayList<BufferedImage> imgCh00.
     * @param imgCh00 
     */
    public void setImgCh00(ArrayList<RawImage> imgCh00) {
        this.imgCh00 = imgCh00;
    }

    /**
     * Setter for ArrayList<BufferedImage> imgCh01.
     * @param imgCh01 
     */
    public void setImgCh01(ArrayList<RawImage> imgCh01) {
        this.imgCh01 = imgCh01;
    }

    /**
     * Setter for ArrayList<String> nmsCh00.
     * @param nmsCh00 
     */
    public void setNmsCh00(ArrayList<String> nmsCh00) {
        this.nmsCh00 = nmsCh00;
    }

    /**
     * Setter for ArrayList<String> nmsCh01.
     * @param nmsCh01 
     */
    public void setNmsCh01(ArrayList<String> nmsCh01) {
        this.nmsCh01 = nmsCh01;
    }
    
    
}
 class ChannelDescription {
    private int dataType; // 0 integer, 1 float
    private int ChannelTag; // 0 gray, 1 red, 2 green, 3 blue
    private int resolution; // bits per pixel
    private String LutName; // lookup table name
    private String nameOfMeasuredQuantity; //name
    private int bytesInc; // distance from channels in bytes      
    
    /**
     * Creates new ChannelDescription with specified parameters.
     * @param dataType the data type of channel, (0 integer, 1 float)
     * @param ChannelTag the channel tag, (0 gray, 1 red, 2 green, 3 blue)
     * @param resolution the channel resolution in bits per pixel
     * @param nameOfMeasuredQuantity the name 
     * @param bytesInc the distance from first channel in bytes
     * @param LutName lookup table name for color
     */
    public ChannelDescription(int dataType, int ChannelTag, int resolution, String nameOfMeasuredQuantity, int bytesInc, String LutName) {
        this.dataType = dataType;
        this.ChannelTag = ChannelTag;
        this.resolution = resolution;
        this.nameOfMeasuredQuantity = nameOfMeasuredQuantity;
        this.bytesInc = bytesInc;
        this.LutName  = LutName;
    }    
    
    /**
     * Getter for dataType.
     * @return dataType
     */
    public int getDataType() {
        return dataType;
    }
    
    /**
     * Getter for ChannelTag.
     * @return ChannelTag
     */
    public int getChannelTag() {
        return ChannelTag;
    }
    
    /**
     * Getter for resolution.
     * @return resolution.
     */
    public int getResolution() {
        return resolution;
    }
    
    /**
     * Getter for nameOfMeasuredQuantity.
     * @return nameOfMeasuredQuantity
     */
    public String getNameOfMeasuredQuantity() {
        return nameOfMeasuredQuantity;
    }
    
    /**
     * Getter for bytesInc.
     * @return bytesInc
     */
    public int getBytesInc() {
        return bytesInc;
    }
    
    /**
     * Getter for LutName.
     * @return LutName
     */
    public String getLutName() {
        return this.LutName;
    }
    
    /**
     * Setter for dataType.
     * @param dataType 
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    
    /**
     * Setter for ChannelTag.
     * @param ChannelTag 
     */
    public void setChannelTag(int ChannelTag) {
        this.ChannelTag = ChannelTag;
    }
    
    /**
     * Setter for resolution.
     * @param resolution 
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
    /**
     * Setter for nameOfMeasuredQuantity.
     * @param nameOfMeasuredQuantity 
     */
    public void setNameOfMeasuredQuantity(String nameOfMeasuredQuantity) {
        this.nameOfMeasuredQuantity = nameOfMeasuredQuantity;
    }
    
    /**
     * Setter for bytesInc.
     * @param bytesInc 
     */
    public void setBytesInc(int bytesInc) {
        this.bytesInc = bytesInc;
    }
    
    /**
     * Setter for LutName.
     * @param LutName 
     */
    public void setLutName(String LutName) {
        this.LutName = LutName;
    }

}
class DimensionDescription {
    private int dimId; // 0 not valid, 1 x, 2 y, 3 z, 4 t as time
    private int numOfElements; // number of elements in this dimension
    private int bytesInc; // distance from one element to next in this dimension
    
    /**
     * Creates new DimensionDescription with specified parameters.
     * @param dimId the id of dimension, (0 not valid, 1 x, 2 y, 3 z, 4 t as time)
     * @param numOfElements the number of elements in this dimension
     * @param bytesInc the distance from one element to next in this dimension
     */
    public DimensionDescription(int dimId, int numOfElements, int bytesInc) {
        this.dimId = dimId;
        this.numOfElements = numOfElements;
        this.bytesInc = bytesInc;
    }

    /**
     * Getter for dimId.
     * @return dimId
     */
    public int getDimId() {
        return dimId;
    }

    /**
     * Getter for getNumOfElements.
     * @return getNumOfElements.
     */
    public int getNumOfElements() {
        return numOfElements;
    }
    
    /**
     * Getter for getBytesInc.
     * @return getBytesInc
     */
    public int getBytesInc() {
        return bytesInc;
    }
    /**
     * Setter for dimId. 
     * @param dimId 
     */
    public void setDimId(int dimId) {
        this.dimId = dimId;
    }
    
    /**
     * Setter for numOfElements.
     * @param numOfElements 
     */
    public void setNumOfElements(int numOfElements) {
        this.numOfElements = numOfElements;
    }
    
    /**
     * Setter for bytesInc.
     * @param bytesInc 
     */
    public void setBytesInc(int bytesInc) {
        this.bytesInc = bytesInc;
    }    
}

class SeriesData{
    // doesn't handle float values
    private int seriesCount;
    private String seriesName;
    private ArrayList<ChannelDescription> channels;
    private ArrayList<DimensionDescription> dimensions;
    private ArrayList<Double> timeStamp;

    /**
     * Creates new SeriesData with specified parameters.
     * @param seriesCount the number of images in this series
     * @param seriesName the name of this series
     */
    public SeriesData(int seriesCount, String seriesName) {
        this.seriesCount = seriesCount;
        this.seriesName = seriesName;
        channels = new ArrayList<>();
        dimensions = new ArrayList<>();
        timeStamp = new ArrayList<>();
    }
    
    /**
     * Getter for ArrayList<Double> timeStamp property.
     * @return timeStamp
     */
    public ArrayList<Double> getTimeStamp() {
        return timeStamp;
    }

    /**
     * Adds ChannelDescription data to ArrayList of channels.
     * @param data the channel description data
     */
    public void addChannelData(ChannelDescription data) {
        channels.add(data);
    }
    
    /**
     * Adds DimensionDescription data to ArrayList of dimensions.
     * @param data the dimension description data
     */
    public void addDimensionData(DimensionDescription data) {
        dimensions.add(data);
    }

    /**
     * Getter for seriesCount.
     * @return seriesCount
     */
    public int getSeriesCount() {
        return seriesCount;
    }

    /**
     * Getter for seriesName.
     * @return seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * Getter for ArrayList<ChannelDescription> channels.
     * @return channels
     */
    public ArrayList<ChannelDescription> getChannels() {
        return channels;
    }

    /**
     * Getter for ArrayList<DimensionDescriptio> dimensions.
     * @return dimensions
     */
    public ArrayList<DimensionDescription> getDimensions() {
        return dimensions;
    }

    /**
     * Setter for seriesCount.
     * @param seriesCount 
     */
    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    /**
     * Setter for seriesName.
     * @param seriesName 
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
    
    /**
     * Setter for ArrayList<ChannelDescription> channels.
     * @param channels 
     */
    public void setChannels(ArrayList<ChannelDescription> channels) {
        this.channels = channels;
    }

    /**
     * Setter for ArrayList<DimensionDescription> dimensions.
     * @param dimensions 
     */
    public void setDimensions(ArrayList<DimensionDescription> dimensions) {
        this.dimensions = dimensions;
    }

}
public class LifReader {
    
    private BinaryHeader header;
    private ImageReader reader;
    private String fileName;
    private boolean isInterrupted;
    private final long test0x70;
    private final int test0x2A;  
    private ArrayList<SeriesData> allSeriesData;

    /**
     * Getter for BinaryHeader header.
     * @return header
     */
    public BinaryHeader getHeader() {
        return header;
    }

    /**
     * Getter for ImageReader reader.
     * @return reader
     */
    public ImageReader getReader() {
        return reader;
    }

    /**
     * Getter for fileName.
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Getter for isInterrupted.
     * @return isInterrupted
     */
    public boolean isIsInterrupted() {
        return isInterrupted;
    }

    /**
     * Getter for getTest0x70 value.
     * @return getTest0x70
     */
    public long getTest0x70() {
        return test0x70;
    }

    /**
     * Getter for getTest0x2A value.
     * @return test0x2A
     */
    public int getTest0x2A() {
        return test0x2A;
    }

    /**
     * Getter for ArrayList allSeriesData.
     * @return allSeriesData
     */
    public ArrayList<SeriesData> getAllSeriesData() {
        return allSeriesData;
    }

    /**
     * Setter for BinaryHeader header.
     * @param header the BinaryHeader description for .lif file
     */
    public void setHeader(BinaryHeader header) {
        this.header = header;
    }
    
    /**
     * Setter for ImageReader reader.
     * @param reader the ImageReader
     */
    public void setReader(ImageReader reader) {
        this.reader = reader;
    }

    /**
     * Setter for fileName.
     * @param fileName the absolute file path of .lif file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Setter for isInterrupted.
     * @param isInterrupted whether or not getSeries method is interrupted
     */
    public void setIsInterrupted(boolean isInterrupted) {
        this.isInterrupted = isInterrupted;
    }
    
    /**
     * Setter for ArrayList allSeriesData.
     * @param allSeriesData the ArrayList of SeriesData for every detected series
     */
    public void setAllSeriesData(ArrayList<SeriesData> allSeriesData) {
        this.allSeriesData = allSeriesData;
    }
    
    
    
    private class BinaryHeader {
        private long xmlLength; // in unicode characters 
        private String xmlStructure; // version of lif file either Version 1 or Version 2
        
        /**
         * Reads the FileInputStream header and checks if specified file is .lif format. If specified file is not in .lif format, 
         * then throws new IOException("Incorrect file format") exception, else saves data description xml to xmlStructure String variable.
         * @param inputStrm the FileInputStream to be read
         * @throws IOException
         * @throws ParserConfigurationException
         * @throws SAXException 
         */
        public BinaryHeader(FileInputStream inputStrm) throws IOException, ParserConfigurationException, SAXException {
            if(test0x70 != readNextLong(inputStrm)) {
                throw new IOException("Incorrect file format");
            }
            readNextLong(inputStrm);
            if (test0x2A != readNextByte(inputStrm)) {
                throw new IOException("Incorrect file format");            
            }
            xmlLength = readNextLong(inputStrm);      
            xmlStructure = readNextString(inputStrm, (int)xmlLength);
        }
    } 
    
    /**
     * Creates new FileInputStream and checks header of selected file, if specified file is not in correct format then throws new IOException("Incorrect file format"), 
     * if the file is in correct format scans file for all image series. 
     * @param fileName the absolute path of selected file
     * @throws FileNotFoundException this exception is thrown if specified file is not found
     * @throws IOException this exception can be thrown due to use of FileInputStream object
     * @throws ParserConfigurationException this exception can be thrown due to call of populateImages method
     * @throws SAXException this exception can be thrown due to call of populateImages method
     * @throws FormatException this exception can be thrown due to call of populateImages method
     */
    public LifReader(String fileName) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, FormatException {
        isInterrupted = false;
        this.fileName = fileName;
        test0x70 = Integer.parseInt("70", 16);
        test0x2A = Integer.parseInt("2A", 16);
        
        // try to create new input stream
        FileInputStream inputStrm = new FileInputStream(fileName);
        // read header 
        header = new BinaryHeader(inputStrm);
        reader = new ImageReader();
        reader.setId(fileName);
        // get data of all images in xml document
        allSeriesData = new ArrayList<>();
        populateImages();
        
    }
    
    /**
     * Reads next long from FileInputStream.
     * @param inputStream the FileInputStream to be read
     * @return next long
     * @throws IOException the exception can be thrown due to use of FileInputStream object.
     */
    public long readNextLong(FileInputStream inputStream) throws IOException {
        byte[] buff = new byte[4];
        inputStream.read(buff);
        byte[] buff1 = new byte[4];
        buff1[0] = buff[3];
        buff1[1] = buff[2];
        buff1[2] = buff[1];
        buff1[3] = buff[0];        
        return java.nio.ByteBuffer.wrap(buff1).getInt();
    }
    
    /**
     * Reads next byte from FileInputStream.
     * @param inputStream the FileInputStream to be read
     * @return next byte
     * @throws IOException the exception can be thrown due to use of FileInputStream object
     */
    public int readNextByte(FileInputStream inputStream) throws IOException {
        return inputStream.read();
    }
    
    /**
     * Reads next String from FileInputStream, the String length is specified with charNum.
     * @param inputStream the FileInputStream to be read
     * @param charNum the String length
     * @return next String
     * @throws IOException the exception can be thrown due to use of FileInputStream object
     */
    public String readNextString(FileInputStream inputStream, int charNum) throws IOException {
        int size = charNum*2;
        byte[] buff = new byte[size];
        StringBuilder str = new StringBuilder();
        inputStream.read(buff);
        for(int i = 0; i < size; i += 2) {
            char c = (char)(buff[i+1] << 4 | buff[i]);
            str.append(c);   
        }    
        return str.toString();
    }
    
    /**
     * Creates Document from header.xmlStructure and calls a readImages method.
     * @throws ParserConfigurationException this exception can be thrown due to use of DocumentBuilder object
     * @throws IOException this exception can be thrown due to use of InputSource object
     * @throws SAXException  this exception can be thrown due to use of DocumentBuilder object
     */
    public void populateImages() throws ParserConfigurationException, IOException, SAXException {
        // parse xml data and save all image data in list images
        String xmlData = header.xmlStructure;  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlData));
        Document document = builder.parse(is);
        readImages(document.getDocumentElement());
    }
    
    /**
     * Computes the time difference in seconds between first node and second node.
     * @param first the first TimeStamp node
     * @param second the second TimeStampNode
     * @return the time interval in seconds
     */
    public double getTimeDiff(Node first, Node second) {
        long high1 = Long.parseLong(first.getAttributes().getNamedItem("HighInteger").getNodeValue());
        long low1 = Long.parseLong(first.getAttributes().getNamedItem("LowInteger").getNodeValue());
                
        long high2 = Long.parseLong(second.getAttributes().getNamedItem("HighInteger").getNodeValue());
        long low2 = Long.parseLong(second.getAttributes().getNamedItem("LowInteger").getNodeValue());
        
        long firstTime = (low1 & 0xFFFFFFFF) | (high1 << 32);
        long secondTime = (low2 & 0xFFFFFFFF) | (high2 << 32);
        
        return (double)((int)(secondTime - firstTime)/10000000.0);
    }
    
    /**
     * Fills timeStamp ArrayList with time intervals.
     * @param node the XML node
     * @param sdata the data about the current series
     */
    public void fillTimeStamps(Node node, SeriesData sdata) {
        try {
            NodeList timeStamps = ((Element)node).getElementsByTagName("TimeStampList");      
            Node currNode = timeStamps.item(0);
            NodeList allStamps = ((Element)currNode).getElementsByTagName("TimeStamp");
            ArrayList<Double> myStamps = sdata.getTimeStamp();
            myStamps.add(0.0);
            double sum = 0;
            int channels = sdata.getChannels().size();
            if(channels == 0) {
                return;
            }
            for(int j = channels; j <allStamps.getLength() ; j += channels) {
                double value = getTimeDiff(allStamps.item(j-channels), allStamps.item(j));
                sum += value;
                myStamps.add(sum);
            }
        }
        catch(Exception e) {}
    }
    
    /**
     * Recursively scans for nodes with node name "Image" and saves description data to allSeriesData variable.
     * @param node the current Node
     */
    public void readImages(Node node) {
        // for given data next node is element
        NodeList nodes = node.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++) {
            Node currNode = nodes.item(i);
            if(currNode.getNodeType() == Node.ELEMENT_NODE) {
                // element node
                if(currNode.getNodeName().equals("Image")) {
                    // we found an image                   
                    reader.setSeries(allSeriesData.size());
                    SeriesData sd = new SeriesData(reader.getSizeT(), currNode.getParentNode().getParentNode().getAttributes().getNamedItem("Name").getNodeValue());
                    Node imageDescription = ((Element)currNode).getElementsByTagName("ImageDescription").item(0);
                    NodeList channels = ((Element)(((Element)imageDescription).getElementsByTagName("Channels").item(0))).getElementsByTagName("ChannelDescription");                  
                    for(int j = 0; j < channels.getLength(); j++) {
                        Node child = channels.item(j);
                        NamedNodeMap attrs = child.getAttributes();
                        ChannelDescription des = new ChannelDescription(
                                Integer.parseInt(attrs.getNamedItem("DataType").getNodeValue()), 
                                Integer.parseInt(attrs.getNamedItem("ChannelTag").getNodeValue()), 
                                Integer.parseInt(attrs.getNamedItem("Resolution").getNodeValue()), 
                                attrs.getNamedItem("NameOfMeasuredQuantity").getNodeValue(), 
                                Integer.parseInt(attrs.getNamedItem("BytesInc").getNodeValue()), 
                                attrs.getNamedItem("LUTName").getNodeValue());
                        sd.addChannelData(des); 
                    }
                    
                    NodeList dimensions = ((Element)(((Element)imageDescription).getElementsByTagName("Dimensions").item(0))).getElementsByTagName("DimensionDescription");                          
                    
                    for(int j = 0; j < dimensions.getLength(); j++) {
                        Node child = dimensions.item(j);
                        NamedNodeMap attrs = child.getAttributes();
                        DimensionDescription dim = new DimensionDescription(
                                Integer.parseInt(attrs.getNamedItem("DimID").getNodeValue()),
                                Integer.parseInt(attrs.getNamedItem("NumberOfElements").getNodeValue()), 
                                Integer.parseInt(attrs.getNamedItem("BytesInc").getNodeValue()));
                        sd.addDimensionData(dim);
                    }
                    fillTimeStamps(currNode, sd);
                    allSeriesData.add(sd);
                }
            }
            // dive recursively in xml structure
            readImages(currNode);
        }
    }
    
    /**
     * Creates and returns RawImage.
     * @param data the pixel data for image
     * @param LUTname the color for image
     * @param xSize the width of image
     * @param ySize the height of image
     * @return new RawImage
     */
    public RawImage getRawImage(byte[] data, String LUTname, int xSize, int ySize) {
        return (new RawImage(LUTname, data, xSize, ySize));
    }

    /**
     * Reads the sequential series data specified with sindex parameter if series to be returned is in correct format, else new FormatException("Image format not supported!").
     * @param sindex the index of series to be read
     * @param scan the reference to main object, so that displayProgressBar method is called
     * @param chan1 the number of 1st channel
     * @param chan2 the number of 2nd channel
     * @return WrapedSeries mySeries
     * @throws FormatException this exception is thrown if specified series are not in correct format
     * @throws IOException this exception can be thrown due to use of ImageReader object
     */
    public WrapedSeries getSeries(int sindex, CaPTURE scan, int chan1, int chan2) throws FormatException, IOException {
        reader.setSeries(sindex); // set current series
        if(!"XYCTZ".equals(reader.getDimensionOrder()) || reader.getSizeT() < 2 || reader.getSizeC() < 2 || reader.getSizeZ() > 1) {
            // only xyczt format supported with at least two channels and at least two timepoints and at most one Z dimension
            throw new FormatException("Image format not supported!");
        }
        // load metadata from xml header
        SeriesData sData = allSeriesData.get(sindex);
        // get first two channes
        ChannelDescription ch1 = sData.getChannels().get(0);
        ChannelDescription ch2 = sData.getChannels().get(1); 
      
        if(ch1.getResolution() != 8 || ch2.getResolution() != 8 || ch1.getDataType() != 0 || ch2.getDataType() != 0) {
            // only 1 byte per pixel is currently supported with integer values
            throw new FormatException("Image format not supported!");        
        }
        WrapedSeries mySeries = new WrapedSeries();
        int seriesSize = reader.getSizeT() - 1;
        int CSize = reader.getSizeC();
        int imgSize = reader.getSizeX()*reader.getSizeY();
        byte [] buff; 
        for(int i = 0; i < seriesSize; i++) {
            if(!isInterrupted) {
                buff = reader.openBytes(i*CSize + chan1);
                RawImage img1 = getRawImage(buff, sData.getChannels().get(chan1).getLutName(), sData.getDimensions().get(0).getNumOfElements(), sData.getDimensions().get(1).getNumOfElements());
                buff = reader.openBytes(i*CSize + chan2);  
                RawImage img2 = getRawImage(buff, sData.getChannels().get(chan2).getLutName(), sData.getDimensions().get(0).getNumOfElements(), sData.getDimensions().get(1).getNumOfElements());
                mySeries.addImageData(img1, img2, "t" + Integer.toString(i) + "_ch" + Integer.toString(chan1), "t" + Integer.toString(i) + "_ch" + Integer.toString(chan2));

                if(i%20 == 0) {
                    scan.displayProgressBar(i, seriesSize);
                }
            }
            else {
                isInterrupted = false;
                return null;
            }
        }
        buff = null;
        mySeries.setTmpnts(allSeriesData.get(sindex).getTimeStamp());
        return mySeries;
    }
}
