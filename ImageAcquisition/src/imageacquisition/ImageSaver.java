/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageacquisition;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import mmcorej.CMMCore;
import org.micromanager.utils.ImageUtils;

/**
 *
 * @author Bahareh Mahrou
 */
public class ImageSaver {
    public ImageSaver(String path, int numerator, int folderNumber, CMMCore core){
        
        String imgNumber = Integer.toString(numerator);
        String iValue = Integer.toString(folderNumber);
        String prePathFP = path + iValue +"/" + imgNumber;                    //define a saving path for fluorescence protein
        String prePathBF = path + iValue +"/BF/" + imgNumber;                 //define a saving path for bright field image of the whole cell
        
        try{
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//             Snap and Save images
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            //capture an image from fluorescence protein using the required LED light and camera
            core.setConfig("groupName", "configName");
            core.snapImage();
            //make an array of image pixel values 
            short[] specimen = (short[])core.getImage();
            ImageProcessor cellFP = ImageUtils.makeProcessor(core, specimen); //make image processor for fluorescence protein
            // save pre-frame to TIF
            ImagePlus impFP = new ImagePlus(prePathFP, cellFP);
            FileSaver fsFP = new FileSaver(impFP);
            fsFP.saveAsTiff(prePathFP + ".tif");
            
            //capture an image from the whole cell using the bright field light and camera
            core.setConfig("groupName", "configName");
            core.snapImage();           
            //make an array of image pixel values 
            short[] specimen2 = (short[])core.getImage();
            ImageProcessor cellBF = ImageUtils.makeProcessor(core, specimen2); //make image processor for bright feild (whole cell)
            // save pre-frame to TIF
            ImagePlus impBF = new ImagePlus(prePathBF, cellBF);
            FileSaver fsBF = new FileSaver(impBF);
            fsBF.saveAsTiff(prePathBF + ".tif");
            
            
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
}
