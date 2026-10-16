/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.setup;

import id.pjj.sitagihseragild.db.Database;
import java.sql.Connection;

/**
 *
 * @author 060111601
 */

public class CekMySQL {
    public static void main(String[] args) {
        try (Connection c = Database.bukaServer()) {
            System.out.println("BERHASIL: MySQL Server dapat dihubungi.");
            System.out.println("Host : " + Database.host());
            System.out.println("Port : " + Database.port());
            System.out.println("User : " + Database.user());
        } catch (Exception e) {
            System.out.println("GAGAL TERHUBUNG KE MYSQL");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}