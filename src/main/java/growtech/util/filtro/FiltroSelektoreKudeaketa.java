package growtech.util.filtro;

import java.util.ArrayList;
import java.util.List;

public class FiltroSelektoreKudeaketa {

    public static <T, V> List<T> filtroa(FiltroSelektorea<T, V> selektorea, List<T> lista) {
        List<T> aukeratuak = new ArrayList<>();

        for (T aukera : lista) {
            if ((selektorea.filtroa(aukera))) {
                aukeratuak.add(aukera);
            }
        }

        return aukeratuak;
    }

}
