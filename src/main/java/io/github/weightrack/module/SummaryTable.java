package io.github.weightrack.module;

import lombok.Data;

import java.util.List;

@Data
public class SummaryTable {
    private List<List<String>> table;
    private String tableName;
    private List<String> columnNames;

}
