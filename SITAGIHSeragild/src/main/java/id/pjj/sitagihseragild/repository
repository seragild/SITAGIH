package id.pjj.sitagih.repository;

import id.pjj.sitagih.db.Database;
import id.pjj.sitagih.exception.ValidasiException;
import id.pjj.sitagih.model.Satker;
import java.sql.*;
import java.util.*;

public class SatkerRepository implements Repository<Satker> {
    @Override
    public List<Satker> semua() throws ValidasiException {
        List<Satker> hasil = new ArrayList<>();
        String sql = "SELECT kode,nama FROM satker ORDER BY kode";
        try (Connection c = Database.buka(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) hasil.add(new Satker(rs.getString("kode"), rs.getString("nama")));
            return hasil;
        } catch (SQLException e) { throw new ValidasiException("Gagal membaca satker", e); }
    }

    @Override
    public void tambah(Satker s) throws ValidasiException {
        if (s.getKode().isBlank() || s.getNama().isBlank())
            throw new ValidasiException("Kode dan nama satker wajib diisi");
        String sql = "INSERT INTO satker(kode,nama) VALUES(?,?)";
        try (Connection c = Database.buka(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getKode()); ps.setString(2, s.getNama()); ps.executeUpdate();
        } catch (SQLException e) { throw new ValidasiException("Gagal menambah satker: " + e.getMessage(), e); }
    }
}
