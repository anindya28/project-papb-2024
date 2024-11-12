package com.example.postnatalcare;

public class Riwayat {
    public String SpO2;
    public String mm;
    public String Hg;
    public String bpm;
    public String celcius;
    public String waktuPengisian;


    public Riwayat(String S, String m, String H, String b, String c, String waktu){
        this.SpO2= S;
        this.mm= m;
        this.Hg=H;
        this.bpm=b;
        this.celcius=c;
        this.waktuPengisian = waktu;
    }

    public String getWaktuPengisian(){
        return  waktuPengisian;
    }

    public void  setWaktuPengisian(String waktuPengisian){
        this.waktuPengisian = waktuPengisian;
    }

    public String diagnosa(){
        int spO2 = Integer.parseInt(this.SpO2);
        int bpm = Integer.parseInt(this.bpm);
        int mm = Integer.parseInt(this.mm);
        int hg = Integer.parseInt(this.Hg);
        double suhu = Double.parseDouble(this.celcius);

        if (spO2 < 95 || spO2 >100){
            return "Saturasi Oksigen Tidak Normal";

        }

        if (bpm<60 || bpm>100){
            return  "Detak Jantung (bpm) Tidak Normal";
        } else if (mm > 130 && hg > 80){
            return "Tekanan Darah Tidak Normal";
        }

        if (suhu < 36.5 || suhu > 37.5){
            return "Suhu Tubuh Tidak Normal";
        }

        return  "Semua Normal";
    }
}
