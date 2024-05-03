package xyz.wingio.hellish.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.component.LoadingButton
import xyz.wingio.hellish.ui.screen.auth.viewmodel.LoginViewModel
import xyz.wingio.hellish.ui.screen.main.MainScreen

class LoginFormScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginViewModel = getScreenModel()

        LaunchedEffect(viewModel.loginSuccess) {
            if (viewModel.loginSuccess) navigator.parent!!.replace(MainScreen())
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            val canSubmit = viewModel.username.isNotBlank() && viewModel.password.isNotBlank()

            OutlinedTextField(
                label = { Text(stringResource(R.string.label_username)) },
                value = viewModel.username,
                onValueChange = viewModel::updateUsername,
                keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(stringResource(R.string.label_password)) },
                value = viewModel.password,
                onValueChange = viewModel::updatePassword,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (canSubmit && !viewModel.loading) viewModel.login()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(6.dp))

            LoadingButton(
                onClick = viewModel::login,
                loading = viewModel.loading,
                enabled = canSubmit,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.action_login))
            }
        }
    }

}