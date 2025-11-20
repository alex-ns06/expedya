package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerGetAllTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;


    /**
     * Testa o endpoint GET /api/v1/clientes verificando se:
     * 1. O serviço retorna uma lista contendo um ClienteDTO mockado.
     * 2. A requisição é feita com um usuário autenticado com papel ADMIN.
     * 3. O controlador responde com status 200 (OK).
     * 4. O JSON retornado contém o e-mail do cliente mockado na primeira posição.
     */
    @Test
    void testGetAllClientes() throws Exception {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setEmail("teste@teste.com");

        when(clienteService.findAll()).thenReturn(List.of(cliente));

        mockMvc.perform(
                        get("/api/v1/clientes")
                                .with(user("admin").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("teste@teste.com"));
    }
}

