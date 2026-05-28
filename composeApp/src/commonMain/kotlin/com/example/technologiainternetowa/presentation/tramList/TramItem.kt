package com.example.technologiainternetowa.presentation.tramList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.technologiainternetowa.domain.model.Tram

// 🌟 NATYWNE I NIEZAWODNE ZASOBY JETPACK COMPOSE MULTIPLATFORM:
import org.jetbrains.compose.resources.painterResource
import technologiainternetowa.composeapp.generated.resources.* @Composable
fun TramItem(
    tram: Tram,
    onFavoriteClick: (String) -> Unit,
    onLineClick: (String) -> Unit
) {
    // 🚋 Mapujemy pole 'type' z bazy danych bezpośrednio na wygenerowany obiekt zasobu z folderu drawable
// 🚋 Mapujemy zasób na podstawie nazwy pliku ukrytej w adresie URL
// 🚋 Super-bezpieczne mapowanie zasobów (sprawdza i URL, i nazwę, ignorując wielkość liter)
    val url = tram.imageUrl.lowercase()
    val name = tram.name.lowercase()

    val imageRes = when {
        url.contains("swing") || name.contains("swing") -> Res.drawable.swing
        url.contains("jazz") || name.contains("jazz") -> Res.drawable.jazz_duo
        url.contains("konstal") || name.contains("konstal") || name.contains("105na") -> {
            // Dodatkowy warunek, żeby odróżnić zwykłego Konstala od Tygrysa
            if (url.contains("tygrys") || name.contains("tygrys")) Res.drawable.tygrys
            else Res.drawable.konstal105
        }
        url.contains("hyundai") || name.contains("hyundai") || url.contains("140n") -> Res.drawable.hyundai
        url.contains("tygrys") || name.contains("tygrys") -> Res.drawable.tygrys
        else -> Res.drawable.compose_multiplatform
    }
    Card(
        modifier = Modifier
            .width(320.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.LightGray)
            ) {

                // 🎯 W 100% stabilny, natywny komponent graficzny
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = tram.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(8.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Text(
                        text = "Dostępny na liniach:",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 12.dp)
                    )

                    tram.lines.forEach { line ->
                        Surface(
                            color = Color(0xFFFFD700),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clickable { onLineClick(line) }
                        ) {
                            Text(
                                text = line,
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            IconButton(
                onClick = { onFavoriteClick(tram.id) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = if (tram.favorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (tram.favorite) Color.Yellow else Color.LightGray
                )
            }

            Text(
                text = tram.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}