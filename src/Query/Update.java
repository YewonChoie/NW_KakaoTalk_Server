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

    public static void UpdateFollow(Connection con, String user, String otherUser, boolean TorF) {
        PreparedStatement pstmt = null;

        try {
            String sql = "update " + user + " set following = ? where nickname = ?";
            pstmt = con.prepareStatement(sql);

            //pstmt.setString(1, user);
            pstmt.setBoolean(1, TorF);
            pstmt.setString(2, otherUser);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "update " + otherUser + " set follower = ? where nickname = ?";
            pstmt = con.prepareStatement(sql);

            //pstmt.setString(1, otherUser);
            pstmt.setBoolean(1, TorF);
            pstmt.setString(2, user);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
