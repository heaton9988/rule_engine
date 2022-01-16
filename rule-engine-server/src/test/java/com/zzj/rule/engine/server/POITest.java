package com.zzj.rule.engine.server;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class POITest {
    public Map<String, String> readTableName2comment() throws Exception {
        Map<String, String> tableName2comment = Maps.newHashMap();
        List<String> lines = Files.readLines(new File("/Users/xingchuan/github/rule_engine/rule-engine-server/src/test/java/com/zzj/rule/engine/server/表名-合集.txt"), StandardCharsets.UTF_8);
        for (String line : lines) {
            String[] ss = line.trim().split("\\s+", 2);
            if (ss.length == 2) {
                tableName2comment.put(ss[0].toLowerCase(), ss[1]);
            }
        }
        return tableName2comment;
    }

    @Test
    public void generate() throws Exception {
        String filePath = "/Users/xingchuan/Desktop/资产统计表头（通用大表表头）2022.xlsx";

        Map<String, Map<String, LinkedHashSet<String>>> tableName2fieldAndType2comments = Maps.newHashMap();

        readCustomer(tableName2fieldAndType2comments, filePath, 0, "客户结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 1, "逾期结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 2, "物联网结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 3, "电催中心结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 4, "催收结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 5, "收车结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 6, "诉讼结果表");
        readCustomer(tableName2fieldAndType2comments, filePath, 7, "库存处置结果表");


        printFinalResult(tableName2fieldAndType2comments, "/Users/xingchuan/Desktop/writeTest2.xlsx");
    }

    public void readCustomer(Map<String, Map<String, LinkedHashSet<String>>> tableName2fieldAndType2comments, String filePath, int sheetIndex, String moduleName) throws Exception {
        ExcelReader reader = ExcelUtil.getReader(filePath, sheetIndex);
        List<List<Object>> arr = reader.read();

        Map<Integer, String> col2header = Maps.newHashMap();
        List<Object> row0 = arr.get(0);
        for (int col = 0; col < row0.size(); col++) {
            Object value = row0.get(col);
            col2header.put(col, value == null ? "" : value.toString());
        }

        String lastSystem = "";
        Map<String, Map<Integer, String>> system2col2field = new LinkedHashMap<>();
        Map<Integer, String> col2fieldPath = new LinkedHashMap<>();

        rowTag:
        for (int row = 1; row < arr.size(); row++) {
            List<Object> rows = arr.get(row);

            String currSystem = rows.get(0) == null ? "" : rows.get(0).toString();
            if (StringUtils.isBlank(currSystem)) continue rowTag;

            if (!Objects.equals(currSystem, lastSystem)) {
                if (StringUtils.isNotBlank(lastSystem)) {
                    system2col2field.put(lastSystem, col2fieldPath);
                    col2fieldPath = Maps.newHashMap();
                }
                lastSystem = currSystem;
            }

            for (int col = 0; col < rows.size(); col++) {
                Object value = rows.get(col);
                String str = (value == null ? "" : value.toString());

                if (row == 0) col2header.put(col, str);

                if (col >= 2) {
                    String fieldPath = col2fieldPath.get(col);
                    if (fieldPath == null) {
                        col2fieldPath.put(col, moduleName + "-" + col2header.get(col) + ":" + str);
                    } else {
                        if (str.toLowerCase(Locale.ROOT).contains("select")) {
                            str = "select.sql";
                        }
                        col2fieldPath.put(col, fieldPath + ":" + str);
                    }
                }
            }
        }
        system2col2field.put(lastSystem, col2fieldPath);

        printDbTableFields(tableName2fieldAndType2comments, system2col2field);
    }


    public void printDbTableFields(Map<String, Map<String, LinkedHashSet<String>>> tableName2fieldAndType2comments, Map<String, Map<Integer, String>> system2col2field) throws Exception {
        for (String system : system2col2field.keySet()) {
            Map<Integer, String> col2field = system2col2field.get(system);
            for (Integer colIndex : col2field.keySet()) {
                String fieldLongPath = col2field.get(colIndex);
                String[] ss = fieldLongPath.split(":", 4);
                String fieldChinese = ss[0];
                String schema = ss[1];
                String table_field = ss.length <= 2 ? "" : ss[2];
                String sss[] = table_field.split("\\.", 2);
                String tableName = sss.length > 0 ? sss[0] : "";
                String fieldEnglish = sss.length > 1 ? sss[1] : "";
                String fieldType = ss.length <= 3 ? "" : ss[3];

                String fieldAndType = fieldEnglish + " " + fieldType;
                String comment = schema + " " + fieldChinese;

                if (StringUtils.isBlank(tableName)) continue;

                tableName = tableName.toLowerCase();
                fieldAndType = fieldAndType.toLowerCase();

                Map<String, LinkedHashSet<String>> fieldAndType2comments = tableName2fieldAndType2comments.get(tableName);
                if (fieldAndType2comments == null) {
                    fieldAndType2comments = Maps.newHashMap();
                    tableName2fieldAndType2comments.put(tableName, fieldAndType2comments);
                }

                LinkedHashSet<String> comments = fieldAndType2comments.get(fieldAndType);
                if (comments == null) {
                    comments = Sets.newLinkedHashSet();
                    fieldAndType2comments.put(fieldAndType, comments);
                }
                comments.add(comment);
            }
        }
    }

    // 打印tableName2fieldAndType2comments
    private void printFinalResult(Map<String, Map<String, LinkedHashSet<String>>> tableName2fieldAndType2comments, String outputPath) throws Exception {
        Map<String, String> tableName2comment = readTableName2comment();

        List<List<String>> rows = Lists.newArrayList();
        for (String tableName : tableName2fieldAndType2comments.keySet()) {
            if (tableName.equals("默认恒信主体") || tableName.equals("select") || tableName.equals("回租") || tableName.equals("无"))
                continue;

            rows.add(Lists.newArrayList());
            rows.add(Lists.newArrayList(tableName, tableName2comment.get(tableName)));

            Map<String, LinkedHashSet<String>> fieldAndType2comments = tableName2fieldAndType2comments.get(tableName);
            for (String fieldAndType : fieldAndType2comments.keySet()) {
                LinkedHashSet<String> comments = fieldAndType2comments.get(fieldAndType);
                int index = fieldAndType.lastIndexOf(' ');
                rows.add(Lists.newArrayList(fieldAndType.substring(0, index), fieldAndType.substring(index), comments.toString().replaceAll(",", ",   ")));
            }
        }

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(outputPath);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }
}