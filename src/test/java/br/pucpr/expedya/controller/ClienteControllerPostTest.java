package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Testa o endpoint POST /api/v1/clientes verificando se:
     * 1. Um ClienteDTO é enviado no corpo da requisição em formato JSON.
     * 2. O serviço mockado retorna o ClienteDTO salvo.
     * 3. O controlador responde com status 201 (Created).
     * 4. O JSON retornado contém o e-mail do cliente salvo.
     */
    @Test
    void testCreateCliente() throws Exception {
        ClienteDTO request = new ClienteDTO();
        request.setEmail("novo@teste.com");

        ClienteDTO saved = new ClienteDTO();
        saved.setEmail("novo@teste.com");

        when(clienteService.save(any())).thenReturn(saved);

        mockMvc.perform(
                        post("/api/v1/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("novo@teste.com"));
    }
}
