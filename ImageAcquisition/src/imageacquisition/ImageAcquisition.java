/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageacquisition;

import mmcorej.CMMCore;
import org.micromanager.api.MultiStagePosition;
import org.micromanager.api.PositionList;

/**
 *
 * @author Bahareh Mahrou
 */
public class ImageAcquisition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        String pathToSave = "insert a path in computer to save images"; // define a desired path in the computer here to save images
        int numOfImages = 300;                                          //total number of images that needs to be taken
        int numOfPositions = 4;                                         //number of sample positions in the plate
                                
        CMMCore core = new InitializeMM().mmc();                        //create a core object from initializeMM class
        PositionList stagePosition = new InitializeMM().Position();     //create a stage position object from initializeMM class
        double[] offSet = new InitializeMM().offset();                  //create an offset object from initializeMM class
       
        for (int i = 0; i< numOfImages; i++){
        //---------------------------------------------------------------------------------------------------------------------------------------
        //            Image Acquisition
        //---------------------------------------------------------------------------------------------------------------------------------------
            for (int n = 0; n < numOfPositions; n++){
                MultiStagePosition.goToPosition(stagePosition.getPosition(n), core);
                core.setAutoFocusOffset(offSet[n]);
                new ImageSaver(pathToSave, i, n, core);                 //take an image and save it to a file using ImageSaver class
            }
            
        //---------------------------------------------------------------------------------------------------------------------------------------
        //            Blue exposure to delta-psd sample (as an example light-dark cycle for blue irradiation is 50 minutes here)
        //            in every if condition the sample is irradiated by light for one minute
        //---------------------------------------------------------------------------------------------------------------------------------------    
            if ((i >= 0 && i < 50) || (i >= 100 && i < 150) || (i >= 200 && i < 250))
            {    
                MultiStagePosition.goToPosition(stagePosition.getPosition(0 /*position of delta-psd sample that is going to be irradiated*/), core);
                //start blue irradiation by arduino
                core.setSerialPortCommand("Arduino port name", "the name of output pin in arduino, the value of light intensity (an integer number from 1 to 255)", "\n");
                Thread.sleep(60000); //imaging period is one minute so blue irradiation needs to be stoped after 1 minute for image acquisition (time unit: microsecends)
                //stop blue irradiation by arduino
                core.setSerialPortCommand("Arduino port name", "the name of output pin in arduino, the value of light intensity is zero", "\n");
                Thread.sleep(3000);
            }
        //---------------------------------------------------------------------------------------------------------------------------------------
        //            Blue exposure to psd sample
        //---------------------------------------------------------------------------------------------------------------------------------------    
            else if ((i >= 50 && i < 100) || (i >= 150 && i < 200)|| (i >= 250 && i < 300) )
            {              
                MultiStagePosition.goToPosition(stagePosition.getPosition(1/*position of psd sample that is going to be irradiated*/), core);
                //start blue irradiation by arduino
                core.setSerialPortCommand("Arduino port name", "the name of output pin in arduino, the value of light intensity (an integer number from 1 to 255)", "\n");
                Thread.sleep(60000); //imaging period is one minute so blue irradiation needs to be stoped after 1 minute for image acquisition (time unit: microsecends)
                //stop blue irradiation by arduino
                core.setSerialPortCommand("Arduino port name", "the name of output pin in arduino, the value of light intensity is zero", "\n");
                Thread.sleep(3000);
            }
            
        }
        
    }
    
}
