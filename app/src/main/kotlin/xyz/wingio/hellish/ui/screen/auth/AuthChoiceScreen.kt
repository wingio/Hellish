package xyz.wingio.hellish.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.ui.component.LabeledDivider
import xyz.wingio.hellish.ui.screen.auth.component.VerticalSegmentedButton
import xyz.wingio.hellish.ui.screen.main.MainScreen

class AuthChoiceScreen : Screen {

    @Composable
    override fun Content() {
        val authManager: AuthManager = koinInject()
        val navigator = LocalNavigator.currentOrThrow

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
            ) {
                VerticalSegmentedButton(
                    text = stringResource(R.string.action_register),
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }
                )

                VerticalSegmentedButton(
                    text = stringResource(R.string.action_sign_in),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navigator.push(LoginFormScreen())
                    }
                )
            }

            LabeledDivider(
                label = { Text(stringResource(R.string.label_or)) }
            )

            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    authManager.setPassedOnboarding(true)
                    navigator.parent!!.replace(MainScreen())
                }
            ) {
                Text(stringResource(R.string.action_skip_login))
            }
        }
    }

}