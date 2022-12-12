package Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    public static void Logout(ConnectDB connectDB, String nickname) {
        PreparedStatement pstmt = null;

        try {
            String sql = "delete from logined where nickname = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, nickname);

            int count = pstmt.executeUpdate();

            Update.UpdateLogoutTime(connectDB, nickname);

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteFollow(Connection con, String user, String otherUser) {
        PreparedStatement pstmt = null;

        try {
            String sql = "delete from " + user + " where follower = false and following = false";
            pstmt = con.prepareStatement(sql);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "delete from " + otherUser + " where follower = false and following = false";
            pstmt = con.prepareStatement(sql);

            int count = pstmt.executeUpdate();

            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
