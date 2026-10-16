/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.Satker to edit this template
 */
package id.pjj.sitagih.model;

/**
 *
 * @author Zaman PC
 */
public class Satker {
    private final String kode;
    private String nama;

    public Satker(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    @Override public String toString() { return kode + " - " + nama; }
}
