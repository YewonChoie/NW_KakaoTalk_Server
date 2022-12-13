package Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;

    public static Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public static void Disconnect() throws SQLException {
        con.close();
    }

    public ConnectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/kakaotalk";
            String user = "root", passwd = "1217";
            // 데이터베이스 정보

            con = DriverManager.getConnection(url, user, passwd);
            // 데이터베이스 연결
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}