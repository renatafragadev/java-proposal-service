package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.user_info.component;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.enumerator.I18nMessage;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.ServiceUnavailableException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.user_info.enumerator.VoteStatus;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.user_info.pojo.VoteStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Lazy
@Component
public class UserInfoIntegrationComponent {

    private final WebClient userInfoClient;

    public UserInfoIntegrationComponent(@Value("${services.user-info.base-url}") String baseUrl) {
        this.userInfoClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public boolean validateDocument(String document) {
        log.info("Integration - validateDocument | document: {}", document);

        VoteStatusResponse statusResponseMono = userInfoClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users/{cpf}").build(document))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    throw new ServiceUnavailableException(I18nMessage.SESSION_UNAVAILABLE.getKey());
                })
                .bodyToMono(VoteStatusResponse.class)
                .timeout(Duration.ofMinutes(1))
                .block();

        return statusResponseMono.getStatus().equals(VoteStatus.ABLE_TO_VOTE);
    }
}
