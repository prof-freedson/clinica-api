package com.clinica.clinica_api.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Integer idPac;
    private String nomePac;
    private LocalDate dataNascPac;
    private BigDecimal pesoPac;
    private BigDecimal altPac;
    private String tipoSang;
}