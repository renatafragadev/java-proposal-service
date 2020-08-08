package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v1;


import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.VoteRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.converter.VoteConverter;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Vote;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.VoteService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.converter.URIGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/votes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteControllerV1 {

    private final VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody VoteRequest voteRequest) {
        log.info("Controller - create | voteRequest: {}", voteRequest);

        Vote vote = voteService.create(VoteConverter.toEntity(voteRequest));

        log.info("Controller - create | status: {} | id: {}", HttpStatus.CREATED, vote.getId());
        return ResponseEntity.created(URIGenerator.get("/{id}", vote.getId())).build();
    }
}