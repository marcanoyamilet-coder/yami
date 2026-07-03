package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.mock.CampusLocation
import com.example.data.mock.UnimarData

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier
) {
    var selectedLocation by remember { mutableStateOf<CampusLocation?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF, 0xF9, 0xF5))
            .padding(horizontal = 16.dp)
    ) {
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Welcome Header Banner
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0x0F, 0x2C, 0x59)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Recorrido Virtual UNIMAR",
                    color = Color(0xFF, 0xD7, 0x00),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Galería del Campus Universitario",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Explora las instalaciones y áreas verdes donde harás vida académica, ubicadas en el hermoso Valle del Espíritu Santo en la Isla de Margarita.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid View of Campus Locations
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(UnimarData.campusLocations) { location ->
                CampusGridItem(
                    location = location,
                    onClick = { selectedLocation = location }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // Detail Pop-up Dialog
    if (selectedLocation != null) {
        val loc = selectedLocation!!
        AlertDialog(
            onDismissRequest = { selectedLocation = null },
            title = {
                Text(
                    text = loc.name,
                    fontWeight = FontWeight.Black,
                    color = Color(0x0F, 0x2C, 0x59)
                )
            },
            text = {
                Column {
                    // Stylized canvas image drawing matching category
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFF, 0x6F, 0x00), Color(0x0F, 0x2C, 0x59))
                                )
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = when (loc.category) {
                                    "Académico" -> Icons.Default.School
                                    "Área Social" -> Icons.Default.Landscape
                                    "Estudio" -> Icons.Default.Book
                                    "Eventos" -> Icons.Default.Campaign
                                    "Naturaleza" -> Icons.Default.Park
                                    else -> Icons.Default.SportsBasketball
                                },
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = loc.category.uppercase(),
                                color = Color(0xFF, 0xD7, 0x00),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Descripción de la Instalación:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0x0F, 0x2C, 0x59)
                    )
                    Text(
                        text = loc.description,
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Guía visual: ${loc.imageDescription}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedLocation = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x0F, 0x2C, 0x59))
                ) {
                    Text("CERRAR")
                }
            }
        )
    }
}

@Composable
fun CampusGridItem(
    location: CampusLocation,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Visual top portion with category background gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x0F, 0x2C, 0x59).copy(alpha = 0.85f),
                                Color(0x0F, 0x2C, 0x59).copy(alpha = 0.7f)
                            )
                        )
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (location.category) {
                        "Académico" -> Icons.Default.School
                        "Área Social" -> Icons.Default.Landscape
                        "Estudio" -> Icons.Default.Book
                        "Eventos" -> Icons.Default.Campaign
                        "Naturaleza" -> Icons.Default.Park
                        else -> Icons.Default.SportsBasketball
                    },
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(36.dp)
                )
            }

            // Bottom title and category description
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = location.category,
                    color = Color(0xFF, 0x6F, 0x00),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = location.name,
                    color = Color(0x0F, 0x2C, 0x59),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}
