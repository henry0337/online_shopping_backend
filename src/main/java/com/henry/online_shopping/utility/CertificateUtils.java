package com.henry.online_shopping.utility;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Slf4j
public final class CertificateUtils {

    /**
     * Install <b>PKCS12</b> certificate into the device's current OS.
     * @throws IOException when the command can't be executed by unknown reasons.
     * @apiNote Working only on <b>Windows</b> for the time being.
     */
    public static void installCertificate() throws IOException {
        final String path = System.getProperty("user.dir") + "/src/main/resources/";
        final String certName = "backend.p12";

        ProcessBuilder builder = new ProcessBuilder("certutil", "-f", "-importpfx", path + certName, "Root");
        builder.start();
    }

    /**
     * Indicate whether the certificate with the {@code aliasToCheck} alias was installed in the Trusted Root certificate store or not.
     * @param aliasToCheck The certificate's alias name.
     * @return {@code true} if installed, {@code false} otherwise.
     * @throws Exception if errors are occurred while interacting with target certificate.
     * @apiNote Working only on <b>Windows</b> for the time being.
     */
    public static boolean isCertificateInstalled(String aliasToCheck) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("Windows-ROOT");
        keyStore.load(null, null);

        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            if (certificate != null && alias.equalsIgnoreCase(aliasToCheck)) {
                System.out.println("Certificate found: " + alias);
                return true;
            }
        }
        log.error("The certificate with alias: \"{}\" doesn't exist inside Trusted Root certificate store", aliasToCheck);
        return false;
    }
}
