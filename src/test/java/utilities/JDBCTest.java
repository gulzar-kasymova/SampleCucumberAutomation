package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTest {

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/HR_QA",
                "postgres",
                "admin"
        );
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employees");

//        resultSet.next();
//
//        System.out.println(resultSet.getString(2));
//        System.out.println(resultSet.getString("SALARY"));
//
//        resultSet.next();
//        System.out.println(resultSet.getString("FIRST_NAME"));
//
//        resultSet.next();
//        resultSet.next();
//        resultSet.next();
//        System.out.println(resultSet.getString("PHONE_NUMBER"));

        // .next(); -> method returns true if there is next line is available
        // .next(); -> method returns false if there is no data in next row;

        // ResultSetMetaData -> will provide column names
        ResultSetMetaData metaData = resultSet.getMetaData();
        System.out.println(metaData.getColumnName(5));
        System.out.println(metaData.getColumnCount());

        // print all column names
        for(int i=1; i<= metaData.getColumnCount(); i++){
            System.out.println(metaData.getColumnName(i));
        }

        List<Map<String, Object>> table = new ArrayList<>();

        // Looping through each row. If there is no next row, then next method returns false;
        while(resultSet.next()){
            Map<String,Object> map = new HashMap<>();
            // Looping through each column
            for(int i=1; i<= metaData.getColumnCount(); i++){
                // storing each cell of row into map
                map.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            table.add(map);
        }

        System.out.println(table);
        System.out.println(table.get(5).get("email"));

        System.out.println("==================");
        // Print all last names that starts with letter 'A'
        for(int i=0; i< table.size();i++){
            if(table.get(i).get("last_name").toString().startsWith("A"))
                System.out.println(table.get(i).get("last_name"));
        }

        System.out.println("============================");
        for(int i=0;i<table.size();i++){
            if(Double.parseDouble(table.get(i).get("salary").toString())>25000.0){
                System.out.println(table.get(i).get("first_name"));
                System.out.println(table.get(i).get("salary"));
                System.out.println("------------------");
            }
        }

        System.out.println("=============================");
        for(int i=0; i<table.size();i++){
            if(table.get(i).get("manager_id")!=null){
                System.out.println(table.get(i).get("employee_id") + "||" + table.get(i).get("first_name")+" "+table.get(i).get("last_name"));
            }
        }

        System.out.println("===============================");
        double sum=0;
        for(int i=0;i< table.size();i++){
            sum+=Double.parseDouble(table.get(i).get("salary").toString());
        }
        double avgSalary = sum/ table.size();
        System.out.println(avgSalary);
        for (int i=0; i< table.size(); i++){
            if(Double.parseDouble(table.get(i).get("salary").toString())>avgSalary){
                System.out.println(table.get(i).get("first_name")+" "+table.get(i).get("last_name"));
            }
        }




    }

}
