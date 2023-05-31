package kz.tiger.zone.tigerzoneshopbot.anypay;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnyPayConfig {

    @Value("${anypay.verification}")
    @Getter
    private String verificationCode;
}
