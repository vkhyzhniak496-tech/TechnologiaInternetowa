package com.example.technologiainternetowa
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Importy Twoich plików:
import com.example.technologiainternetowa.presentation.tramList.TramItem
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalUriHandler
@Composable
fun App() {
    MaterialTheme {
        // Używamy Twojego nowego ViewModelu, który sam ogarnia RemoteDataSource
        val viewModel = remember { TramViewModel() }
        val trams by viewModel.trams.collectAsState()
        val uriHandler = LocalUriHandler.current

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = "Technologia internetowa ver. 1.228",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Usunięto "count ="
                    items(trams) { tram ->
                        TramItem(
                            tram = tram,
                            onFavoriteClick = { id ->
                                viewModel.toggleFavorite(id)
                            },
                        ) { line ->
                            // 3. Po kliknięciu budujemy link ZTM i odpalamy przeglądarkę na telefonie!
                            val url = "https://www.wtp.waw.pl/rozklady-jazdy/?wtp_md=3&wtp_ln=$line"
                            uriHandler.openUri(url)
                        }
                    }
                }
            }
        }
    }
}