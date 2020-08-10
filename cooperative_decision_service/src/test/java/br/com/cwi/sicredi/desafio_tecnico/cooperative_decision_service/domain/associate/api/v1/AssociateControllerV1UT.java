package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.v1;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.BaseIntegrationTest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.api.request.AssociateRequest;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.entity.Associate;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.domain.associate.service.AssociateService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Integration Test: AssociateV1 - Controller Layer")
@WebMvcTest(AssociateControllerV1.class)
public class AssociateControllerV1UT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AssociateRequest> associateReqJacksonTest;

    @MockBean
    private AssociateService associateService;

    private Associate associateExistent;

    @BeforeEach
    void initEach() {
        createAssociates();
    }

    @DisplayName("All tests of createMethod")
    @Nested
    class CreateMethodTest {

        @SneakyThrows
        @DisplayName("Should create associated and return status code 201")
        @Test
        void create_ValidResource_ReturnStatusCode201() {
            AssociateRequest associateRequest = new AssociateRequest();
            associateRequest.setName(FAKER.name().fullName());
            associateRequest.setDocument(FAKER.number().digits(11));
            associateRequest.setBirthDate(LocalDate.now().minusYears(2));

            when(associateService.create(any(Associate.class))).thenReturn(associateExistent);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/v1/associates")
                            .content(associateReqJacksonTest.write(associateRequest).getJson())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse();

            assertEquals(HttpStatus.CREATED.value(), response.getStatus());
            assertThat(response.getHeader("location")).contains(associateExistent.getId().toString());
        }

        @SneakyThrows
        @DisplayName("Should return status code 400 and not create a resource because the document field is invalid")
        @Test
        void create_InvalidDocumentField_ReturnStatusCode400() {
            AssociateRequest associateRequest = new AssociateRequest();
            associateRequest.setName(FAKER.name().fullName());
            associateRequest.setDocument(FAKER.number().digits(5));
            associateRequest.setBirthDate(LocalDate.now().minusYears(2));

            when(associateService.create(any(Associate.class))).thenReturn(associateExistent);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/v1/associates")
                            .content(associateReqJacksonTest.write(associateRequest).getJson())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse();

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        }
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
