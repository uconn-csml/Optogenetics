/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAcquisition;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.io.Opener;
import ij.plugin.ImageCalculator;
import ij.plugin.filter.RankFilters;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import java.io.IOException;
import jxl.write.WriteException;

/**
 *
 * @author Bahareh Mahrou
 */
public class DataAcquisition {
    public static void main(String[] args) throws IOException, WriteException {
        
        int numOfImages = 360;                  //Total number of images that need to be processed
        double[] fI = new double[numOfImages];  //fI = Fluorescence intensity of the target cell
        double mFI;                             //mFI = mean Fluorescence intensity of the target cell
        int[] area = new int[numOfImages];      //The area of the target cell
        Opener op = new Opener();               //create an opener to open an image
        ImagePlus img = null;                   //make an image plus object to read the image

        
        
        String[] thresholdMethods = new String[4];     //an string array of 4 threshold methods
        thresholdMethods[0] = "MaxEntropy";
        thresholdMethods[1] = "Otsu";
        thresholdMethods[2] = "RenyiEntropy";
        thresholdMethods[3] = "Yen";

// ---------Read an image from file, crop region of interest containig target cell from the image
//----------process the region of interest and measure the mean fluorescence intensity.        
            for(int i = 0; i<numOfImages; i++)
            {
                String numerator = Integer.toString(i);     //images are saved in a numeric order, here an string numerator is made to read the images in an order          
                img = op.openImage("Image path" + numerator + ".tif");

                ImageProcessor im = img.getProcessor().duplicate();
                
            //set region of interest for the target cell
                im.setRoi(10,15,20,20);                             //im.setRoi(x, y, height, width);
                ImageProcessor cropedImg = im.crop();               //crop region of interest from the image
            //Image processing
                ImagePlus imp = new ImagePlus("img1.tif", cropedImg);
                IJ.setAutoThreshold(imp, thresholdMethods[1] + " dark");
                IJ.run(imp, "Convert to Mask", "");
                ImageConverter imc = new ImageConverter(imp);
                imc.convertToGray16();
                
                ImageProcessor imp0 = imp.getProcessor().duplicate();
                RankFilters outliers = new RankFilters();
                outliers.rank(imp0, 2.0, 5, 1, 50);
                
                imp0.multiply(1./255.);
                ImagePlus imp00 = new ImagePlus("img1.tif", imp0);
                
                ImageProcessor im3 = im.crop();                
                ImagePlus imp2 = new ImagePlus("img2.tif", im3);
                
                ImageCalculator ic = new ImageCalculator();
                ImagePlus res = ic.run("Multiply create", imp00, imp2);
                
            //   Measure mean fluorescence intensity
                int intensitySum = 0;
                for (int j=0; j<res.getWidth(); j++)
                {
                    for (int k=0; k<res.getHeight(); k++)
                    {
                        int[] v = res.getPixel(j, k);
                        if (v[0] != 0)
                        {
                            area[i] = area[i] + 1;
                            intensitySum = intensitySum + v[0];
                        }
                    }
                }
                if (area[i] == 0)
                {
                    mFI = 0;
                }
                else{
                    mFI = intensitySum / area[i];
                }
                
                ImageProcessor background1 = img.getProcessor().duplicate();
            //set region of interest for background 1
                background1.setRoi(548, 486, 45, 28);
                double backgroundMean1 = background1.getStatistics().mean;
        
                ImageProcessor background2 = img.getProcessor().duplicate();
            //set region of interest for background 2
                background2.setRoi(531, 525, 22, 46);
                double backgroundMean2 = background2.getStatistics().mean;
                
                ImageProcessor background3 = img.getProcessor().duplicate();
            //set region of interest for background 3
                background1.setRoi(566, 551, 38, 23);
                double backgroundMean3 = background3.getStatistics().mean;
        
                double backgroundMean = (backgroundMean1 + backgroundMean2 + backgroundMean3)/3;
                
            //subtract background intensity from mean fluorescence intensity    
                fI[i] = mFI - backgroundMean;
            
            //save the processed image to a file.
                FileSaver fs3 = new FileSaver(res);
                fs3.saveAsTiff("File path" + thresholdMethods[1] + i + ".tif");  
            }
            //write the data of fluorescence intensity and the area of the target cell to an excell file
            WriteToExcel write = new WriteToExcel();
            write.write(1, numOfImages, fI, thresholdMethods[1], area);

    }

}
