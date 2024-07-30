package com.example.playlistmakerjetpackcompose.settings.presentation.compose_fun

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmakerjetpackcompose.R
import com.example.playlistmakerjetpackcompose.settings.presentation.view_model.SettingsScreenViewModel
import com.example.playlistmakerjetpackcompose.ui.theme.YsMediumFamily
import com.example.playlistmakerjetpackcompose.ui.theme.YsRegularFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = koinViewModel(),
    switchTheme: (answer: Boolean) -> Unit,
) {
    val theme = viewModel.theme.observeAsState(true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = YsMediumFamily,
            fontSize = 22.sp,
            text = stringResource(R.string.settings_screen),
            modifier = Modifier
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 21.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = YsRegularFamily,
                text = stringResource(R.string.dark_theme),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.yp_blue),
                    checkedTrackColor = colorResource(id = R.color.yp_blue_light),
                    uncheckedTrackColor = colorResource(id = R.color.yp_light_grey),
                    uncheckedThumbColor = colorResource(id = R.color.grey_playlist),
                    uncheckedBorderColor = Color.White,
                ),
                checked = theme.value,
                onCheckedChange = {
                    viewModel.updateTheme(!theme.value)
                    switchTheme(it)
                },
                modifier = Modifier.scale(0.7f)
            )
        }

        RowButtonSettings(
            textButton = stringResource(R.string.share_app),
            idImageButton = R.drawable.ic_share,
            1,
            viewModel
        )
        RowButtonSettings(
            textButton = stringResource(R.string.email_support),
            idImageButton = R.drawable.ic_support,
            2,
            viewModel
        )
        RowButtonSettings(
            textButton = stringResource(R.string.user_policy),
            idImageButton = R.drawable.ic_back,
            3,
            viewModel
        )
    }
}

@Composable
private fun RowButtonSettings(
    textButton: String,
    idImageButton: Int,
    use: Int,
    viewModel: SettingsScreenViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 22.dp,
                horizontal = 16.dp
            )
            .clickable {
                when (use) {
                    1 -> viewModel.shareApp()
                    2 -> viewModel.sendToSupport()
                    3 -> viewModel.userPolicy()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = YsRegularFamily,
            text = textButton,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(
                id = idImageButton
            ), contentDescription = null
        )
    }
}