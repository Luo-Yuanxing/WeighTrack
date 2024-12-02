package io.github.weightrack.module;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SummaryTable {
    private List<List<String>> table;
    private String tableName;
    private List<String> columnNames;

    public SummaryTable() {
        this.table = new ArrayList<>();
    }

    public void addRow(List<String> row) {
        table.add(row);
    }

    public String getCell(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < table.size() && columnIndex >= 0 && columnIndex < table.get(rowIndex).size()) {
            return table.get(rowIndex).get(columnIndex);
        }
        return null;
    }

    public void setCell(int row, String columnName, String value) {
        if (row < 0 || row >= table.size()) {
            return;
        }
        for (int i = 0; i < columnNames.size(); i++) {
            if (columnNames.get(i).equals(columnName)) {
                table.get(row).set(i, value);
            }
        }
    }

    public void printTable() {
        for (List<String> row : table) {
            System.out.println(row);
        }
    }
}
