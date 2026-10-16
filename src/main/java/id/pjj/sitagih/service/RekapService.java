package id.pjj.sitagih.service;

import id.pjj.sitagih.model.*;
import java.util.*;

public class RekapService {
    public Map<StatusTagihan, RekapStatus> perStatus(List<Tagihan> data) {
        Map<StatusTagihan, RekapStatus> hasil = new LinkedHashMap<>();
        for (StatusTagihan s : StatusTagihan.values()) hasil.put(s, new RekapStatus(s));
        for (Tagihan t : data) hasil.get(t.getStatus()).tambah(t.getNilai());
        return hasil;
    }
}
