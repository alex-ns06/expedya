package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.model.Cliente;
import br.pucpr.expedya.repository.ClienteRepository;
import br.pucpr.expedya.mapper.MapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClienteServiceFindAllTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MapperDTO mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método findAll() do ClienteService verificando se:
     * 1. O repositório retorna uma lista contendo uma entidade Cliente mockada.
     * 2. O MapperDTO converte corretamente a entidade para ClienteDTO.
     * 3. O serviço retorna uma lista de ClienteDTO com o tamanho esperado.
     * 4. O e-mail do DTO retornado corresponde ao valor mockado.
     * 5. O método findAll() do repositório é chamado exatamente uma vez.
     */
    @Test
    void testFindAll() {
        Cliente cliente = new Cliente();
        cliente.setEmail("email@teste.com");

        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("email@teste.com");

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(mapper.toDTO(cliente)).thenReturn(dto);

        List<ClienteDTO> result = clienteService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("email@teste.com");
        verify(clienteRepository, times(1)).findAll();
    }
}
