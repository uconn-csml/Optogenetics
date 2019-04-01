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
public class InitializeMM {
    
    //Create a core object from CMMCore class
    public CMMCore mmc() throws Exception
    {
        CMMCore core = new CMMCore();  
        core.loadSystemConfiguration("insert the path for system configuration that is used in MicroManager");
        //set MicroManager configuration that is used for imaging
        core.setConfig("groupName", "configName");
        return core;
    }
    
    //define a set of position list which are different positions of the stage
    //this is used to change the position of stage to the desired position during the acquisition 
    public PositionList Position(){
        PositionList positionList = new PositionList();
        //MultiStagePosition(java.lang.String xyStage, double x, double y, java.lang.String zStage, double z)
        //x,y, and z stage should be read from micromanager
        MultiStagePosition msp = new MultiStagePosition("XYStage",10000 , 10000, "TIZDrive", 1000.000);
        msp.setLabel("1st Position");
        msp.setProperty("Slide","Number 0");
        positionList.addPosition(msp);
              
        //MultiStagePosition(java.lang.String xyStage, double x, double y, java.lang.String zStage, double z)
        //x,y, and z stage should be read from micromanager
        msp = new MultiStagePosition("XYStage",20000 , 20000, "TIZDrive", 1000.000);        
        msp.setLabel("2nd Position");
        msp.setProperty("Slide", "Number 1");
        positionList.addPosition(msp);
        
        //MultiStagePosition(java.lang.String xyStage, double x, double y, java.lang.String zStage, double z)
        //x,y, and z stage should be read from micromanager
        msp = new MultiStagePosition("XYStage",20000 , 20000, "TIZDrive", 1000.000);        
        msp.setLabel("3rd Position");
        msp.setProperty("Slide", "Number 2");
        positionList.addPosition(msp);
        
        //MultiStagePosition(java.lang.String xyStage, double x, double y, java.lang.String zStage, double z)
        //x,y, and z stage should be read from micromanager
        msp = new MultiStagePosition("XYStage",20000 , 20000, "TIZDrive", 1000.000);        
        msp.setLabel("4rth Position");
        msp.setProperty("Slide", "Number 3");
        positionList.addPosition(msp);
        
        return positionList;
    }
    
    //define stage offset for auto focus
    public double[] offset(){
        double offSet0 = 375; //should read and replace here from micromanager 
        double offSet1 = 375; //should read and replace here from micromanager
        double offSet2 = 375; //should read and replace here from micromanager
        double offSet3 = 375; //should read and replace here from micromanager
        double[] offSet = {offSet0, offSet1, offSet2, offSet3};
        
        return offSet;
    }    
}

