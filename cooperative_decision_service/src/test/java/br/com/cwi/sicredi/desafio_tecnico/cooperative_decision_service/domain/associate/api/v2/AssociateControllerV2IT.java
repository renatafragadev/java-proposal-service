package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v2;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.BaseIntegrationTest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("Integration Test: AssociateV2 - Controller Layer")
@WebMvcTest(AssociateControllerV2.class)
public class AssociateControllerV2IT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssociateService associateService;

    private Associate associateExistent;

    @BeforeEach
    void initEach() {
        createAssociates();
    }

    @SneakyThrows
    @DisplayName("Should consult associated and return code 200")
    @Test
    void findByUuid_ExistingAssociate_ReturnStatusCode200() {
        when(associateService.findByUuid(anyString())).thenReturn(associateExistent);

        MockHttpServletResponse response = mockMvc.perform(
                get("/v2/associates/{uuid}", associateExistent.getUuid())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @SneakyThrows
    @DisplayName("Should return 404 code because there is no resource ")
    @Test
    void findByUuid_NonexistentAssociate_ReturnStatusCode404() {
        when(associateService.findByUuid(anyString())).thenThrow(NotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get("/v2/associates/{uuid}", "cfb07b05-0acb-4195-95da")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    private void createAssociates() {
        associateExistent = new Associate();
        associateExistent.setId(34L);
        associateExistent.setDocument(FAKER.number().digits(11));
        associateExistent.setName(FAKER.name().fullName());
        associateExistent.setUuid(FAKER.internet().uuid());
        associateExistent.setBirthDate(LocalDate.now().minusYears(20));
        associateExistent.setEnabled(true);
    }
}
