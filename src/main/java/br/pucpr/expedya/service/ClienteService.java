package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Cliente;
import br.pucpr.expedya.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder; // Para criptografar a senha

    public ClienteDTO save(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        // Criptografa a senha antes de salvar
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Define um role padrão se não for fornecido
        if (dto.getRole() == null || dto.getRole().isEmpty()) {
            cliente.setRole("USER");
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        return toDTO(savedCliente);
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        Cliente updatedCliente = toEntity(dto);
        updatedCliente.setId(id); // Garante a atualização

        // Mantém a senha antiga se a nova for nula/vazia
        if (dto.getSenha() == null || dto.getSenha().isEmpty()) {
            updatedCliente.setSenha(cliente.getSenha());
        } else {
            updatedCliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Cliente savedCliente = clienteRepository.save(updatedCliente);
        return toDTO(savedCliente);
    }

    public ClienteDTO partialUpdate(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        // Copia propriedades não nulas
        BeanUtils.copyProperties(dto, cliente, getNullPropertyNames(dto));

        // Trata a senha separadamente
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Cliente updatedCliente = clienteRepository.save(cliente);
        return toDTO(updatedCliente);
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return toDTO(cliente);
    }

    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        clienteRepository.delete(cliente);
    }

    // --- Mapeadores DTO <-> Entity ---
    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cliente, dto);
        dto.setSenha(null); // Nunca retornar a senha
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(dto, cliente);
        return cliente;
    }

    // --- Helper para o PATCH ---
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}