package com.example;

import com.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDemo {
    // 数据库配置
    private static final String JDBC_URL = "jdbc:mysql://192.168.56.112:3306/testdb?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    public static void main(String[] args) {
        // 测试CRUD操作
        try {
            // 插入用户
            User newUser = new User(null, "jdbc_user", "jdbc@example.com");
            long insertedId = insertUser(newUser);
            newUser.setId(insertedId);
            System.out.println("插入用户ID: " + insertedId);

            // 查询所有用户
            List<User> users = getAllUsers();
            System.out.println("所有用户：");
            users.forEach(System.out::println);

            // 更新用户
            newUser.setEmail("updated@example.com");
            updateUser(newUser);

            // 查询单个用户
            User user = getUserById(insertedId);
            System.out.println("更新后的用户：" + user);

            // 删除用户
            deleteUser(insertedId);
            System.out.println("删除成功");

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // 数据库连接方法
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // 插入用户
    public static long insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
            return -1;
        }
    }

    // 获取所有用户
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    // 根据ID查询用户
    public static User getUserById(long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    // 更新用户
    public static void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setLong(3, user.getId());
            pstmt.executeUpdate();
        }
    }

    // 删除用户
    public static void deleteUser(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    // 结果集映射到User对象
    private static User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"));
    }

    // 异常处理方法
    private static void handleSQLException(SQLException e) {
        System.err.println("SQL错误:");
        System.err.println("错误代码: " + e.getErrorCode());
        System.err.println("SQL状态: " + e.getSQLState());
        System.err.println("错误信息: " + e.getMessage());
        e.printStackTrace();
    }
}
