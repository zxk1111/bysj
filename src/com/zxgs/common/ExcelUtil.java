package com.zxgs.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 读取excel数据
	 * @param serverFile	excel绝对路径
	 * @param sheetNum	第几个excel
	 * @param valueRow 第几行开始读取
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static List<Object[]> getExcelData(String serverFile,int sheetNum,int valueRow) throws IOException{
		String fileType = serverFile.substring(serverFile.lastIndexOf(".") + 1);
		StringBuilder stringBuilder = new StringBuilder();
		InputStream xls = new FileInputStream(serverFile);
		Workbook wb = null;
		Sheet sheet = null;
		Row currentRow = null;
		Row headRow = null;
		Cell currentCell = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(xls);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(xls);
		}
		sheet = wb.getSheetAt(sheetNum);// excel中至少会存在一个sheet页
		
		int rowNum = sheet.getPhysicalNumberOfRows();// 物理有效行数
		Object[] rowValues = null;// excel中一行树木信息
		List<Object[]> models = new ArrayList<Object[]>();// excel中全部树木信息
		if (rowNum > 1) {
			headRow = sheet.getRow(0);
			columns: for (int i = valueRow; i < rowNum; i++) {
				currentRow = sheet.getRow(i);
				if (currentRow != null) {
					rowValues = new Object[50];
					// int cellNum = currentRow.getLastCellNum();// 总单元格数目
					for (short j = 0; j < 50; j++) {
						try {
							currentCell = currentRow.getCell(j);
							Object obj = null;
							if (currentCell == null) {
								obj = "";
							} else {
								switch (currentCell.getCellType()) {
									case Cell.CELL_TYPE_BLANK:
										obj = "";
										break;
									case Cell.CELL_TYPE_STRING:
										obj = currentCell.getRichStringCellValue();
										break;
									case Cell.CELL_TYPE_NUMERIC:
										if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
											double d = currentCell.getNumericCellValue();
											Date date = HSSFDateUtil.getJavaDate(d);
											obj = sdfDate.format(date);
										} else {
											NumberFormat nf = NumberFormat.getInstance();
											nf.setGroupingUsed(false);// true时的格式：1,234,567,890
											obj = nf.format(currentCell.getNumericCellValue());
										}
										break;
									default:
										obj = "";
										break;
								}
							}
							String cellVal = obj.toString();
							rowValues[j] = cellVal;
						} catch (IllegalStateException e) {
							rowValues = null;
							stringBuilder.append("第" + i + "行," + headRow.getCell(j).getRichStringCellValue() + "列输入了非法值，未导入成功！");
							continue columns;
						} catch (NullPointerException e) {
							rowValues = null;
							stringBuilder.append("第" + i + "行," + headRow.getCell(j).getRichStringCellValue() + "列输入了空值，未导入成功!");
							continue columns;
						} catch (Exception e) {
							rowValues = null;
							stringBuilder.append(e.getMessage());
							continue columns;
						}
					}
					if (rowValues != null) {
						models.add(rowValues);
					}
				}
			}
		} else if (rowNum <= 1 && rowNum > 0) {// 表示模版中只存在头部信息
			System.out.println("Excel表格中没有需要导入 的内容！");
		} else if (rowNum <= 0) {// 表示这是一个空sheet页
			System.out.println("所导入文件格式不正确，请下载模板！");
		}
		return models;
	}
}
