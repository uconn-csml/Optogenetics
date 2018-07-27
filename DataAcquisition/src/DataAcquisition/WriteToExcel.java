/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAcquisition;

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Bahareh Mahrou
 */
public class WriteToExcel {
    public void write(int col, int row, double[] data, String methods, int[] area) throws IOException, WriteException{
        File f = new File("excel file path" + methods +"file name.xls");
        WritableWorkbook myexel = Workbook.createWorkbook(f);
        WritableSheet mysheet = myexel.createSheet("mySheet", 0);
        
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++){
                String stringData = Double.toString(data[j]);
                String stringArea = Double.toString(area[j]);
                Label l1 = new Label(1, j, stringData);
                Label l2 = new Label(2, j, stringArea);
                mysheet.addCell(l1);
                mysheet.addCell(l2);
            }
        }
        myexel.write();
        myexel.close();
    }
}
