package me.night_brainiac.mocket.android.ui.profile.connect_wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.night_brainiac.mocket.android.R
import timber.log.Timber

@Preview(showBackground = true)
@Composable
fun ConnectWalletScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connect_wallet),
            contentDescription = stringResource(id = R.string.cd_connect_wallet),
            modifier = Modifier
                .size(height = 120.dp, width = 120.dp)
                .padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.connect_wallet_tv_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 40.dp, end = 40.dp),
            text = stringResource(id = R.string.connect_wallet_tv_subtitle),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
        Button(
            modifier = Modifier.padding(top = 64.dp),
            onClick = {
                // TODO Do WalletConnect DApp sign
                Timber.d("Wallet Connect")
            }
        ) {
            Text(text = stringResource(id = R.string.connect_wallet_bt_connect))
        }
    }
}
