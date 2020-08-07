package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.converter;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIGenerator {

    public static URI get(String path, Object value) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(value)
                .toUri();
    }
}
