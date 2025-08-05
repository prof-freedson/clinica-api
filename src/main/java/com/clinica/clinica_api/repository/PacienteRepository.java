package com.clinica.clinica_api.repository;

import com.clinica.clinica_api.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Buscar por nome (case insensitive)
    List<Paciente> findByNomePacContainingIgnoreCase(String nomePac);
    // Buscar por tipo sanguíneo

    List<Paciente> findByTipoSang(String tipoSang);
    // Buscar por data de nascimento

    List<Paciente> findByDataNascPacBetween(LocalDate dataInicio, LocalDate dataFim);
    // Buscar por peso maior que

    List<Paciente> findByPesoPacGreaterThan(BigDecimal peso);
// Verificar se existe por nome e data de nascimento

    boolean existsByNomePacAndDataNascPac(String nomePac, LocalDate dataNascPac);
}

