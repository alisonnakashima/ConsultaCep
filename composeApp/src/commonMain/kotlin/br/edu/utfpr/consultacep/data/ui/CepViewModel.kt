package br.edu.utfpr.consultacep.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.consultacep.data.model.Endereco
import br.edu.utfpr.consultacep.data.repository.CepRepository
import br.edu.utfpr.consultacep.data.validator.CepValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

open class CepViewModel(
    private val cepRepository: CepRepository,
    private val cepValidator: CepValidator
) : ViewModel() {

    private val _formState = MutableStateFlow(CepFormState())
    val formState: StateFlow<CepFormState> = _formState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun buscarCep(cep: String) {
        if (_formState.value?.isDataValid != true || _formState.value?.isLoading == true) return

        _formState.value = _formState.value.copy(
            isLoading = true,
            hasErrorLoading = false
        )
        viewModelScope.launch {
            try {
                val endereco = cepRepository.buscarCep(cep)

                if ( endereco.cep.isNullOrBlank() &&
                    endereco.logradouro.isNullOrBlank() && endereco.bairro.isNullOrBlank() &&
                    endereco.cidade.isNullOrBlank() && endereco.uf.isNullOrBlank() ) {
                    _errorMessage.value = "CEP inexistente!"
                    _formState.value = _formState.value.copy(
                        isLoading = false,
                        hasErrorLoading = true
                    )
                    return@launch
                    clearError()
                }

                _formState.value = _formState.value.copy(
                    isLoading = false,
                    endereco = endereco
                )


            } catch (ex: UnknownHostException) {
            // Caso seja erro de conexão, exibir mensagem específica
            _errorMessage.value = "Sem conexão com a internet. Verifique sua rede e tente novamente."
            _formState.value = _formState.value.copy(
                isLoading = false,
                hasErrorLoading = true
            )
        } catch (ex: Exception) {
                _errorMessage.value = "Erro ao consultar o CEP: ${ex.message}"
                _formState.value = _formState.value.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            }
        }
    }

    fun onCepChanged(cep: String) {
        _formState.value = _formState.value.copy(
            isDataValid = cepValidator.verificarCep(cep)
        )
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearEndereco() {
        _formState.value = _formState.value.copy(
            endereco = Endereco()
        )
    }
}