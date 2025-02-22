package growtech.util.filtro;

import growtech.util.negutegiKudeaketa.Negutegia;

public class NegutegiFactory {
    public static FiltroSelektorea<Negutegia, String> getFiltroLurralde(String balioa) {
        return new FiltroLurralde(balioa);
    }
    public static FiltroSelektorea<Negutegia, String> getFiltroHerria(String balioa) {
        return new FiltroHerria(balioa);
    }

    public static class FiltroLurralde implements FiltroSelektorea<Negutegia, String> {
        String lurraldea;

        public FiltroLurralde(String lurraldea) {
            this.lurraldea = lurraldea;
        }
        @Override
        public boolean filtroa(Negutegia balioa) {
            if(balioa instanceof Negutegia) 
                return ((Negutegia)balioa).getLurraldea().equals(lurraldea);
            else 
                return false;
        }
        @Override
        public String selektorea(Negutegia balioa) {
            if(balioa instanceof Negutegia) 
                return ((Negutegia)balioa).getLurraldea();
            else 
                return null;
        }
    }

    public static class FiltroHerria implements FiltroSelektorea<Negutegia, String> {
        String herria;

        public FiltroHerria(String herria) {
            this.herria = herria;
        }
        @Override
        public boolean filtroa(Negutegia balioa) {
            if(balioa instanceof Negutegia) 
                return ((Negutegia)balioa).getHerria().equals(herria);
            else 
                return false;
        }
        @Override
        public String selektorea(Negutegia balioa) {
            if(balioa instanceof Negutegia) 
                return ((Negutegia)balioa).getHerria();
            else 
                return null;
        }
    }
}
