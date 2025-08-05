package com.clinica.clinica_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteCreateDTO {
    @NotBlank(message = "Nome do paciente é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nomePac;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascPac;

    @DecimalMin(value = "0.0", message = "Peso deve ser maior que 0")
    @DecimalMax(value = "999.99", message = "Peso deve ser menor que 1000")
    private BigDecimal pesoPac;

    @DecimalMin(value = "0.0", message = "Altura deve ser maior que 0")
    @DecimalMax(value = "9.99", message = "Altura deve ser menor que 10")
    private BigDecimal altPac;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo sanguíneo inválido")
    private String tipoSang;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoPac;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefonePac;

    @Email(message = "Email deve ser válido")
    private String emailPac;
}