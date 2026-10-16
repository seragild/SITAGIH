/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.pjj.sitagihseragild.service;

import id.pjj.sitagihseragild.model.RekapStatus;
import id.pjj.sitagihseragild.model.StatusTagihan;
import id.pjj.sitagihseragild.model.Tagihan;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zaman PC
 */
public class RekapService {
    public Map<StatusTagihan, RekapStatus> perStatus(List<Tagihan> data) {
        Map<StatusTagihan, RekapStatus> hasil = new LinkedHashMap<>();
        for (StatusTagihan s : StatusTagihan.values()) hasil.put(s, new RekapStatus(s));
        for (Tagihan t : data) hasil.get(t.getStatus()).tambah(t.getNilai());
        return hasil;
    }
}
