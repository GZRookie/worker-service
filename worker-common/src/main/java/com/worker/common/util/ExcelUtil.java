package com.worker.common.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工具类
 */
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 创建Excel工作簿
     *
     * @param sheetName 工作表名称
     * @param headers   表头
     * @return 工作簿
     */
    public static Workbook createWorkbook(String sheetName, String[] headers) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // 创建表头行
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 15 * 256);
        }

        return workbook;
    }

    /**
     * 导出Excel到响应
     *
     * @param response  HTTP响应
     * @param workbook  工作簿
     * @param fileName  文件名
     */
    public static void exportToResponse(HttpServletResponse response, Workbook workbook, String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName + ".xlsx");

            try (OutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            LOGGER.error("导出Excel失败", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                LOGGER.error("关闭工作簿失败", e);
            }
        }
    }

    /**
     * 读取Excel文件
     *
     * @param inputStream 输入流
     * @return 数据列表
     */
    public static List<String[]> readExcel(InputStream inputStream) {
        List<String[]> dataList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount > 1) {
                Row headerRow = sheet.getRow(0);
                int cellCount = headerRow.getPhysicalNumberOfCells();

                for (int i = 1; i < rowCount; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }

                    String[] rowData = new String[cellCount];
                    boolean hasData = false;

                    for (int j = 0; j < cellCount; j++) {
                        Cell cell = row.getCell(j);
                        String cellValue = getCellValue(cell);
                        rowData[j] = cellValue;
                        if (cellValue != null && !cellValue.trim().isEmpty()) {
                            hasData = true;
                        }
                    }

                    if (hasData) {
                        dataList.add(rowData);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("读取Excel失败", e);
        }
        return dataList;
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    // 防止数字变成科学计数法
                    long longValue = (long) cell.getNumericCellValue();
                    if (cell.getNumericCellValue() == longValue) {
                        return String.valueOf(longValue);
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}