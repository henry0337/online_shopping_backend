package com.henry.online_shopping.config;

import com.henry.online_shopping.utility.CertificateUtils;
import com.henry.online_shopping.utility.constant.Profiles;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Profile({Profiles.HTTPS})
public class SetupCertificateListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        try {
            if (!CertificateUtils.isCertificateInstalled("spring_keystore")) {
                CertificateUtils.installCertificate();
            }
        } catch (Exception ignored) {}
    }
}
