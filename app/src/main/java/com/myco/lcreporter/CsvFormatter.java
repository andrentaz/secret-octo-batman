package com.myco.lcreporter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create a new CSV text file
 * Created by andre on 2/26/15.
 */
public class CsvFormatter {

    // Attributes
    private List<String> mTable;
    private int counter;

    // Constructor
    public CsvFormatter() {
        this.mTable = new ArrayList<String>();
        this.mTable.add("N, M, C, V, Nome, Telefone");
        this.counter=0;
    }

    public int getCounter() {
        return counter;
    }

    public void addRow(String row) {
        this.counter++;
        this.mTable.add(row);
    }

    public String toString() {
        String result = "";

        for (String tmp : this.mTable) {
            result += tmp;
            result += "\n";
        }

        return result;
    }

    public void erase() {
        mTable.clear();
        this.mTable.add("N, M, C, V, Nome, Telefone");
    }
}
