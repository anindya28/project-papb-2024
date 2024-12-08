package com.example.papostnatal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RekamMedis {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "saturasiOksigen")
    public String saturasiOksigen;
    @ColumnInfo(name = "tekananDarah")
    public String tekananDarah;
    @ColumnInfo(name = "detakJantung")
    public String detakJantung;
    @ColumnInfo(name = "suhu")
    public String suhu;
    @ColumnInfo(name = "waktuPengisian")
    public String waktuPengisian;
    @ColumnInfo(name = "diagnosa")
    public String diagnosa;

    public int getId() {
        return id;
    }

    public RekamMedis(String saturasiOksigen, String tekananDarah, String detakJantung, String suhu, String waktuPengisian, String diagnosa){
        this.saturasiOksigen = saturasiOksigen;
        this.tekananDarah = tekananDarah;
        this.detakJantung =detakJantung;
        this.suhu = suhu;
        this.waktuPengisian = waktuPengisian;
        this.diagnosa = diagnosa;
    }

    public  String diagnosa(){
        int spO2 = Integer.parseInt(this.saturasiOksigen);
        String[] tekananSplit = this.tekananDarah.split("/"); // Pecah sistolik/diastolik
        int sistolik = Integer.parseInt(tekananSplit[0]);
        int diastolik = Integer.parseInt(tekananSplit[1]);
        int bpm = Integer.parseInt(this.detakJantung);
        double suhu = Double.parseDouble(this.suhu);

        StringBuilder diagnosa = new StringBuilder();

        if (spO2 < 95 || spO2 >100){
           diagnosa.append("Saturasi Oksigen Tidak Normal\n");
        }
        if (bpm<60 || bpm>100){
            diagnosa.append("Detak Jantung (bpm) Tidak Normal\n");
        } else if (sistolik > 130 && diastolik > 80){
            diagnosa.append("Tekanan Darah Tidak Normal\n");
        }
        if (suhu < 36.5 || suhu > 37.5){
            diagnosa.append("Suhu Tubuh Tidak Normal\n");
        }
        if (diagnosa.length() == 0){
            return "Semua Normal";
        }

        return diagnosa.toString().trim();
    }
}
