package growtech.util;

import com.formdev.flatlaf.util.SystemInfo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AppUtils {

    public static String getAppBertsioa() {
        var p = AppUtils.class.getPackage();
        return p.getImplementationVersion() == null ? "<DEV>" : p.getImplementationVersion();
    }

    public static boolean sabalduUrlNabigatzailean(String url) {
        if (SystemInfo.isLinux) return sabalduUrlNabigatzaileanXDGOpenErabiltzen(url);
        if (Desktop.isDesktopSupported()) return sabalduUrlNabigatzaileanDesktopErabiltzen(url);

        System.out.println("Ezin izan da ireki URLa");
        return false;
    }

    private static boolean sabalduUrlNabigatzaileanXDGOpenErabiltzen(String url) {
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec(new String[]{"sh", "-c", "xdg-open " + url});
            return true;
        } catch (IOException e) {
            System.out.println("Ezin izan da ireki URLa");
            return false;
        }
    }

    private static boolean sabalduUrlNabigatzaileanDesktopErabiltzen(String url) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(new URI(url));
            return true;
        } catch (IOException | URISyntaxException e) {
            System.out.println("Ezin izan da ireki URLa");
            return false;
        }
    }
}
