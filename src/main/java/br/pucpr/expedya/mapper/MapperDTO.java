package br.pucpr.expedya.mapper;

import br.pucpr.expedya.dto.*;
import br.pucpr.expedya.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperDTO {

    /* ============================================================
       ===============   CLIENTE   ================================
       ============================================================ */

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cliente, dto);

        dto.setSenha(null); // Nunca retorna senha
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

        if (aviao.getCompanhiasAereas() != null) {
            dto.setCompanhiaAereaId(
                    aviao.getCompanhiasAereas()
                            .stream()
                            .map(CompanhiaAerea::getId)
                            .collect(Collectors.toSet())
            );

            dto.setNomesCompanhiasAereas(
                    aviao.getCompanhiasAereas()
                            .stream()
                            .map(CompanhiaAerea::getNome)
                            .collect(Collectors.toSet())
            );
        }

        if (aviao.getPassagens() != null) {
            dto.setPassagemId(
                    aviao.getPassagens()
                            .stream()
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

        // Não preenchendo relacionamentos aqui
        // Eles devem ser setados no Service (boa prática)
        return aviao;
    }

    /* ============================================================
       ===============   COMPANHIA AÉREA   =========================
       ============================================================ */

    public CompanhiaAereaDTO toDTO(CompanhiaAerea compania) {
        if (compania == null) return null;

        CompanhiaAereaDTO dto = new CompanhiaAereaDTO();
        BeanUtils.copyProperties(compania, dto);

        if (compania.getPassagens() != null) {
            dto.setPassagensId(
                    compania.getPassagens()
                            .stream()
                            .map(Passagem::getId)
                            .collect(Collectors.toList())
            );
        }

        if (compania.getAviao() != null) {
            dto.setAviaoId(compania.getAviao().getId());
            dto.setModeloAviao(compania.getAviao().getModelo());
        }

        return dto;
    }

    public CompanhiaAerea toEntity(CompanhiaAereaDTO dto) {
        if (dto == null) return null;

        CompanhiaAerea compania = new CompanhiaAerea();
        BeanUtils.copyProperties(dto, compania);
        // Relacionamentos são tratados no service
        return compania;
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
        // Relacionamentos são preenchidos no service
        return passagem;
    }
}
