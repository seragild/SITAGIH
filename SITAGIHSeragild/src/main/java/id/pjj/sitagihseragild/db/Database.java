/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Zaman PC
 */
public class Database {
    private static final Properties P = new Properties();

    static {
        try (InputStream in = Database.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties tidak ditemukan");
            }
            P.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Database() {}

    public static String host() { return P.getProperty("db.host", "localhost"); }
    public static String port() { return P.getProperty("db.port", "3306"); }
    public static String user() { return P.getProperty("db.user"); }
    public static String password() { return P.getProperty("db.password"); }

    public static Connection bukaServer() throws SQLException {
        String url = "jdbc:mysql://" + host() + ":" + port()
                + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta";
        return DriverManager.getConnection(url, user(), password());
    }

    public static Connection buka() throws SQLException {
        String url = "jdbc:mysql://" + host() + ":" + port()
                + "/sitagih?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta";
        return DriverManager.getConnection(url, user(), password());
    }
}
