## Aplikasi Penatausahaan Tagihan Sederhana
Aplikasi desktop Java Swing untuk pencatatan satker dan tagihan, pengendalian transisi status, pemeriksaan pagu, rekapitulasi, dan ekspor CSV.

## Stack teknologi
- Eclipse Temurin JDK 25 (proyek disetel untuk Java release 25)
- Apache NetBeans 31, proyek Java with Maven
- MySQL 8.0+
- MySQL Connector/J melalui dependency Maven `com.mysql:mysql-connector-j`
- Swing; `MainFrame.form` menyediakan kerangka JFrame yang dikenali NetBeans GUI Builder. Isi tab dibangun dengan Swing dari kode agar mudah dipelihara.
- JUnit Jupiter untuk pengujian unit

## Fitur
- CRUD Satker: tambah, daftar, ubah, hapus; penghapusan ditolak apabila satker masih memiliki tagihan.
- Input dan daftar tagihan.
- Transisi status berurutan: `DIAJUKAN → DIVERIFIKASI → DISETUJUI` atau `DIAJUKAN → DIVERIFIKASI → DITOLAK`. Status terminal tidak dapat dilanjutkan. Penolakan memerlukan alasan.
- Pagu diverifikasi secara transaksional ketika tagihan dibuat. Jumlah tagihan berstatus selain `DITOLAK` untuk satu satker tidak boleh melampaui pagu.
- Riwayat status disimpan di tabel `riwayat_status`.
- Rekap jumlah dan nilai per status diproses memakai Stream API dan lambda, pada thread terpisah melalui `SwingWorker`.
- Ekspor rekap ke CSV.
- Seluruh parameter dari pengguna dikirim dengan `PreparedStatement`.

## Konfigurasi database
Secara default aplikasi membaca `config/db.properties`. Lokasi dapat diganti melalui variabel lingkungan `APP_CONFIG_FILE` atau properti sistem `-Dapp.config=...`. Properti wajib: `db.url`, `db.username`, dan `db.password`.

Link referensi toolchain: 
[Eclipse Temurin JDK 25](https://adoptium.net/temurin/releases/?version=25), 
[Apache NetBeans 31](https://netbeans.apache.org/front/main/download/nb31/),
[MySQL Connector/J via Maven](https://dev.mysql.com/doc/connectors/en/connector-j-installing-maven.html).
