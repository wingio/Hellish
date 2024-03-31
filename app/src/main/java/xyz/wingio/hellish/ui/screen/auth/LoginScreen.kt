package xyz.wingio.hellish.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import xyz.wingio.hellish.ui.screen.auth.viewmodel.LoginViewModel
import xyz.wingio.hellish.ui.screen.main.MainScreen
import xyz.wingio.hellish.util.navigate

class LoginScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(viewModel.loginSuccess) {
            if (viewModel.loginSuccess) navigator.navigate(MainScreen())
        }

        Scaffold { pv ->
            Column(
                modifier = Modifier.padding(pv)
            ) {
                OutlinedTextField(
                    label = { Text("Username") },
                    value = viewModel.username,
                    onValueChange = {
                        viewModel.username = it
                    }
                )

                OutlinedTextField(
                    label = { Text("Password") },
                    value = viewModel.password,
                    onValueChange = {
                        viewModel.password = it
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.login() },
                    enabled = viewModel.username.isNotBlank() && viewModel.password.isNotBlank()
                ) {
                    Text("Login")
                }
            }
        }
    }

}