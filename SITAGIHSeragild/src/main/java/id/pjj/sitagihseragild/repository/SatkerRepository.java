package id.pjj.sitagihseragild.repository;

import id.pjj.sitagihseragild.db.Database;
import id.pjj.sitagihseragild.exception.ValidasiException;
import id.pjj.sitagihseragild.model.Satker;
import java.sql.*;
import java.util.*;

public class SatkerRepository implements id.pjj.sitagihseragild.repository.Repository<id.pjj.sitagihseragild.model.Satker> {
    @Override
    public List<id.pjj.sitagihseragild.model.Satker> semua() throws id.pjj.sitagihseragild.exception.ValidasiException {
        List<id.pjj.sitagihseragild.model.Satker> hasil = new ArrayList<>();
        String sql = "SELECT kode,nama FROM satker ORDER BY kode";
        try (Connection c = id.pjj.sitagihseragild.db.Database.buka(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) hasil.add(new id.pjj.sitagihseragild.model.Satker(rs.getString("kode"), rs.getString("nama")));
            return hasil;
        } catch (SQLException e) { throw new id.pjj.sitagihseragild.exception.ValidasiException("Gagal membaca satker", e); }
    }

    @Override
    public void tambah(id.pjj.sitagihseragild.model.Satker s) throws id.pjj.sitagihseragild.exception.ValidasiException {
        if (s.getKode().isBlank() || s.getNama().isBlank())
            throw new id.pjj.sitagihseragild.exception.ValidasiException("Kode dan nama satker wajib diisi");
        String sql = "INSERT INTO satker(kode,nama) VALUES(?,?)";
        try (Connection c = id.pjj.sitagihseragild.db.Database.buka(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getKode()); ps.setString(2, s.getNama()); ps.executeUpdate();
        } catch (SQLException e) { throw new id.pjj.sitagihseragild.exception.ValidasiException("Gagal menambah satker: " + e.getMessage(), e); }
    }
}
