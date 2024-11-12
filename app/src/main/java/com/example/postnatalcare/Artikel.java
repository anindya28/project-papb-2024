package com.example.postnatalcare;

public class Artikel {
    private  String judul;
    private String gambarUrl;
    private String deskripsi;

    public Artikel(String judul, String gambarUrl, String deskripsi){
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
    public String getDeskripsi(){return deskripsi;}

    public void setGambarUrl(String gambarUrl) {
        this.gambarUrl = gambarUrl;
    }

}
