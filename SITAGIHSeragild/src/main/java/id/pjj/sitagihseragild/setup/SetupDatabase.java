/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.setup;

import id.pjj.sitagih.db.Database;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author 060111601
 */
public class SetupDatabase {

    public static void main(String[] args) {
        try {
            buatDatabase();
            buatTabelDanData();
            System.out.println("====================================");
            System.out.println("SETUP SELESAI");
            System.out.println("Database : sitagih");
            System.out.println("Tabel    : satker, tagihan, riwayat_status");
            System.out.println("====================================");
        } catch (Exception e) {
            System.out.println("SETUP GAGAL");
            e.printStackTrace();
        }
    }

    private static void buatDatabase() throws Exception {
        try (Connection c = Database.bukaServer();
             Statement st = c.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS sitagih "
                    + "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("[OK] Database sitagih siap");
        }
    }

    private static void buatTabelDanData() throws Exception {
        try (Connection c = Database.buka();
             Statement st = c.createStatement()) {

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS satker (
                    kode VARCHAR(20) PRIMARY KEY,
                    nama VARCHAR(100) NOT NULL
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tagihan (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    kode_satker VARCHAR(20) NOT NULL,
                    nomor VARCHAR(50) NOT NULL UNIQUE,
                    tanggal DATE NOT NULL,
                    uraian VARCHAR(255) NOT NULL,
                    nilai DECIMAL(18,2) NOT NULL,
                    status VARCHAR(20) NOT NULL,
                    alasan_penolakan VARCHAR(255),
                    CONSTRAINT fk_tagihan_satker
                        FOREIGN KEY (kode_satker) REFERENCES satker(kode)
                )
                """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS riwayat_status (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    tagihan_id INT NOT NULL,
                    status_lama VARCHAR(20),
                    status_baru VARCHAR(20) NOT NULL,
                    alasan VARCHAR(255),
                    waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    CONSTRAINT fk_riwayat_tagihan
                        FOREIGN KEY (tagihan_id) REFERENCES tagihan(id)
                        ON DELETE CASCADE
                )
                """);

            System.out.println("[OK] Semua tabel siap");
        }

        isiDummyJikaKosong();
    }

    private static void isiDummyJikaKosong() throws Exception {
        try (Connection c = Database.buka()) {
            int jumlah;
            try (Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM satker")) {
                rs.next();
                jumlah = rs.getInt(1);
            }
            if (jumlah > 0) {
                System.out.println("[INFO] Data dummy sudah ada, tidak ditambah lagi");
                return;
            }

            String sqlSatker = "INSERT INTO satker(kode,nama) VALUES(?,?)";
            try (PreparedStatement ps = c.prepareStatement(sqlSatker)) {
                String[][] data = {
                    {"001", "Satker Anggrek"},
                    {"002", "Satker Melati"},
                    {"003", "Satker Mawar"},
                    {"004", "Satker Kenanga"},
                    {"005", "Satker Cempaka"}
                };
                for (String[] d : data) {
                    ps.setString(1, d[0]);
                    ps.setString(2, d[1]);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String sqlTagihan = """
                INSERT INTO tagihan
                (kode_satker,nomor,tanggal,uraian,nilai,status,alasan_penolakan)
                VALUES(?,?,?,?,?,?,?)
                """;
            try (PreparedStatement ps = c.prepareStatement(sqlTagihan)) {
                Object[][] data = {
                    {"001","TAG-001","2026-10-01","Pengadaan ATK",5000000,"DIAJUKAN",null},
                    {"001","TAG-002","2026-10-02","Pemeliharaan komputer",7500000,"DIVERIFIKASI",null},
                    {"002","TAG-003","2026-10-03","Sewa kendaraan",12000000,"DISETUJUI",null},
                    {"002","TAG-004","2026-10-04","Konsumsi rapat",3500000,"DIAJUKAN",null},
                    {"003","TAG-005","2026-10-05","Perjalanan dinas",8500000,"DIVERIFIKASI",null},
                    {"003","TAG-006","2026-10-06","Pemeliharaan gedung",15000000,"DISETUJUI",null},
                    {"004","TAG-007","2026-10-07","Pengadaan printer",6000000,"DIAJUKAN",null},
                    {"004","TAG-008","2026-10-08","Honor kegiatan",4500000,"DIVERIFIKASI",null},
                    {"005","TAG-009","2026-10-09","Pengadaan meubel",10000000,"DISETUJUI",null},
                    {"005","TAG-010","2026-10-10","Pemeliharaan jaringan",5500000,"DIAJUKAN",null},
                    {"001","TAG-011","2026-10-11","Langganan internet",4250000,"DIAJUKAN",null},
                    {"003","TAG-012","2026-10-12","Bahan kegiatan",2750000,"DITOLAK","Dokumen pendukung belum lengkap"}
                };
                for (Object[] d : data) {
                    ps.setString(1, (String)d[0]);
                    ps.setString(2, (String)d[1]);
                    ps.setDate(3, java.sql.Date.valueOf((String)d[2]));
                    ps.setString(4, (String)d[3]);
                    ps.setBigDecimal(5, BigDecimal.valueOf(((Number)d[4]).longValue()));
                    ps.setString(6, (String)d[5]);
                    ps.setString(7, (String)d[6]);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            System.out.println("[OK] Data dummy dimasukkan");
        }
    }
}