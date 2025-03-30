package br.edu.utfpr.consultacep

import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.consultacep.data.ui.CepViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.consultacep.data.repository.CepRepository
import br.edu.utfpr.consultacep.data.ui.CepViewModelFactory
import br.edu.utfpr.consultacep.data.validator.CepValidator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: CepViewModel = viewModel(factory = CepViewModelFactory())
            MainScreen(viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val fakeViewModel = remember {
        object : CepViewModel(CepRepository(), CepValidator()) {}
    }
    MainScreen(viewModel = fakeViewModel)
}