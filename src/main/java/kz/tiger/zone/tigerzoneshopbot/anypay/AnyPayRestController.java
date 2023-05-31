package kz.tiger.zone.tigerzoneshopbot.anypay;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnyPayRestController {
    private final AnyPayConfig config;

    @GetMapping("/anypay-verification.txt")
    public String getVerification() {
        return config.getVerificationCode();
    }
}
