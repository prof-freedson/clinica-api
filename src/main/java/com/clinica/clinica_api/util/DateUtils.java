package com.clinica.clinica_api.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Calcula a idade baseada na data de nascimento
     * @param dataNascimento Data de nascimento
     * @return Idade em anos
     */
    public static int calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
    
    /**
     * Formata uma data para o padrão brasileiro (dd/MM/yyyy)
     * @param data Data a ser formatada
     * @return Data formatada como string
     */
    public static String formatarData(LocalDate data) {
        if (data == null) {
            return "";
        }
        return data.format(DATE_FORMATTER);
    }
    
    /**
     * Verifica se uma data é válida (não é futura)
     * @param data Data a ser validada
     * @return true se a data é válida, false caso contrário
     */
    public static boolean isDataValida(LocalDate data) {
        return data != null && !data.isAfter(LocalDate.now());
    }
    
    /**
     * Verifica se uma pessoa é maior de idade (18 anos ou mais)
     * @param dataNascimento Data de nascimento
     * @return true se é maior de idade, false caso contrário
     */
    public static boolean isMaiorDeIdade(LocalDate dataNascimento) {
        return calcularIdade(dataNascimento) >= 18;
    }
} 