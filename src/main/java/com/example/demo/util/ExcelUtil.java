package com.example.demo.util;

import com.example.demo.domain.WorkDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Slf4j
@Component
public class ExcelUtil {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 각 셀의 데이터타입에 맞게 값 가져오기
    public String getCellValue(XSSFCell cell) {

        String value = "";

        if(cell == null){
            return value;
        }

        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                value = (int) cell.getNumericCellValue() + "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

    // 엑셀파일의 데이터 목록 가져오기 (파라미터들은 위에서 설명함)
    public List<WorkDetailDto> getListData(MultipartFile file, int startRowNum, int columnLength) {

        List<WorkDetailDto> excelList = new ArrayList<WorkDetailDto>();

        try {
            OPCPackage opcPackage = OPCPackage.open(file.getInputStream());

            @SuppressWarnings("resource")
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

            // 첫번째 시트
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowIndex = 0;
            int columnIndex = 0;

            // 첫번째 행(0)은 컬럼 명이기 때문에 두번째 행(1) 부터 검색
            for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);

                WorkDetailDto workDetailDto = new WorkDetailDto();

                workDetailDto.setDate(LocalDate.parse(getCellValue(row.getCell(0)), formatter));
                workDetailDto.setName(getCellValue(row.getCell(2)));
                workDetailDto.setBeginWork(getCellValue(row.getCell(7)));
                workDetailDto.setEndWork(getCellValue(row.getCell(8)));
                workDetailDto.setTotalWork(getCellValue(row.getCell(14)));
                workDetailDto.setNightWork(getCellValue(row.getCell(16)));
                workDetailDto.setHolidayWork(getCellValue(row.getCell(17)));
                workDetailDto.setLeave(getCellValue(row.getCell(23)));
                workDetailDto.setHoliday(getCellValue(row.getCell(26)));
                excelList.add(workDetailDto);
            }

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelList;
    }

}
