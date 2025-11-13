package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.security.JwtFilter;
import br.pucpr.expedya.security.JwtUtil;
import br.pucpr.expedya.service.AviaoService;
import br.pucpr.expedya.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AviaoController.class)
public class AviaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AviaoService aviaoService;

    // Mocks necessários para o SecurityConfig e JwtFilter
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN") // Simula um usuário logado com role ADMIN
    void testFindById_Success() throws Exception {
        // Setup
        AviaoDTO aviao = new AviaoDTO(1L, "Airbus A320", "PR-EXA", 150, 1L, "Expedya");
        when(aviaoService.findById(1L)).thenReturn(aviao);

        // Execute & Verify
        mockMvc.perform(get("/api/v1/avioes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.modelo").value("Airbus A320"));
    }

    @Test
    @WithMockUser(roles = "USER") // Simula um usuário comum
    void testFindById_Forbidden_WhenRoleIsUser() throws Exception {
        // Execute & Verify
        mockMvc.perform(get("/api/v1/avioes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // @PreAuthorize("hasRole('ADMIN')") deve bloquear
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSave_Success() throws Exception {
        // Setup
        AviaoDTO requestDTO = new AviaoDTO(null, "Airbus A320", "PR-EXA", 150, 1L, null);
        AviaoDTO responseDTO = new AviaoDTO(10L, "Airbus A320", "PR-EXA", 150, 1L, "Expedya");

        when(aviaoService.save(any(AviaoDTO.class))).thenReturn(responseDTO);

        // Execute & Verify
        mockMvc.perform(post("/api/v1/avioes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nomeCompanhiaAerea").value("Expedya"));
    }
}