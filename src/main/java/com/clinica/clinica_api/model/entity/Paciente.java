package com.clinica.clinica_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "paciente", schema = "clinica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pac")
    private Integer idPac;

    @NotBlank(message = "Nome do paciente é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome_pac", nullable = false)
    private String nomePac;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "data_nasc_pac", nullable = false)
    private LocalDate dataNascPac;

    @DecimalMin(value = "0.0", message = "Peso deve ser maior que 0")
    @DecimalMax(value = "999.99", message = "Peso deve ser menor que 1000")
    @Column(name = "peso_pac", precision = 5, scale = 2)
    private BigDecimal pesoPac;

    @DecimalMin(value = "0.0", message = "Altura deve ser maior que 0")
    @DecimalMax(value = "9.99", message = "Altura deve ser menor que 10")
    @Column(name = "alt_pac", precision = 4, scale = 2)
    private BigDecimal altPac;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo sanguíneo inválido")
    @Column(name = "tipo_sang", length = 3)
    private String tipoSang;

//    @ManyToOne
//    @JoinColumn(name = "id_end", referencedColumnName = "id_end")
//    private Endereco endereco;
}