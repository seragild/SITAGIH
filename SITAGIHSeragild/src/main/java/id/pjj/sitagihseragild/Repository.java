package id.pjj.sitagih.repository;

import id.pjj.sitagih.exception.ValidasiException;
import java.util.List;

public interface Repository<T> {
    List<T> semua() throws ValidasiException;
    void tambah(T data) throws ValidasiException;
}
