package com.gryphon.rxone;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class SeedData {

    private static final String URL = "jdbc:postgresql://localhost:5432/test";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder(10);

    public static void main(String[] args) {
        seedData();
    }

    public static void seedData() {

        String orgId = UUID.randomUUID().toString();
        String orgName = "Gryphon Academy";

        String userId = UUID.randomUUID().toString();
        String userEmail = "superadmin@gryphonacademy.co.in";
        String userPassword = "password123";
        String passwordHash = passwordEncoder.encode(userPassword);

        LocalDateTime now = LocalDateTime.now();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            conn.setAutoCommit(false);

            try (PreparedStatement orgStmt = conn.prepareStatement("""
                    INSERT INTO organisations (id, name, created_at, updated_at)
                    VALUES (?, ?, ?, ?)
                    ON CONFLICT (id) DO NOTHING
                    """)) {

                orgStmt.setObject(1, UUID.fromString(orgId));
                orgStmt.setString(2, orgName);
                orgStmt.setTimestamp(3, Timestamp.valueOf(now));
                orgStmt.setTimestamp(4, Timestamp.valueOf(now));
                orgStmt.executeUpdate();

                System.out.println("Creating organisation: " + orgName);
            }

            try (PreparedStatement userStmt = conn.prepareStatement("""
                    INSERT INTO users (
                        id, name, email, role, password_hash,
                        password_provider, organisation_id, created_at, updated_at
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    ON CONFLICT (email) DO UPDATE
                    SET password_hash = EXCLUDED.password_hash,
                        role = EXCLUDED.role
                    """)) {

                userStmt.setObject(1, UUID.fromString(userId));
                userStmt.setString(2, "Super Admin");
                userStmt.setString(3, userEmail);
                userStmt.setString(4, "SUPERADMIN");
                userStmt.setString(5, passwordHash);
                userStmt.setString(6, "LOCAL");
                userStmt.setObject(7, UUID.fromString(orgId));
                userStmt.setTimestamp(8, Timestamp.valueOf(now));
                userStmt.setTimestamp(9, Timestamp.valueOf(now));

                userStmt.executeUpdate();

                System.out.println("Creating superadmin user: " + userEmail);
            }

            conn.commit();
            System.out.println("Successfully seeded Organisation and Super Admin!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}