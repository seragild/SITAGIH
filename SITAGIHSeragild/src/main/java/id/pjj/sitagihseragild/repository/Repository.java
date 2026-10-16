/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package id.pjj.sitagihseragild.repository;

import id.pjj.sitagihseragild.exception.ValidasiException;
import java.util.List;

/**
 *
 * @author Zaman PC
 */
public interface Repository<T> {
    List<T> semua() throws ValidasiException;
    void tambah(T data) throws ValidasiException;
}
