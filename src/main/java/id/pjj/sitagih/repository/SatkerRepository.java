package id.pjj.sitagih.repository;

import id.pjj.sitagih.db.Database;
import id.pjj.sitagih.exception.ValidasiException;
import id.pjj.sitagih.model.Satker;
import java.sql.*;
import java.util.*;

public class SatkerRepository implements id.pjj.sitagih.repository.Repository<id.pjj.sitagih.model.Satker> {
    @Override
    public List<id.pjj.sitagih.model.Satker> semua() throws id.pjj.sitagih.exception.ValidasiException {
        List<id.pjj.sitagih.model.Satker> hasil = new ArrayList<>();
        String sql = "SELECT kode,nama FROM satker ORDER BY kode";
        try (Connection c = id.pjj.sitagih.db.Database.buka(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) hasil.add(new id.pjj.sitagih.model.Satker(rs.getString("kode"), rs.getString("nama")));
            return hasil;
        } catch (SQLException e) { throw new id.pjj.sitagih.exception.ValidasiException("Gagal membaca satker", e); }
    }

    @Override
    public void tambah(id.pjj.sitagih.model.Satker s) throws id.pjj.sitagih.exception.ValidasiException {
        if (s.getKode().isBlank() || s.getNama().isBlank())
            throw new id.pjj.sitagih.exception.ValidasiException("Kode dan nama satker wajib diisi");
        String sql = "INSERT INTO satker(kode,nama) VALUES(?,?)";
        try (Connection c = id.pjj.sitagih.db.Database.buka(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getKode()); ps.setString(2, s.getNama()); ps.executeUpdate();
        } catch (SQLException e) { throw new id.pjj.sitagih.exception.ValidasiException("Gagal menambah satker: " + e.getMessage(), e); }
    }
}
