package com.weibo.keeplooking.tryout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Help my wife process CSV data files.
 * 
 * @author Johnny
 *
 */
public class CsvDataProcess {

    @Test
    public void process() throws IOException {
        /* 读原始文件 */
        System.out.println("Loading source file...");
        List<String[]> srcRows = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(
                        "D:/git/python-toolbox/data/xizang_src.csv"), "GBK"));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = line + ",-1";
            String[] rowCells = line.split(",");
            Assert.assertTrue(rowCells.length == 9 * 26 + 1);
            srcRows.add(rowCells);
        }
        br.close();
        System.out.println("Source file loaded, total rows: " + srcRows.size());

        /* 数据处理 */
        List<String[]> destRows = new ArrayList<String[]>();
        for (String[] srcRow : srcRows) {
            for (int memIndex = 0; memIndex < 9; memIndex++) { // 9个成员，每个成员一行
                String[] destRow = new String[9 + 25]; // 每行列数为9个户属性+25个成员属性
                int srcRowIndex = 0;
                int destRowIndex = 0;

                /* 9个户属性 */
                for (destRowIndex = 0; destRowIndex < 9; destRowIndex++) {
                    destRow[destRowIndex] = srcRow[destRowIndex];
                }

                Assert.assertTrue(destRowIndex == 9);

                /* 25个成员属性 */
                int cellCount = srcRow.length - 1;
                Assert.assertTrue(cellCount == 9 * 26);
                srcRowIndex = 9 + memIndex;
                while (srcRowIndex < cellCount) {
                    destRow[destRowIndex] = srcRow[srcRowIndex];
                    destRowIndex++;
                    srcRowIndex = srcRowIndex + 9;
                }
                destRows.add(destRow);
                Assert.assertTrue(destRow.length == 9 + 25);
            }
        }

        /* 写目标文件 */
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(
                        "D:/git/python-toolbox/data/xizang_dest.csv"), "GBK"));
        for (String[] destRow : destRows) {
            StringBuilder sb = new StringBuilder();
            for (String cell : destRow) {
                sb.append(cell);
                sb.append(",");
            }
            line = sb.toString();
            line = line.substring(0, line.length());
            pw.println(line);
        }
        pw.close();
    }

}
