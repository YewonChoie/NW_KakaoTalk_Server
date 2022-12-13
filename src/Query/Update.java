package Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public static void UpdateLoginTime(ConnectDB connectDB, String nickName) {
        PreparedStatement pstmt = null;

        try {
            String sql = "update userinfo set login = (select login from loggedin where nickname = ?), logout = NULL where nickname = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, nickName);
            pstmt.setString(2, nickName);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateLogoutTime(ConnectDB connectDB, String nickName) {
        PreparedStatement pstmt = null;

        try {
            String sql = "update userinfo set logout = NOW() where nickname = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, nickName);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
