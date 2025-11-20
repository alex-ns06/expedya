package br.pucpr.expedya.service;

import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.exception.ResourceNotFoundException;
import br.pucpr.expedya.model.Cliente;
import br.pucpr.expedya.security.Role;
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
    private final PasswordEncoder passwordEncoder;

    // ---------- CREATE ----------
    public ClienteDTO save(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);

        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));

        if (dto.getRole() == null) {
            cliente.setRole(Role.USER);
        }

        return toDTO(clienteRepository.save(cliente));
    }

    // ---------- FULL UPDATE (PUT) ----------
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente existing = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        Cliente updated = toEntity(dto);
        updated.setId(id);

        if (dto.getSenha() == null || dto.getSenha().isEmpty()) {
            updated.setSenha(existing.getSenha());
        } else {
            updated.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return toDTO(clienteRepository.save(updated));
    }

    // ---------- PARTIAL UPDATE (PATCH) ----------
    public ClienteDTO partialUpdate(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        BeanUtils.copyProperties(dto, cliente, getNullPropertyNames(dto));

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return toDTO(clienteRepository.save(cliente));
    }

    // ---------- GET ALL ----------
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------- GET BY ID ----------
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return toDTO(cliente);
    }

    // ---------- DELETE ----------
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        clienteRepository.delete(cliente);
    }

    // ---------- MAPEAMENTO DTO → ENTITY ----------
    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(dto, cliente);
        return cliente;
    }

    // ---------- MAPEAMENTO ENTITY → DTO ----------
    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        BeanUtils.copyProperties(cliente, dto);
        dto.setSenha(null);
        return dto;
    }

    // ---------- IGNORA CAMPOS NULOS NO PATCH ----------
    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        return java.util.Arrays.stream(src.getPropertyDescriptors())
                .map(pd -> pd.getName())
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com email: " + email));
    }

}
