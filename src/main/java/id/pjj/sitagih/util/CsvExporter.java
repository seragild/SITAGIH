/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagih.util;

import id.pjj.sitagih.model.Tagihan;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


/**
 *
 * @author 060111601
 */
public class CsvExporter {

    public static void ekspor(String namaFile, List<Tagihan> daftar)
            throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(namaFile)) {

            // Header CSV
            out.println(
                    "Nomor,Satker,Tanggal,Uraian,Nilai,Status"
            );

            // Isi data
            for (Tagihan t : daftar) {
                out.println(
                        q(t.getNomor()) + ","
                        + q(t.getNamaSatker()) + ","
                        + q(String.valueOf(t.getTanggal())) + ","
                        + q(t.getUraian()) + ","
                        + q(String.valueOf(t.getNilai())) + ","
                        + q(String.valueOf(t.getStatus()))
                );
            }
        }
    }

    /**
     * Mengamankan teks untuk format CSV.
     * Tanda kutip di dalam teks digandakan.
     */
    private static String q(String s) {

        if (s == null) {
            s = "";
        }

        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}