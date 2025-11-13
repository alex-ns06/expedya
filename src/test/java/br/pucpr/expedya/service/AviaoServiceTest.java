package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.AviaoDTO;
import br.pucpr.expedya.model.Aviao;
import br.pucpr.expedya.model.CompanhiaAerea;
import br.pucpr.expedya.repository.AviaoRepository;
import br.pucpr.expedya.repository.CompanhiaAereaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AviaoServiceTest {

    @Mock
    private AviaoRepository aviaoRepository;

    @Mock
    private CompanhiaAereaRepository companhiaAereaRepository;

    @InjectMocks
    private AviaoService aviaoService;

    @Test
    void testSave_Success() {
        // Setup
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.setId(1L);
        companhia.setNome("Expedya Linhas Aereas");

        AviaoDTO dto = new AviaoDTO(null, "Boeing 737", "PR-EXP", 180, 1L, null);

        Aviao aviaoSalvo = new Aviao();
        aviaoSalvo.setId(10L);
        aviaoSalvo.setModelo("Boeing 737");
        aviaoSalvo.setCompanhiaAerea(companhia);

        when(companhiaAereaRepository.findById(1L)).thenReturn(Optional.of(companhia));
        when(aviaoRepository.save(any(Aviao.class))).thenReturn(aviaoSalvo);

        // Execute
        AviaoDTO result = aviaoService.save(dto);

        // Verify
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Boeing 737", result.getModelo());
        assertEquals("Expedya Linhas Aereas", result.getNomeCompanhiaAerea());
    }

    @Test
    void testSave_ThrowsResourceNotFound_WhenCompanhiaNaoExiste() {
        // Setup
        AviaoDTO dto = new AviaoDTO(null, "Boeing 737", "PR-EXP", 180, 99L, null);
        when(companhiaAereaRepository.findById(99L)).thenReturn(Optional.empty());

        // Execute & Verify
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            aviaoService.save(dto);
        });

        assertEquals("CompanhiaAerea não encontrada com id: 99", exception.getMessage());
    }
}