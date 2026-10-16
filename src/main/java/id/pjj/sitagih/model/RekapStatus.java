/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagih.model;

import java.math.BigDecimal;

/**
 *
 * @author Zaman PC
 */
public class RekapStatus {
    private final StatusTagihan status;
    private int jumlah;
    private BigDecimal total = BigDecimal.ZERO;

    public RekapStatus(StatusTagihan status) { this.status = status; }
    public void tambah(BigDecimal nilai) { jumlah++; total = total.add(nilai); }
    public StatusTagihan getStatus() { return status; }
    public int getJumlah() { return jumlah; }
    public BigDecimal getTotal() { return total; }
}
