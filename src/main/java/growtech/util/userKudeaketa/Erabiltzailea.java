package growtech.util.userKudeaketa;

import java.util.ArrayList;
import java.util.List;

import growtech.util.klaseak.Negutegia;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erabiltzailea {
    private String izena;
    private String abizena;
    private String erabiltzaile;
    private String pasahitza;
    private String mota;
    private List<Negutegia> negutegiak;

    public Erabiltzailea() {
    }

    public Erabiltzailea(String izena, String abizena, String erabiltzaile, String pasahitza, String mota) {
        this.izena = izena;
        this.abizena = abizena;
        this.erabiltzaile = erabiltzaile;
        this.pasahitza = pasahitza;
        this.mota = mota;
        negutegiak = new ArrayList<>();
    }

    public void gehituNegutegia(Negutegia negutegia) {
        negutegiak.add(negutegia);
    }

    @Override
    public String toString() {
        return "Erabiltzailea [erabiltzaile=" + erabiltzaile + ", pasahitza=" + pasahitza + ", mota=" + mota + "]";
    }

}
