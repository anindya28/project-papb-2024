package com.example.papostnatal;

public class Profile {
    private String namaPengguna;
    private String namaIbuNifas;
    private String tglPersalinan;
    private String jenisKelamin;


    public void Profile() {}

    public Profile(String namaPengguna, String namaIbuNifas, String tglPersalinan, String jenisKelamin) {
        this.namaPengguna = namaPengguna;
        this.namaIbuNifas = namaIbuNifas;
        this.tglPersalinan = tglPersalinan;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getNamaIbuNifas() {
        return namaIbuNifas;
    }

    public void setNamaIbuNifas(String namaIbuNifas) {
        this.namaIbuNifas = namaIbuNifas;
    }

    public String getTglPersalinan() {
        return tglPersalinan;
    }

    public void setTglPersalinan(String tglPersalinan) {
        this.tglPersalinan = tglPersalinan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }



}
