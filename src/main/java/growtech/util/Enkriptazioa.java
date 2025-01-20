package growtech.util;

import org.mindrot.jbcrypt.BCrypt;

public class Enkriptazioa {

    public static String kontrasenaEnkriptatu(String kontrasena) {
        return BCrypt.hashpw(kontrasena, BCrypt.gensalt());
    }

    public static boolean kontrasenaKonprobatu(String kontrasena, String kontrasenaEnkriptatuta) {
        return BCrypt.checkpw(kontrasena, kontrasenaEnkriptatuta);
    }

}
