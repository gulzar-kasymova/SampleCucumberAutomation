package utilities;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JDBCTests2 {
    public static void main(String[] args) throws SQLException {

        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("HRDBURL"),
                ConfigReader.getProperty("HRDBUsername"),
                ConfigReader.getProperty("HRDBPassword")
        );

        List<Map<String, Object>> data = JDBCUtils.executeQuery("select first_name from employees");
        System.out.println(data);

        JDBCUtils.closeConnection();

        System.out.println(data);
    }
}
