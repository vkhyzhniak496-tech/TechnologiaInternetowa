package com.example.technologiainternetowa

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
// Importy Twoich plików:
import com.example.technologiainternetowa.utils.openBrowser
import com.example.technologiainternetowa.presentation.tramList.TramListViewModel
import com.example.technologiainternetowa.presentation.tramList.TramItem
import com.example.technologiainternetowa.data.dataSource.TramsLocalDataSource
import com.example.technologiainternetowa.data.repository.TramsLocalRepository
import com.example.technologiainternetowa.domain.useCase.GetAllTramsUseCase
import com.example.technologiainternetowa.domain.useCase.ToggleFavoriteUseCase
import androidx.compose.runtime.collectAsState
@Composable
@Preview
fun App() {
    MaterialTheme {
        // 1. Ręczne tworzenie zależności (bo ViewModel ich wymaga)
        val repository = remember { TramsLocalRepository() }
        val getAllTramsUseCase = remember { GetAllTramsUseCase(repository) }
        val toggleFavoriteUseCase = remember { ToggleFavoriteUseCase(repository) }

        // 2. Przekazujemy je do ViewModelu
        val viewModel = remember {
            TramListViewModel(
                getAllTramsUseCase = getAllTramsUseCase,
                toggleFavoriteUseCase = toggleFavoriteUseCase
            )
        }

        val uiState by viewModel.uiState.collectAsState()
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

                // 3. Wyświetlamy siatkę tramwajów (pulpit)
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp), // Kafelki będą się dopasowywać do szerokości ekranu
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.trams) { tram ->
                        TramItem(
                            tram = tram,
                            onFavoriteClick = { id -> viewModel.onFavoriteClick(id) },
                            onLineClick = { line ->
                                val url = "https://www.wtp.waw.pl/rozklady-jazdy/?wtp_dt=2026-04-27&wtp_md=3&wtp_ln=$line"
                                openBrowser(url) // To wywołuje Twój kod z BrowserUtils
                            }
                        )
                    }
                }
            }
        }
    }
}