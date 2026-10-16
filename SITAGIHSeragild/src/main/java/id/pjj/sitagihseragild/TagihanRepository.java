package id.pjj.sitagih.repository;

import id.pjj.sitagih.db.Database;
import id.pjj.sitagih.exception.ValidasiException;
import id.pjj.sitagih.model.StatusTagihan;
import id.pjj.sitagih.model.Tagihan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class TagihanRepository implements Repository<Tagihan> {

    private Tagihan baca(ResultSet rs) throws SQLException {
        return new Tagihan(
                rs.getInt("id"),
                rs.getString("kode_satker"),
                rs.getString("nama_satker"),
                rs.getString("nomor"),
                rs.getDate("tanggal").toLocalDate(),
                rs.getString("uraian"),
                rs.getBigDecimal("nilai"),
                StatusTagihan.valueOf(rs.getString("status")),
                rs.getString("alasan_penolakan")
        );
    }

    private String selectDasar() {
        return """
               SELECT
                   t.id,
                   t.kode_satker,
                   s.nama AS nama_satker,
                   t.nomor,
                   t.tanggal,
                   t.uraian,
                   t.nilai,
                   t.status,
                   t.alasan_penolakan
               FROM tagihan t
               JOIN satker s
                   ON s.kode = t.kode_satker
               """;
    }

    @Override
    public List<Tagihan> semua() throws ValidasiException {
        List<Tagihan> hasil = new ArrayList<>();

        String sql = selectDasar()
                + " ORDER BY t.tanggal DESC, t.nomor";

        try (
                Connection c = Database.buka();
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                hasil.add(baca(rs));
            }

            return hasil;

        } catch (SQLException e) {
            throw new ValidasiException(
                    "Gagal membaca tagihan",
                    e
            );
        }
    }

    public List<Tagihan> cari(String kata) throws ValidasiException {
        List<Tagihan> hasil = new ArrayList<>();

        String sql = selectDasar()
                + """
                   WHERE LOWER(t.nomor) LIKE ?
                      OR LOWER(t.uraian) LIKE ?
                      OR LOWER(s.nama) LIKE ?
                   ORDER BY t.tanggal DESC, t.nomor
                   """;

        try (
                Connection c = Database.buka();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {
            String parameter = "%"
                    + kata.toLowerCase()
                    + "%";

            ps.setString(1, parameter);
            ps.setString(2, parameter);
            ps.setString(3, parameter);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(baca(rs));
                }
            }

            return hasil;

        } catch (SQLException e) {
            throw new ValidasiException(
                    "Gagal mencari tagihan",
                    e
            );
        }
    }

    @Override
    public void tambah(Tagihan t) throws ValidasiException {

        String sql = """
                     INSERT INTO tagihan
                     (
                         kode_satker,
                         nomor,
                         tanggal,
                         uraian,
                         nilai,
                         status,
                         alasan_penolakan
                     )
                     VALUES (?, ?, ?, ?, ?, ?, ?)
                     """;

        try (
                Connection c = Database.buka();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, t.getKodeSatker());
            ps.setString(2, t.getNomor());

            ps.setDate(
                    3,
                    java.sql.Date.valueOf(t.getTanggal())
            );

            ps.setString(4, t.getUraian());
            ps.setBigDecimal(5, t.getNilai());
            ps.setString(6, t.getStatus().name());
            ps.setString(7, t.getAlasanPenolakan());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ValidasiException(
                    "Gagal menambah tagihan: "
                    + e.getMessage(),
                    e
            );
        }
    }

    public void ubah(Tagihan t) throws ValidasiException {

        String sql = """
                     UPDATE tagihan
                     SET
                         kode_satker = ?,
                         nomor = ?,
                         tanggal = ?,
                         uraian = ?,
                         nilai = ?
                     WHERE id = ?
                     """;

        try (
                Connection c = Database.buka();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, t.getKodeSatker());
            ps.setString(2, t.getNomor());

            ps.setDate(
                    3,
                    java.sql.Date.valueOf(t.getTanggal())
            );

            ps.setString(4, t.getUraian());
            ps.setBigDecimal(5, t.getNilai());
            ps.setInt(6, t.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ValidasiException(
                    "Gagal mengubah tagihan: "
                    + e.getMessage(),
                    e
            );
        }
    }

    public void hapus(int id) throws ValidasiException {

        String sql = """
                     DELETE FROM tagihan
                     WHERE id = ?
                     """;

        try (
                Connection c = Database.buka();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ValidasiException(
                    "Gagal menghapus tagihan",
                    e
            );
        }
    }
}