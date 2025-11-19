package br.pucpr.expedya.mapper;

import br.pucpr.expedya.dto.*;
import br.pucpr.expedya.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperDTO {

    // Removi o AviaoRepository daqui. O Mapper agora é "puro", não acessa banco.

    /* ============================================================
       ===============   CLIENTE   ================================
       ============================================================ */

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cliente, dto);

        dto.setSenha(null); // Segurança: nunca retorna a senha no JSON

        if (cliente.getPassagens() != null && !cliente.getPassagens().isEmpty()) {
            dto.setPassagensId(
                    cliente.getPassagens().stream()
                            .map(Passagem::getId) // Pega apenas o ID de cada objeto Passagem
                            .collect(Collectors.toSet()) // Transforma em um Set<Long>
            );
        }

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

        // Mapeia o ID da Companhia
        if (aviao.getCompanhiaAerea() != null) {
            dto.setCompanhiaAereaId(aviao.getCompanhiaAerea().getId());
        }

        // Mapeia os IDs das Passagens
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
        // As relações (Companhia) são setadas no Service
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

            // Opcional: Lista de nomes dos modelos
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
        // As relações (Aviões/Passagens) são geridas no Service
        return companhia;
    }


    /* ============================================================
       ===============   PASSAGEM   ================================
       ============================================================ */
    public PassagemDTO toDTO(Passagem passagem) {
        if (passagem == null) return null;

        PassagemDTO dto = new PassagemDTO();
        BeanUtils.copyProperties(passagem, dto);

        // Mapeia Companhia
        if (passagem.getCompanhiaAerea() != null) {
            dto.setCompanhiaAereaId(passagem.getCompanhiaAerea().getId());
        }

        // Mapeia Avião
        if (passagem.getAviao() != null) {
            dto.setAviaoId(passagem.getAviao().getId());
        }

        // CORREÇÃO FINAL AQUI:
        // Agora passagem.getCliente() existe e é um objeto único.
        if (passagem.getCliente() != null) {
            dto.setClienteId(passagem.getCliente().getId());
        }

        return dto;
    }

    public Passagem toEntity(PassagemDTO dto) {
        if (dto == null) return null;

        Passagem passagem = new Passagem();
        BeanUtils.copyProperties(dto, passagem);
        // Companhia e Aviao são setados no Service
        return passagem;
    }

    // Removi o método getAviaoById pois ele usava o repositório.
}