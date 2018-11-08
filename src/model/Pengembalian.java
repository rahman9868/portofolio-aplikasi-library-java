/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LABP2KOMP14
 */
public class Pengembalian {
    
public String kode_anggota;
public String kode_buku;
public String tgl_kembali;
private String tgl_dikembalikan;
public int terlambat;
public double denda;

    public String getKode_anggota() {
        return kode_anggota;
    }

    public void setKode_anggota(String kode_anggota) {
        this.kode_anggota = kode_anggota;
    }

    public String getKode_buku() {
        return kode_buku;
    }

    public void setKode_buku(String kode_buku) {
        this.kode_buku = kode_buku;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getTgl_dikembalikan() {
        return tgl_dikembalikan;
    }

    public void setTgl_dikembalikan(String tgl_dikembalikan) {
        this.tgl_dikembalikan = tgl_dikembalikan;
    }

    
    public int getTerlambat() {
        return terlambat;
    }

    public void setTerlambat(int terlambat) {
        this.terlambat = terlambat;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }


}
