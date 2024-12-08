package com.example.papostnatal;

public class Tips {

    private String isiTips;
    private String judul;
    private String gambarUrl;
    private String deskripsi;

    public Tips(){

    }

    public Tips(String judul, String gambarUrl, String deskripsi, String isiTips){
        this.judul = judul;
        this.gambarUrl = gambarUrl;
        this.deskripsi = deskripsi;
        this.isiTips = isiTips;
    }

    public String getGambarUrl() {
        return gambarUrl;
    }
    public String getJudul() {
        return judul;
    }
    public String getDeskripsi(){return deskripsi;}
    public String getIsiTips(){return isiTips;}

    public void setIsiTips(String isiTips) {
        this.isiTips = isiTips;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    public void setGambarUrl(String gambarUrl) {
        this.gambarUrl = gambarUrl;
    }


}
