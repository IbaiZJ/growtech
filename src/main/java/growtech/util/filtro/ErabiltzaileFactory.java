package growtech.util.filtro;

import java.util.Comparator;

import growtech.util.userKudeaketa.ErabiltzaileMota;
import growtech.util.userKudeaketa.Erabiltzailea;

public class ErabiltzaileFactory {
    public static FiltroSelektorea<Erabiltzailea, String> getFiltroIzena(String balioa) {
        return new FiltroIzena(balioa);
    }

    public static Comparator<Erabiltzailea> getIzenKonparatzailea() {
        return new IzenKonparatzailea();
    }

    public static FiltroSelektorea<Erabiltzailea, String> getFiltroAbizena(String balioa) {
        return new FiltroAbizena(balioa);
    }

    public static FiltroSelektorea<Erabiltzailea, ErabiltzaileMota> getFiltroMota(ErabiltzaileMota balioa) {
        return new FiltroMota(balioa);
    }

    public static FiltroSelektorea<Erabiltzailea, Integer> getFiltroNegutegiaId(Integer balioa) {
        return new FiltroNegutegiaId(balioa);
    }

    public static class FiltroIzena implements FiltroSelektorea<Erabiltzailea, String> {
        String izena;

        public FiltroIzena(String izena) {
            this.izena = izena;
        }

        @Override
        public boolean filtroa(Erabiltzailea balioa) {
            if (balioa instanceof Erabiltzailea)
                return ((Erabiltzailea) balioa).getIzena().equals(izena);
            else
                return false;
        }

        @Override
        public String selektorea(Erabiltzailea balioa) {
            if (balioa instanceof Erabiltzailea)
                return ((Erabiltzailea) balioa).getIzena();
            else
                return null;
        }
    }

    public static class IzenKonparatzailea implements Comparator<Erabiltzailea> {
        @Override
        public int compare(Erabiltzailea a, Erabiltzailea b) {
            int izenKonparatzailea = a.getIzena().toLowerCase().compareTo(b.getIzena().toLowerCase());
            if (izenKonparatzailea == 0) {
                return a.getAbizena().toLowerCase().compareTo(b.getAbizena().toLowerCase());
            }
            return izenKonparatzailea;
        }
    }

    public static class FiltroAbizena implements FiltroSelektorea<Erabiltzailea, String> {
        String abizena;

        public FiltroAbizena(String abizena) {
            this.abizena = abizena;
        }

        @Override
        public boolean filtroa(Erabiltzailea balioa) {
            if (balioa instanceof Erabiltzailea)
                return ((Erabiltzailea) balioa).getAbizena().equals(abizena);
            else
                return false;
        }

        @Override
        public String selektorea(Erabiltzailea balioa) {
            if (balioa instanceof Erabiltzailea)
                return ((Erabiltzailea) balioa).getAbizena();
            else
                return null;
        }
    }

    public static class FiltroMota implements FiltroSelektorea<Erabiltzailea, ErabiltzaileMota> {
        ErabiltzaileMota mota;

        public FiltroMota(ErabiltzaileMota mota) {
            this.mota = mota;
        }

        @Override
        public boolean filtroa(Erabiltzailea balioa) {
            return balioa != null && balioa.getMota() == mota;
        }

        @Override
        public ErabiltzaileMota selektorea(Erabiltzailea balioa) {
            return balioa != null ? balioa.getMota() : null;
        }
    }

    public static class FiltroNegutegiaId implements FiltroSelektorea<Erabiltzailea, Integer> {
        int negutegiaId;

        public FiltroNegutegiaId(int negutegiaId) {
            this.negutegiaId = negutegiaId;
        }

        @Override
        public boolean filtroa(Erabiltzailea erabiltzaile) {
            return erabiltzaile.getNegutegiak().contains(negutegiaId);
        }

        @Override
        public Integer selektorea(Erabiltzailea erabiltzaile) {
            return null;
        }
    }
}
