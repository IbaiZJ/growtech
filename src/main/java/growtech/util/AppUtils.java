package growtech.util;

import com.formdev.flatlaf.util.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AppUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUtils.class);

    public static String getAppBertsioa() {
        var p = AppUtils.class.getPackage();
        return p.getImplementationVersion() == null ? "<DEV>" : p.getImplementationVersion();
    }

    public static boolean sabalduUrlNabigatzailean(String url) {
        if (SystemInfo.isLinux) return sabalduUrlNabigatzaileanXDGOpenErabiltzen(url);
        if (Desktop.isDesktopSupported()) return sabalduUrlNabigatzaileanDesktopErabiltzen(url);

        LOGGER.error("Ezin izan da ireki URLa");
        return false;
    }

    private static boolean sabalduUrlNabigatzaileanXDGOpenErabiltzen(String url) {
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec(new String[]{"sh", "-c", "xdg-open " + url});
            return true;
        } catch (IOException e) {
            LOGGER.error("Ezin izan da ireki URLa", e);
            return false;
        }
    }

    private static boolean sabalduUrlNabigatzaileanDesktopErabiltzen(String url) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(new URI(url));
            return true;
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Ezin izan da ireki URLa", e);
            return false;
        }
    }
}
