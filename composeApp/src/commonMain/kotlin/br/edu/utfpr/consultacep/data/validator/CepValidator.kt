package br.edu.utfpr.consultacep.data.validator

class CepValidator {

    fun verificarCep(cep: String): Boolean {
        val regex = "^\\d{5}-\\d{3}\$".toRegex()
        return regex.matches(cep)
    }
}