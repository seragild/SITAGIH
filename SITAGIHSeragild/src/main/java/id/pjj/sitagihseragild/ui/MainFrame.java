/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.ui;

/**
 *
 * @author Zaman PC
 */
import id.pjj.sitagihseragild.exception.ValidasiException;
import id.pjj.sitagihseragild.model.RekapStatus;
import id.pjj.sitagihseragild.model.Satker;
import id.pjj.sitagihseragild.model.StatusTagihan;
import id.pjj.sitagihseragild.model.Tagihan;
import id.pjj.sitagihseragild.repository.Repository;
import id.pjj.sitagihseragild.repository.SatkerRepository;
import id.pjj.sitagihseragild.repository.TagihanRepository;
import id.pjj.sitagihseragild.service.RekapService;
import id.pjj.sitagihseragild.service.TagihanService;
import id.pjj.sitagihseragild.util.CsvExporter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

    private final TagihanRepository tagihanRepo =
            new TagihanRepository();

    // Contoh polimorfisme melalui interface
    private final Repository<Tagihan> sumberTagihan =
            tagihanRepo;

    private final SatkerRepository satkerRepo =
            new SatkerRepository();

    private final TagihanService service =
            new TagihanService();

    private final RekapService rekapService =
            new RekapService();

    private final JTextField cari =
            new JTextField(20);

    private final DefaultTableModel model =
            new DefaultTableModel(
                    new Object[]{
                        "ID",
                        "Nomor",
                        "Satker",
                        "Tanggal",
                        "Uraian",
                        "Nilai",
                        "Status"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(
                        int baris,
                        int kolom) {
                    return false;
                }
            };

    private final JTable tabel =
            new JTable(model);

    private java.util.List<Tagihan> data =
            new ArrayList<>();


    public MainFrame() {

        super("SITAGIH - Sistem Penatausahaan Tagihan");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(1050, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(6, 6));

        // ==========================
        // BAGIAN ATAS
        // ==========================

        JPanel atas =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        JButton bCari =
                new JButton("Cari");

        JButton bSemua =
                new JButton("Semua");

        atas.add(new JLabel("Cari:"));
        atas.add(cari);
        atas.add(bCari);
        atas.add(bSemua);

        add(atas, BorderLayout.NORTH);

        // ==========================
        // TABEL
        // ==========================

        add(
                new JScrollPane(tabel),
                BorderLayout.CENTER
        );

        // ==========================
        // BAGIAN BAWAH
        // ==========================

        JPanel bawah =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        String[] namaTombol = {
            "Tambah",
            "Ubah",
            "Hapus",
            "Tambah Satker",
            "Verifikasi",
            "Setujui",
            "Tolak",
            "Rekap",
            "Ekspor CSV"
        };

        JButton[] tombol =
                new JButton[namaTombol.length];

        for (int i = 0;
             i < namaTombol.length;
             i++) {

            tombol[i] =
                    new JButton(namaTombol[i]);

            bawah.add(tombol[i]);
        }

        add(bawah, BorderLayout.SOUTH);

        // ==========================
        // EVENT TOMBOL
        // ==========================

        bCari.addActionListener(
                e -> jalankan(
                        () -> muat(
                                tagihanRepo.cari(
                                        cari.getText()
                                                .trim()
                                )
                        )
                )
        );

        bSemua.addActionListener(
                e -> jalankan(
                        this::muatSemua
                )
        );

        tombol[0].addActionListener(
                e -> formTambah()
        );

        tombol[1].addActionListener(
                e -> formUbah()
        );

        tombol[2].addActionListener(
                e -> hapus()
        );

        tombol[3].addActionListener(
                e -> tambahSatker()
        );

        tombol[4].addActionListener(
                e -> ubahStatus(
                        StatusTagihan.DIVERIFIKASI
                )
        );

        tombol[5].addActionListener(
                e -> ubahStatus(
                        StatusTagihan.DISETUJUI
                )
        );

        tombol[6].addActionListener(
                e -> ubahStatus(
                        StatusTagihan.DITOLAK
                )
        );

        tombol[7].addActionListener(
                e -> tampilkanRekap()
        );

        tombol[8].addActionListener(
                e -> ekspor()
        );

        // Muat data pertama kali
        jalankan(this::muatSemua);
    }


    // ==============================
    // HELPER PENANGANAN ERROR
    // ==============================

    interface Aksi {
        void jalan() throws Exception;
    }

    private void jalankan(Aksi a) {

        try {

            a.jalan();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==============================
    // MUAT DATA
    // ==============================

    private void muatSemua()
            throws Exception {

        muat(
                sumberTagihan.semua()
        );
    }


    private void muat(
            java.util.List<Tagihan> daftar) {

        data = daftar;

        model.setRowCount(0);

        for (Tagihan t : daftar) {

            model.addRow(
                    new Object[]{
                        t.getId(),
                        t.getNomor(),
                        t.getNamaSatker(),
                        t.getTanggal(),
                        t.getUraian(),
                        t.getNilai(),
                        t.getStatus()
                    }
            );
        }
    }


    // ==============================
    // TAGIHAN TERPILIH
    // ==============================

    private Tagihan terpilih()
            throws ValidasiException {

        int baris =
                tabel.getSelectedRow();

        if (baris < 0) {

            throw new ValidasiException(
                    "Pilih satu tagihan di tabel"
            );
        }

        int barisModel =
                tabel.convertRowIndexToModel(
                        baris
                );

        return data.get(barisModel);
    }


    // ==============================
    // TAMBAH TAGIHAN
    // ==============================

    private void formTambah() {

        jalankan(() -> {

            Tagihan t = form(null);

            if (t == null) {
                return;
            }

            service.validasiTagihan(t);

            sumberTagihan.tambah(t);

            muatSemua();
        });
    }


    // ==============================
    // UBAH TAGIHAN
    // ==============================

    private void formUbah() {

        jalankan(() -> {

            Tagihan lama =
                    terpilih();

            Tagihan t =
                    form(lama);

            if (t == null) {
                return;
            }

            service.validasiTagihan(t);

            tagihanRepo.ubah(t);

            muatSemua();
        });
    }


    // ==============================
    // FORM TAGIHAN
    // ==============================

    private Tagihan form(Tagihan lama)
            throws Exception {

        java.util.List<Satker> daftarSatker =
                satkerRepo.semua();

        if (daftarSatker.isEmpty()) {

            throw new ValidasiException(
                    "Belum ada data satker. Tambahkan satker terlebih dahulu."
            );
        }

        JComboBox<Satker> cbSatker =
                new JComboBox<>(
                        daftarSatker.toArray(
                                new Satker[0]
                        )
                );

        JTextField no =
                new JTextField(
                        lama == null
                        ? ""
                        : lama.getNomor()
                );

        JTextField tgl =
                new JTextField(
                        lama == null
                        ? LocalDate.now().toString()
                        : lama.getTanggal().toString()
                );

        JTextField uraian =
                new JTextField(
                        lama == null
                        ? ""
                        : lama.getUraian()
                );

        JTextField nilai =
                new JTextField(
                        lama == null
                        ? ""
                        : lama.getNilai()
                                .toPlainString()
                );

        if (lama != null) {

            for (int i = 0;
                 i < cbSatker.getItemCount();
                 i++) {

                if (cbSatker
                        .getItemAt(i)
                        .getKode()
                        .equals(
                                lama.getKodeSatker()
                        )) {

                    cbSatker
                            .setSelectedIndex(i);

                    break;
                }
            }
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                5,
                                5
                        )
                );

        panel.add(
                new JLabel("Satker")
        );
        panel.add(cbSatker);

        panel.add(
                new JLabel("Nomor")
        );
        panel.add(no);

        panel.add(
                new JLabel(
                        "Tanggal yyyy-mm-dd"
                )
        );
        panel.add(tgl);

        panel.add(
                new JLabel("Uraian")
        );
        panel.add(uraian);

        panel.add(
                new JLabel("Nilai")
        );
        panel.add(nilai);

        int jawaban =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        lama == null
                        ? "Tambah Tagihan"
                        : "Ubah Tagihan",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (jawaban
                != JOptionPane.OK_OPTION) {

            return null;
        }

        Satker satker =
                (Satker)
                cbSatker.getSelectedItem();

        return new Tagihan(
                lama == null
                ? 0
                : lama.getId(),

                satker.getKode(),

                satker.getNama(),

                no.getText().trim(),

                LocalDate.parse(
                        tgl.getText().trim()
                ),

                uraian.getText().trim(),

                new BigDecimal(
                        nilai.getText().trim()
                ),

                lama == null
                ? StatusTagihan.DIAJUKAN
                : lama.getStatus(),

                lama == null
                ? null
                : lama.getAlasanPenolakan()
        );
    }


    // ==============================
    // HAPUS TAGIHAN
    // ==============================

    private void hapus() {

        jalankan(() -> {

            Tagihan t =
                    terpilih();

            int jawaban =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Hapus "
                            + t.getNomor()
                            + "?",
                            "Konfirmasi",
                            JOptionPane.YES_NO_OPTION
                    );

            if (jawaban
                    == JOptionPane.YES_OPTION) {

                tagihanRepo.hapus(
                        t.getId()
                );

                muatSemua();
            }
        });
    }


    // ==============================
    // TAMBAH SATKER
    // ==============================

    private void tambahSatker() {

        jalankan(() -> {

            JTextField kode =
                    new JTextField();

            JTextField nama =
                    new JTextField();

            JPanel panel =
                    new JPanel(
                            new GridLayout(
                                    0,
                                    2,
                                    5,
                                    5
                            )
                    );

            panel.add(
                    new JLabel("Kode")
            );
            panel.add(kode);

            panel.add(
                    new JLabel("Nama")
            );
            panel.add(nama);

            int jawaban =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Tambah Satker",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (jawaban
                    == JOptionPane.OK_OPTION) {

                satkerRepo.tambah(
                        new Satker(
                                kode.getText()
                                        .trim(),

                                nama.getText()
                                        .trim()
                        )
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Satker berhasil ditambahkan."
                );
            }
        });
    }


    // ==============================
    // UBAH STATUS
    // ==============================

    private void ubahStatus(
            StatusTagihan statusBaru) {

        jalankan(() -> {

            Tagihan t =
                    terpilih();

            String alasan = null;

            if (statusBaru
                    == StatusTagihan.DITOLAK) {

                alasan =
                        JOptionPane
                                .showInputDialog(
                                        this,
                                        "Alasan penolakan:"
                                );

                if (alasan == null) {
                    return;
                }
            }

            service.ubahStatus(
                    t.getId(),
                    t.getStatus(),
                    statusBaru,
                    alasan
            );

            muatSemua();
        });
    }


    // ==============================
    // REKAP
    // ==============================

    private void tampilkanRekap() {

        jalankan(() -> {

            StringBuilder s =
                    new StringBuilder();

            for (RekapStatus r :
                    rekapService
                            .perStatus(
                                    tagihanRepo.semua()
                            )
                            .values()) {

                s.append(
                        r.getStatus()
                );

                s.append(": ");

                s.append(
                        r.getJumlah()
                );

                s.append(
                        " tagihan | Rp "
                );

                s.append(
                        r.getTotal()
                );

                // INI YANG SEBELUMNYA ERROR
                s.append("\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    s.toString(),
                    "Rekap per Status",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }


    // ==============================
    // EKSPOR CSV
    // ==============================

    private void ekspor() {

        SwingWorker<Void, Void> worker =
                new SwingWorker<>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                // CsvExporter menerima String,
                // bukan Path
                CsvExporter.ekspor(
                        "rekap-tagihan.csv",
                        tagihanRepo.semua()
                );

                return null;
            }

            @Override
            protected void done() {

                try {

                    get();

                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Ekspor selesai: rekap-tagihan.csv"
                    );

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Ekspor gagal: "
                            + e.getMessage(),
                            "Kesalahan",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }
}
