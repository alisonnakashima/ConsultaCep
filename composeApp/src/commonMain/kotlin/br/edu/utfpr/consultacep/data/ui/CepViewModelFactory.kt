package br.edu.utfpr.consultacep.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import br.edu.utfpr.consultacep.data.repository.CepRepository
import br.edu.utfpr.consultacep.data.validator.CepValidator
import kotlin.reflect.KClass

class CepViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        if (modelClass.java.isAssignableFrom(CepViewModel::class.java)) {
            return CepViewModel(
                cepRepository = CepRepository(),
                cepValidator = CepValidator()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
