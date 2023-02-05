import java.sql.*;

public class JdbcUtil {
    public static Connection getConnection() throws SQLException
    {
        Connection connection=null;
        String url="jdbc:mysql://localhost:3306/ineuron ";
        String user="root";
        String password="root";
        connection =DriverManager.getConnection(url, user, password);
        if(connection!=null)
        {
            return connection;
        }

        return connection;
    }


    public static void closeConnection(Statement stmt, ResultSet rs, Connection conn)
    {
        try {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(rs!=null)
            {
                rs.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }
}
