package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

@ActiveProfiles("unit-test")
@ExtendWith(MockitoExtension.class)
public class BaseUnitTest {

    public final static Faker FAKER = new Faker(new Locale("en-US"));

}
