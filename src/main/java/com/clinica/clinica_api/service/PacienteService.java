package com.clinica.clinica_api.service;

import com.clinica.clinica_api.model.dto.PacienteCreateDTO;
import com.clinica.clinica_api.model.dto.PacienteDTO;
import com.clinica.clinica_api.model.dto.PacienteUpdateDTO;
import com.clinica.clinica_api.model.entity.Paciente;
import com.clinica.clinica_api.repository.PacienteRepository;
import com.clinica.clinica_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    public PacienteDTO findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
        return convertToDTO(paciente);
    }
    public PacienteDTO create(PacienteCreateDTO createDTO) {
        Paciente paciente = new Paciente();
        paciente.setNomePac(createDTO.getNomePac());
        paciente.setDataNascPac(createDTO.getDataNascPac());
        paciente.setPesoPac(createDTO.getPesoPac());
        paciente.setAltPac(createDTO.getAltPac());
        paciente.setTipoSang(createDTO.getTipoSang());
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return convertToDTO(savedPaciente);
    }
    public PacienteDTO update(Long id, PacienteUpdateDTO updateDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
        if (updateDTO.getNomePac() != null) {
            paciente.setNomePac(updateDTO.getNomePac());
        }
        if (updateDTO.getDataNascPac() != null) {
            paciente.setDataNascPac(updateDTO.getDataNascPac());

        }
        if (updateDTO.getPesoPac() != null) {
            paciente.setPesoPac(updateDTO.getPesoPac());
        }
        if (updateDTO.getAltPac() != null) {
            paciente.setAltPac(updateDTO.getAltPac());
        }
        if (updateDTO.getTipoSang() != null) {
            paciente.setTipoSang(updateDTO.getTipoSang());
        }

        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return convertToDTO(updatedPaciente);
    }
    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
    public List<PacienteDTO> findByNome(String nome) {

        return pacienteRepository.findByNomePacContainingIgnoreCase(nome)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    public List<PacienteDTO> findByTipoSang(String tipoSang) {
        return pacienteRepository.findByTipoSang(tipoSang)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    private PacienteDTO convertToDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setIdPac(paciente.getIdPac());
        dto.setNomePac(paciente.getNomePac());
        dto.setDataNascPac(paciente.getDataNascPac());
        dto.setPesoPac(paciente.getPesoPac());
        dto.setAltPac(paciente.getAltPac());
        dto.setTipoSang(paciente.getTipoSang());
        return dto;
    }
}