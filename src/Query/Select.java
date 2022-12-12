package Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Select {
    public static boolean isIdPossible(ConnectDB connectDB, String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            String sql = "select count(id) as result from userinfo where id = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("result") == 0) result = true;
                else result = false;
            }

            rs.close();
            pstmt.close();
            connectDB.Disconnect();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isNicknamePossible(ConnectDB connectDB, String nickname) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            String sql = "select count(nickname) as result from userinfo where nickname = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, nickname);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("result") == 0) result = true;
                else result = false;
            }

            rs.close();
            pstmt.close();
            connectDB.Disconnect();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isEmailPossible(ConnectDB connectDB, String email) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            String sql = "select count(email) as result from userinfo where email = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, email);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("result") == 0) result = true;
                else result = false;
            }

            rs.close();
            pstmt.close();
            connectDB.Disconnect();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String Login(ConnectDB connectDB, String id, String pwd) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = new String("");

        try {
            String sql = "select name, gender, nickname, email from userinfo where id = ? and pwd = ?";
            pstmt = connectDB.getCon().prepareStatement(sql);

            pstmt.setString(1, id);
            pstmt.setString(2, String.valueOf(pwd));

            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (!rs.getString("nickname").equals(null)) {
                    String nickname = rs.getString("nickname");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");

                    Insert.InsertLoggedIn(connectDB, nickname, name, gender, email);
                    Update.UpdateLoginTime(connectDB, nickname);

                    result = nickname;
                }
            }

            rs.close();
            pstmt.close();
            connectDB.Disconnect();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int TotalUserNumber(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int totalUserNumber = 0;

        try {
            String sql = "select count(id) as result from userinfo";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalUserNumber = rs.getInt("result");
            }

            rs.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return totalUserNumber;
    }

//    public static ArrayList<User> SearchUser(Connection con, String user, String nickName) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<User> searched = new ArrayList<User>();
//
//        try {
//            String sql = "select nickname from userinfo where nickname like ? and nickname != ?";
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, "%" + nickName + "%");
//            pstmt.setString(2, user);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                User newUser = new User();
//                System.out.println(rs.getString("nickname"));
//                newUser.setNickName(rs.getString("nickname"));
//                System.out.println(newUser.getNickName());
//                searched.add(newUser);
//            }
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return searched;
//    }
//
//    public static int CountFollower(Connection con, String nickName) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        int follower = 0;
//
//        try {
//            String sql = "select count(follower) as result from " + nickName + " where follower = true";
//            pstmt = con.prepareStatement(sql);
//
//            //pstmt.setString(1, nickName);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                follower = rs.getInt("result");
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return follower;
//    }
//
//    public static int CountFollowing(Connection con, String nickName) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        int following = 0;
//
//        try {
//            String sql = "select count(following) as result from " + nickName + " where following = true";
//            pstmt = con.prepareStatement(sql);
//
//            //pstmt.setString(1, nickName);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                following = rs.getInt("result");
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return following;
//    }
//
//    public static boolean isFollowExist(Connection con, String user, String otherUser) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String searched = new String("");
//        boolean isExist = false;
//
//        try {
//            String sql = "select nickname from " + user + " where nickname = ?";
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, otherUser);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                searched = rs.getString("nickname");
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (searched.equals(otherUser)) isExist = true;
//        else isExist = false;
//
//        return isExist;
//    }
//
//    public static boolean isFollowing(Connection con, String user, String otherUser) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        boolean isFollowing = false;
//
//        try {
//            String sql = "select following from " + user + " where nickname = ?";
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, otherUser);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                isFollowing = rs.getBoolean("following");
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return isFollowing;
//    }
//
//    public static ArrayList<User> SelectFollower(Connection con, String user) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<User> follower = new ArrayList<User>();
//
//        try {
//            String sql = "select nickname from " + user + " where follower = true";
//            pstmt = con.prepareStatement(sql);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                User newUser = new User();
//                newUser.setNickName(rs.getString("nickname"));
//                follower.add(newUser);
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return follower;
//    }
//
//    public static ArrayList<User> SelectFollowing(Connection con, String user) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<User> following = new ArrayList<User>();
//
//        try {
//            String sql = "select nickname from " + user + " where following = true";
//            pstmt = con.prepareStatement(sql);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                User newUser = new User();
//                newUser.setNickName(rs.getString("nickname"));
//                following.add(newUser);
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return following;
//    }
//
//    public static ArrayList<Messages> SelectAllMessages(Connection con) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<Messages> messages = new ArrayList<Messages>();
//
//        try {
//            String sql = "select who, user, text from mainboard order by upload desc";
//            pstmt = con.prepareStatement(sql);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Messages msgs = new Messages();
//                msgs.setFrom(rs.getString("who"));
//                msgs.setTo(rs.getString("user"));
//                msgs.setMessage(rs.getString("text"));
//                messages.add(msgs);
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return messages;
//    }
//
//    public static ArrayList<Messages> SelectUserMessages(Connection con, String user) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        ArrayList<Messages> messages = new ArrayList<Messages>();
//
//        try {
//            String sql = "select who, text from mainboard where user = ? order by upload desc";
//            pstmt = con.prepareStatement(sql);
//
//            pstmt.setString(1, user);
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Messages msgs = new Messages();
//                msgs.setFrom(rs.getString("who"));
//                msgs.setMessage(rs.getString("text"));
//                messages.add(msgs);
//            }
//
//            rs.close();
//            pstmt.close();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//
//        return messages;
//    }
}
