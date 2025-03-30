package br.edu.utfpr.consultacep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.edu.utfpr.consultacep.data.ui.CepViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: CepViewModel
) {

    var cepInput by remember { mutableStateOf(TextFieldValue()) }
    val formState by viewModel.formState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.clearError() // Limpa o erro após exibição
            }
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                enabled = !formState.isLoading,
                value = cepInput,
                onValueChange = {
                    val rawText = it.text.filter { char -> char.isDigit() }

                    // Limitando a entrada em 8 caracteres
                    val limitedText = rawText.take(8)

                    /// Formatar o texto para o formato de CEP (XXXXX-XXX)
                    val formatted = if (limitedText.length > 5) {
                        "${limitedText.take(5)}-${limitedText.drop(5)}"
                    } else {
                        limitedText
                    }

                    cepInput = TextFieldValue(
                        text = formatted,
                        selection = TextRange(formatted.length) // Define o cursor no final do texto
                    )
                    viewModel.onCepChanged(formatted)
                },
                label = { Text("Digite o CEP") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (formState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        viewModel.clearEndereco()
                        viewModel.buscarCep(cepInput.text)
                    },
                    enabled = formState.isDataValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Buscar")
                }
            }

            Text("CEP: ${formState.endereco.cep}")
            Text("Logradouro: ${formState.endereco.logradouro}")
            Text("Bairro: ${formState.endereco.bairro}")
            Text("Localidade: ${formState.endereco.cidade}")
            Text("UF: ${formState.endereco.uf}")

        }
    }
}