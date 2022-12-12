package Query;

import java.sql.*;

public class Create {
    public static void CreateUserTable(ConnectDB connectDB, String nickName) {
        PreparedStatement pstmt = null;

        try {
            String sql = "CREATE TABLE instagram." + nickName + " (" +
                        "nickname VARCHAR(30) NOT NULL, " +
                        "follower TINYINT NOT NULL DEFAULT 0, " +
                        "following TINYINT NOT NULL DEFAULT 0, " +
                        "UNIQUE INDEX nickname_UNIQUE (nickname ASC))";
            pstmt = connectDB.getCon().prepareStatement(sql);

            int count = pstmt.executeUpdate();

            pstmt.close();
            connectDB.Disconnect();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
