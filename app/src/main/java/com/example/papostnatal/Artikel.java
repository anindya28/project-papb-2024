package com.example.papostnatal;

public class Artikel {
    private String judul;
    private String gambarUrl;
    private String deskripsi;

    public Artikel() {

    }

    public Artikel(String judul, String gambarUrl, String deskripsi) {
        this.judul = judul;
        this.gambarUrl = gambarUrl;
        this.deskripsi = deskripsi;
    }

    public String getGambarUrl() {
        return gambarUrl;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setGambarUrl(String gambarUrl) {
        this.gambarUrl = gambarUrl;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
