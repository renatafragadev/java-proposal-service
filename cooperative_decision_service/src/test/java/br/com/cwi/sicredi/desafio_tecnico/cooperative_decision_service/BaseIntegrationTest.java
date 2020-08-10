package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.MessageComponent;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ActiveProfiles("integration-test")
@ExtendWith(SpringExtension.class)
@Import(MessageComponent.class)
@AutoConfigureJsonTesters
public class BaseIntegrationTest {

    protected final static Faker FAKER = new Faker(new Locale("en-US"));

}
