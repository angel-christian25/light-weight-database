import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserQuery {

    public void performQuery(Integer choice) {
        try (Scanner userInput = new Scanner(System.in)) {
            switch (choice) {
                case 1:
                    System.out.println("Enter Query:");
                    String query = userInput.nextLine();
                    String[] parts = query.split("\\s+");

                    // TRANSACTION
                    if (query.startsWith("begin transaction")) {
                        if (query.contains("commit")) {
                            String[] transactionQueryPartition = query.split(";");
                            for (int i = 0; i < transactionQueryPartition.length; i++) {
                                if (transactionQueryPartition[i].contains("create")) {
                                    createTable(transactionQueryPartition[i].split("\\s+"));
                                } else if (transactionQueryPartition[i].contains("insert")) {
                                    insertIntoTable(transactionQueryPartition[i].split("\\s+"));
                                } else if (transactionQueryPartition[i].contains("update")) {
                                    updateTable(transactionQueryPartition[i].split("\\s+"));
                                } else if (transactionQueryPartition[i].contains("delete")) {
                                    deleteTableRow(transactionQueryPartition[i].split("\\s+"));
                                } else if (transactionQueryPartition[i].contains("select")) {
                                    selctQuery(transactionQueryPartition[i].split("\\s+"));
                                }
                            }
                            System.out.println("Transaction Committed!");
                        } else if (query.contains("rollback")) {
                            System.out.println("Transaction Aborted! No change in database.");
                        }
                    }

                    // Create Command
                    else if (query.contains("create")) {
                        createTable(parts);
                    }

                    // INSERT Command
                    else if (query.contains("insert")) {
                        insertIntoTable(parts);
                    }

                    // UPDATE Command
                    else if (query.contains("update")) {
                        updateTable(parts);
                    }

                    // DELETE Command
                    else if (query.contains("delete")) {
                        deleteTableRow(parts);
                    }

                    // SELECT Command
                    else if (query.contains("select")) {
                        selctQuery(parts);
                    }
                    break;

                case 2:
                    break;
            }
        }
    }

    public void createTable(String[] query) {
        try (Scanner userInput = new Scanner(System.in)) {
            String fileName = query[2] + ".txt";

            try {
                File file = new File(fileName);
                if (file.createNewFile()) {
                    System.out.println("Table created");
                } else {
                    System.out.println("File already exists");
                }
                System.out.println("Enter table details:");
                ArrayList<String> columnNames = new ArrayList<>();
                ArrayList<String> columnDataTypes = new ArrayList<>();
                System.out.println("Press 1 when finished");
                while (true) {
                    System.out.println("add column name:");
                    String columnName = userInput.next();
                    if (columnName.matches("1")) {
                        break;
                    }
                    columnNames.add(columnName);
                    System.out.println("add column column data type");
                    String columnDataType = userInput.next();
                    if (columnDataType.matches("1")) {
                        break;
                    }
                    columnDataTypes.add(columnDataType);
                }
                addTableColumns(fileName, columnNames, columnDataTypes);
                System.out.println("Table Columns Added!");
            } catch (IOException e) {
                System.out.println("Error occured");
                e.printStackTrace();
            }
        }
    }

    public void addTableColumns(String tableName, ArrayList<String> columnNames, ArrayList<String> columnDataTypes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tableName, true))) {
            for (String columnName : columnNames) {
                writer.write(columnName + ",");
            }
            writer.newLine();
            for (String columnDataType : columnDataTypes) {
                writer.write(columnDataType + ",");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void insertIntoTable(String[] query) {
        String tableName = query[2].substring(0, query[2].indexOf("(")) + ".txt";
        String[] tableColumnsString = query[2].substring(query[2].indexOf("(") + 1, query[2].indexOf(")")).split(",");
        ArrayList<String> tableColumns = new ArrayList<>();
        for (int i = 0; i < tableColumnsString.length; i++) {
            tableColumns.add(tableColumnsString[i]);
        }
        String[] tableValues = query[3].substring(query[3].indexOf("(") + 1, query[3].indexOf(")")).split(",");
        ArrayList<String> columnValues = new ArrayList<>();
        for (int i = 0; i < tableValues.length; i++) {
            columnValues.add(tableValues[i]);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tableName, true))) {
            writer.newLine();
            for (String columnDataType : columnValues) {
                writer.write(columnDataType + ",");
            }
            System.out.println("Values added to table");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateTable(String[] query) {
        String tableName = query[1] + ".txt";
        String[] columnAndValue = query[3].split("=");
        String columnValue = columnAndValue[1];
        Integer columnValuesIndex = columnAndValue[0].matches("emp_name") ? 1 : 2;
        String[] line = query[5].split("=");
        String fileLine = line[1];

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(tableName))) {
                String tableRow;
                ArrayList<String> oldFile = new ArrayList<>();

                while ((tableRow = reader.readLine()) != null) {
                    String[] parts = tableRow.split(",");
                    if (parts[0].matches(fileLine)) {
                        String tempTableRow = tableRow;
                        String[] rowParts = tempTableRow.split(",");
                        String re = rowParts[columnValuesIndex];
                        String newString = tempTableRow.replace(re, columnValue);
                        oldFile.add(newString);
                    } else {
                        oldFile.add(tableRow);
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(tableName));
                for (String s : oldFile) {
                    writer.write(s);
                    writer.newLine();
                }
                writer.close();
            }
            System.out.println("Values updated to table");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void selctQuery(String[] query) {
        String tableName = query[3] + ".txt";
        Boolean isSelectAll = query[1].contains("*");
        Integer columnIndex = query[1].matches("emp_name") ? 1 : 2;
        String whereClauseColumnName = query.length > 4 ? query[5].split("=")[0] : null;
        String whereClauseColumnValue = query.length > 4 ? query[5].split("=")[1] : null;
        Integer whereClauseColumnIndex = query.length > 4 ? whereClauseColumnName.matches("emp_name") ? 1 : 2 : null;

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(tableName))) {
                String file;
                Integer count = 0;
                while ((file = reader.readLine()) != null) {
                    String[] fileParts = file.split(",");
                    if (count > 1) {
                        if (isSelectAll) {
                            if (query.length > 4) {
                                if (fileParts[whereClauseColumnIndex].contains(whereClauseColumnValue)) {
                                    System.out.println(file);
                                }
                            } else {
                                System.out.println(file);
                            }
                        } else {
                            if (query.length > 4) {
                                if (fileParts[whereClauseColumnIndex].contains(whereClauseColumnValue)) {
                                    System.out.println(fileParts[columnIndex]);
                                }
                            } else {
                                System.out.println(fileParts[columnIndex]);
                            }
                        }
                    }
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTableRow(String[] query) {
        String tableName = query[2] + ".txt";
        String columnName = query[4].split("=")[0];
        String columnValue = query[4].split("=")[1];
        Integer columnIndex = columnName.matches("emp_id") ? 0 : columnName.matches("emp_name") ? 1 : 2;

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(tableName))) {
                String file;
                ArrayList<String> newFile = new ArrayList<>();

                while ((file = reader.readLine()) != null) {
                    String[] fileParts = file.split(",");
                    if (!fileParts[columnIndex].contains(columnValue)) {
                        newFile.add(file);
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(tableName));
                for (String s : newFile) {
                    writer.write(s);
                    writer.newLine();
                }
                System.out.println("Row deleted");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
