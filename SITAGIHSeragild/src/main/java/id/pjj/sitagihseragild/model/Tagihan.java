/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Zaman PC
 */
public class Tagihan {
    private int id;
    private String kodeSatker;
    private String namaSatker;
    private String nomor;
    private LocalDate tanggal;
    private String uraian;
    private BigDecimal nilai;
    private StatusTagihan status;
    private String alasanPenolakan;

    public Tagihan(int id, String kodeSatker, String namaSatker, String nomor,
                   LocalDate tanggal, String uraian, BigDecimal nilai,
                   StatusTagihan status, String alasanPenolakan) {
        this.id = id;
        this.kodeSatker = kodeSatker;
        this.namaSatker = namaSatker;
        this.nomor = nomor;
        this.tanggal = tanggal;
        this.uraian = uraian;
        this.nilai = nilai;
        this.status = status;
        this.alasanPenolakan = alasanPenolakan;
    }

    public int getId() { return id; }
    public String getKodeSatker() { return kodeSatker; }
    public String getNamaSatker() { return namaSatker; }
    public String getNomor() { return nomor; }
    public LocalDate getTanggal() { return tanggal; }
    public String getUraian() { return uraian; }
    public BigDecimal getNilai() { return nilai; }
    public StatusTagihan getStatus() { return status; }
    public String getAlasanPenolakan() { return alasanPenolakan; }

    public void setKodeSatker(String v) { kodeSatker = v; }
    public void setNomor(String v) { nomor = v; }
    public void setTanggal(LocalDate v) { tanggal = v; }
    public void setUraian(String v) { uraian = v; }
    public void setNilai(BigDecimal v) { nilai = v; }
    public void setStatus(StatusTagihan v) { status = v; }
    public void setAlasanPenolakan(String v) { alasanPenolakan = v; }
}
