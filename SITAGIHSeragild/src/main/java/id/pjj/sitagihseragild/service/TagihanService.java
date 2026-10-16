package id.pjj.sitagihseragild.service;

import id.pjj.sitagihseragild.db.Database;
import id.pjj.sitagihseragild.exception.ValidasiException;
import id.pjj.sitagihseragild.model.*;
import java.sql.*;

public class TagihanService {

    public void validasiTagihan(Tagihan t) throws ValidasiException {
        if (t.getKodeSatker() == null || t.getKodeSatker().isBlank())
            throw new ValidasiException("Satker wajib dipilih");
        if (t.getNomor() == null || t.getNomor().isBlank())
            throw new ValidasiException("Nomor tagihan wajib diisi");
        if (t.getTanggal() == null) throw new ValidasiException("Tanggal wajib diisi");
        if (t.getUraian() == null || t.getUraian().isBlank())
            throw new ValidasiException("Uraian wajib diisi");
        if (t.getNilai() == null || t.getNilai().signum() <= 0)
            throw new ValidasiException("Nilai tagihan harus lebih dari 0");
    }

    public void ubahStatus(int id, StatusTagihan lama, StatusTagihan baru, String alasan)
            throws ValidasiException {
        boolean boleh = (lama == StatusTagihan.DIAJUKAN && baru == StatusTagihan.DIVERIFIKASI)
                || (lama == StatusTagihan.DIVERIFIKASI &&
                   (baru == StatusTagihan.DISETUJUI || baru == StatusTagihan.DITOLAK));
        if (!boleh) throw new ValidasiException("Status tidak boleh melompat dari " + lama + " ke " + baru);
        if (baru == StatusTagihan.DITOLAK && (alasan == null || alasan.isBlank()))
            throw new ValidasiException("Alasan penolakan wajib diisi");

        try (Connection c = Database.buka()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE tagihan SET status=?, alasan_penolakan=? WHERE id=?")) {
                    ps.setString(1, baru.name());
                    ps.setString(2, baru == StatusTagihan.DITOLAK ? alasan.trim() : null);
                    ps.setInt(3, id);
                    if (ps.executeUpdate() != 1) throw new SQLException("Tagihan tidak ditemukan");
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO riwayat_status(tagihan_id,status_lama,status_baru,alasan) VALUES(?,?,?,?)")) {
                    ps.setInt(1,id); ps.setString(2,lama.name()); ps.setString(3,baru.name());
                    ps.setString(4, baru == StatusTagihan.DITOLAK ? alasan.trim() : null);
                    ps.executeUpdate();
                }
                c.commit();
            } catch (Exception e) {
                c.rollback();
                if (e instanceof ValidasiException ve) throw ve;
                throw new ValidasiException("Gagal mengubah status", e);
            } finally { c.setAutoCommit(true); }
        } catch (SQLException e) { throw new ValidasiException("Gagal mengubah status", e); }
    }
}
