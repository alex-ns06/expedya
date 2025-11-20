package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.mapper.MapperDTO;
import br.pucpr.expedya.model.Cliente;
import br.pucpr.expedya.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClienteServiceFindByIdTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MapperDTO mapper;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método findById() do ClienteService verificando se:
     * 1. O repositório retorna corretamente um Cliente dentro de um Optional.
     * 2. O MapperDTO converte a entidade Cliente para ClienteDTO.
     * 3. O serviço retorna um DTO não nulo com os dados esperados.
     * 4. O ID e o e-mail do cliente retornado correspondem aos valores mockados.
     * 5. Os métodos findById() do repositório e toDTO() do mapper são chamados exatamente uma vez.
     */
    @Test
    void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setEmail("id@teste.com");

        ClienteDTO dto = new ClienteDTO();
        dto.setId(1L);
        dto.setEmail("id@teste.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(mapper.toDTO(cliente)).thenReturn(dto);

        ClienteDTO result = clienteService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("id@teste.com");

        verify(clienteRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDTO(cliente);
    }
}
