package com.clinica.clinica_api.controller;

import com.clinica.clinica_api.model.dto.PacienteCreateDTO;
import com.clinica.clinica_api.model.dto.PacienteDTO;
import com.clinica.clinica_api.model.dto.PacienteUpdateDTO;
import com.clinica.clinica_api.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "API para gerenciamento de pacientes")
public class PacienteController {
    private final PacienteService pacienteService;
    @GetMapping
    @Operation(summary = "Listar todos os pacientes",

            description = "Retorna uma lista de todos os pacientes cadastrados")

    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso")
    public ResponseEntity<List<PacienteDTO>> findAll() {
        List<PacienteDTO> pacientes = pacienteService.findAll();
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID",

            description = "Retorna um paciente específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    public ResponseEntity<PacienteDTO> findById(
            @Parameter(description = "ID do paciente") @PathVariable Long id) {
        PacienteDTO paciente = pacienteService.findById(id);
        return ResponseEntity.ok(paciente);
    }
    @PostMapping
    @Operation(summary = "Criar novo paciente",

            description = "Cria um novo paciente no sistema")

    @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<PacienteDTO> create(
            @Parameter(description = "Dados do paciente")
            @Valid @RequestBody PacienteCreateDTO createDTO) {
        PacienteDTO paciente = pacienteService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente",

            description = "Atualiza os dados de um paciente existente")

    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    public ResponseEntity<PacienteDTO> update(
            @Parameter(description = "ID do paciente") @PathVariable Long id,
            @Parameter(description = "Dados para atualização")
            @Valid @RequestBody PacienteUpdateDTO updateDTO) {
        PacienteDTO paciente = pacienteService.update(id, updateDTO);
        return ResponseEntity.ok(paciente);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir paciente",

            description = "Remove um paciente do sistema")

    @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do paciente") @PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/buscar")
    @Operation(summary = "Buscar pacientes por nome",

            description = "Busca pacientes pelo nome (parcial)")

    @ApiResponse(responseCode = "200", description = "Pacientes encontrados")
    public ResponseEntity<List<PacienteDTO>> findByNome(
            @Parameter(description = "Nome do paciente")
            @RequestParam String nome) {
        List<PacienteDTO> pacientes = pacienteService.findByNome(nome);
        return ResponseEntity.ok(pacientes);

    }
    @GetMapping("/tipo-sang/{tipoSang}")
    @Operation(summary = "Buscar pacientes por tipo sanguíneo",

            description = "Retorna pacientes de um tipo sanguíneo específico")
    @ApiResponse(responseCode = "200", description = "Pacientes encontrados")
    public ResponseEntity<List<PacienteDTO>> findByTipoSang(
            @Parameter(description = "Tipo sanguíneo") @PathVariable String tipoSang) {
        List<PacienteDTO> pacientes = pacienteService.findByTipoSang(tipoSang);
        return ResponseEntity.ok(pacientes);
    }
}