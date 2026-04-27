package com.example.technologiainternetowa.presentation.tramList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
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
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.technologiainternetowa.domain.model.Tram
import org.jetbrains.compose.resources.painterResource

@Composable
fun TramItem(
    tram: Tram,
    onFavoriteClick: (String) -> Unit,
    onLineClick: (String) -> Unit
) {
    // Stan widoczności paska (obsługuje hover na Desktopie i kliknięcie na Androidzie)
    var isVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(320.dp)
            .padding(8.dp)
            // Obsługa kliknięcia (ważne dla Androida, by pokazać pasek linii)
            .clickable { isVisible = !isVisible }
            // Obsługa najechania myszą (Desktop)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isVisible = true
                            PointerEventType.Exit -> isVisible = false
                        }
                    }
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Box pozwala na nakładanie elementów na siebie [cite: 14]
            Box( //
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(tram.imageRes),
                    contentDescription = tram.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // TO MUSI BYĆ TUTAJ - wewnątrz klamer Box
                androidx.compose.animation.AnimatedVisibility(
                    visible = isVisible,
                    modifier = Modifier.align(Alignment.BottomCenter) // Teraz na 100% zadziała
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(8.dp)
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
                                color = Color(0xFFFFD700), // Żółty kolor tablic
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

                // Przycisk ulubionych (gwiazdka) w prawym górnym rogu zdjęcia
                IconButton(
                    onClick = { onFavoriteClick(tram.id) },
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (tram.favorite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (tram.favorite) Color.Yellow else Color.White
                    )
                }
            }

            // Nazwa modelu pod zdjęciem
            Text(
                text = tram.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}