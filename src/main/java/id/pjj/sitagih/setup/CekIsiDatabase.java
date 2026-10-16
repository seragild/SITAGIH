/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagih.setup;

import id.pjj.sitagih.db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author 060111601
 */
public class CekIsiDatabase {
    public static void main(String[] args) {
        try (Connection c = Database.buka();
             Statement st = c.createStatement()) {

            try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM satker")) {
                rs.next();
                System.out.println("Jumlah satker  : " + rs.getInt(1));
            }
            try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM tagihan")) {
                rs.next();
                System.out.println("Jumlah tagihan : " + rs.getInt(1));
            }
            try (ResultSet rs = st.executeQuery(
                    "SELECT nomor, uraian, status FROM tagihan ORDER BY nomor LIMIT 5")) {
                while (rs.next()) {
                    System.out.println(rs.getString("nomor") + " | "
                            + rs.getString("uraian") + " | " + rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}