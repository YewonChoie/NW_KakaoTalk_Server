package Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {
    public static boolean InsertUserInfo(ConnectDB connectDB, String id, String pwd, String name, String gender, String nickname, String birth, String phone, String email) {
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        try {
            String psql = "insert into userinfo values (?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL)";
            pstmt = connectDB.getCon().prepareStatement(psql);

            pstmt.setString(1, id);
            pstmt.setString(2, String.valueOf(pwd));
            pstmt.setString(3, name);
            pstmt.setString(4, gender);
            pstmt.setString(5, nickname);
            pstmt.setString(6, birth);
            pstmt.setString(7, phone);
            pstmt.setString(8, email);
            pstmt.setString(9, "");

            int count = pstmt.executeUpdate();
            System.out.println("Updated Number Of Row: " + count);

            if (count >= 1) isSuccess = true;

            pstmt.close();
            connectDB.Disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static void InsertLoggedIn(ConnectDB connectDB, String nickname, String name, String gender, String email) {
        PreparedStatement pstmt = null;

        try {
            String psql = "insert into loggedin values (?, ?, ?, ?, NOW())";
            pstmt = connectDB.getCon().prepareStatement(psql);

            pstmt.setString(1, nickname);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setString(4, email);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertFollow(Connection con, String user, String otherUser) {
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into " + user + " values (?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            //pstmt.setString(1, user);
            pstmt.setString(1, otherUser);
            pstmt.setBoolean(2, false);
            pstmt.setBoolean(3, true);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "insert into " + otherUser + " values (?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            //pstmt.setString(1, otherUser);
            pstmt.setString(1, user);
            pstmt.setBoolean(2, true);
            pstmt.setBoolean(3, false);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UploadMessage(Connection con, String from, String to, String text) {
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into mainboard values (?, ?, ?, NOW())";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, from);
            pstmt.setString(2, to);
            pstmt.setString(3, text);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
