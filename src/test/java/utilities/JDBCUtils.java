package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    /*
    .establishConnection(); -> will accept URL, Usernaame, Password as parameters. no returns
    .executeQuery(query); -> accepts query as parameter, returns data as list of maps
    .closeConnection(); -> this method will close to connected database


     Shared objects: should be global
     Connection
     Statement
     ResultSet
     */

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void establishDBConnection(String URL, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        statement = connection.createStatement();
    }

    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        // ResultSet has all data from table (rows, columns)
        resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<Map<String, Object>> table = new ArrayList<>();

        // looping through rows
        while (resultSet.next()) {
            // 1 row=1map
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <=metaData.getColumnCount(); i++) {
                //                 columnName                 value
                map.put(metaData.getColumnName(i),resultSet.getString(i));
            }
            table.add(map);


        }
        return table;
    }

    public static void closeConnection() throws SQLException{

    if(connection!=null && statement!=null && resultSet!=null){
        connection.close();
        statement.close();
        resultSet.close();
    }

}













}
