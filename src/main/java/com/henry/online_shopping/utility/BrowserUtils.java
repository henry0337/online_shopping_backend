package com.henry.online_shopping.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public final class BrowserUtils {

    /**
     * Lấy ra tên thường gọi của trình duyệt mặc định của thiết bị đang tương tác.
     * @return Tên thường gọi của trình duyệt mặc định trong thiết bị.
     */
    @NonNull
    public static String getDefaultBrowser() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            if (os.contains("win")) { // Windows
                process = Runtime.getRuntime().exec("reg query HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\Shell\\Associations\\UrlAssociations\\http\\UserChoice /v ProgId");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) { // Linux
                process = Runtime.getRuntime().exec("xdg-settings get default-web-browser");
            } else {
                return "Got error when executing system command.";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            if (os.contains("win")) {
                return result.toString().contains("Chrome") ? "chrome" :
                        result.toString().contains("Edge") ? "edge" :
                                result.toString().contains("Brave") ? "brave" : "unknown";
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                return result.toString();
            }

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return "Unknown Browser";
    }
}
