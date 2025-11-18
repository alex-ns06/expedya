package br.pucpr.expedya.mapper;

import br.pucpr.expedya.dto.*;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.*;
import br.pucpr.expedya.repository.AviaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperDTO {

    private final AviaoRepository aviaoRepository;

    // Construtor injetado pelo Spring
    public MapperDTO(AviaoRepository aviaoRepository) {
        this.aviaoRepository = aviaoRepository;
    }

    /* ============================================================
       ===============   CLIENTE   ================================
       ============================================================ */

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cliente, dto);

        dto.setSenha(null); // segurança
        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(dto, cliente);
        return cliente;
    }


    /* ============================================================
       ===============   AVIÃO   ==================================
       ============================================================ */

    public AviaoDTO toDTO(Aviao aviao) {
        if (aviao == null) return null;

        AviaoDTO dto = new AviaoDTO();
        BeanUtils.copyProperties(aviao, dto);

        if (aviao.getCompanhiaAerea() != null) {
            dto.setCompanhiaAereaId(aviao.getCompanhiaAerea().getId());
        }

        if (aviao.getPassagens() != null) {
            dto.setPassagensId(
                    aviao.getPassagens().stream()
                            .map(Passagem::getId)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    public Aviao toEntity(AviaoDTO dto) {
        if (dto == null) return null;

        Aviao aviao = new Aviao();
        BeanUtils.copyProperties(dto, aviao);
        return aviao;
    }


    /* ============================================================
       ===============   COMPANHIA AÉREA   =========================
       ============================================================ */

    public CompanhiaAereaDTO toDTO(CompanhiaAerea companhia) {
        if (companhia == null) return null;

        CompanhiaAereaDTO dto = new CompanhiaAereaDTO();
        BeanUtils.copyProperties(companhia, dto);

        if (companhia.getAvioes() != null) {
            dto.setAvioesId(
                    companhia.getAvioes().stream()
                            .map(Aviao::getId)
                            .collect(Collectors.toSet())
            );

            dto.setModelosAvioes(
                    companhia.getAvioes().stream()
                            .map(Aviao::getModelo)
                            .collect(Collectors.toSet())
            );
        }

        if (companhia.getPassagens() != null) {
            dto.setPassagensId(
                    companhia.getPassagens().stream()
                            .map(Passagem::getId)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    public CompanhiaAerea toEntity(CompanhiaAereaDTO dto) {
        if (dto == null) return null;

        CompanhiaAerea companhia = new CompanhiaAerea();
        BeanUtils.copyProperties(dto, companhia);
        return companhia;
    }


    /* ============================================================
       ===============   PASSAGEM   ================================
       ============================================================ */

    public PassagemDTO toDTO(Passagem passagem) {
        if (passagem == null) return null;

        PassagemDTO dto = new PassagemDTO();
        BeanUtils.copyProperties(passagem, dto);

        if (passagem.getCompanhiaAerea() != null) {
            dto.setCompanhiaAereaId(passagem.getCompanhiaAerea().getId());
        }
        if (passagem.getCliente() != null) {
            dto.setClienteId(passagem.getCliente().getId());
        }
        if (passagem.getAviao() != null) {
            dto.setAviaoId(passagem.getAviao().getId());
        }

        return dto;
    }

    public Passagem toEntity(PassagemDTO dto) {
        if (dto == null) return null;

        Passagem passagem = new Passagem();
        BeanUtils.copyProperties(dto, passagem);
        return passagem;
    }


    /* ============================================================
       ===============   MÉTODOS AUXILIARES (BUSCAS)   ============
       ============================================================ */

    /**
     * Busca Aviao por id. Lança ResourceNotFoundException se não existir.
     * (Você pediu que o Mapper faça a busca — injetamos AviaoRepository para isso.)
     */
    public Aviao getAviaoById(Long id) {
        return aviaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avião não encontrado com id: " + id));
    }
}
