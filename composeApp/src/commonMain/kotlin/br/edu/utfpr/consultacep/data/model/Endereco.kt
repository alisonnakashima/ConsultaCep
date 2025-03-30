package br.edu.utfpr.consultacep.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Endereco(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    @SerialName("localidade")
    val cidade: String = "",
    val uf: String = ""
) {
    val cidadeUf get(): String = "$cidade - $uf"
}