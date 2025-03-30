package br.edu.utfpr.consultacep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import br.edu.utfpr.consultacep.data.repository.CepRepository
import br.edu.utfpr.consultacep.data.validator.CepValidator
import br.edu.utfpr.consultacep.data.ui.CepViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainScreen(viewModel = CepViewModel(CepRepository(), CepValidator()))
        }
    }
}