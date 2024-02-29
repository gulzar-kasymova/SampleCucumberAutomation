package utilities;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    public static void main(String[] args) throws IOException {


        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/TestData.xlsx"; // System.getProperty("user.dir" gives the location of the project

        try {
            FileInputStream file = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet1 = workbook.getSheet("Sheet1");
            System.out.println(sheet1.getRow(1).getCell(0).toString());

            sheet1.createRow(3).createCell(0).setCellValue("Linda");
            sheet1.getRow(3).createCell(1).setCellValue("Morgan");
            sheet1.getRow(3).createCell(2).setCellValue("lmorgan@gmail.com");
            sheet1.getRow(3).createCell(3).setCellValue("245 Lam St");

            FileOutputStream output = new FileOutputStream(path);
            workbook.write(output);

        } catch (FileNotFoundException e) {
            System.out.println("Path for the excel file is invalid");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExcelUtils.openExcelFile("TestData","Sheet1");
        System.out.println(ExcelUtils.getValue(2,0));

        ExcelUtils.setValue(3,0, "Sam");


    }
}
