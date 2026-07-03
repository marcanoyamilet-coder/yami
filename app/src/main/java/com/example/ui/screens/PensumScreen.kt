package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.mock.Career
import com.example.data.mock.UnimarData

@Composable
fun PensumScreen(
    modifier: Modifier = Modifier
) {
    var selectedCareerId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF, 0xF9, 0xF5))
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0x0F, 0x2C, 0x59)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Oferta Académica UNIMAR",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Explora los planes de estudio y pensum detallado de nuestras prestigiosas carreras profesionales.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // List of Careers
        items(UnimarData.careers) { career ->
            CareerItemCard(
                career = career,
                isExpanded = selectedCareerId == career.id,
                onToggleExpand = {
                    selectedCareerId = if (selectedCareerId == career.id) null else career.id
                }
            )
        }
    }
}

@Composable
fun CareerItemCard(
    career: Career,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpand() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Main Top Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = career.name,
                        color = Color(0x0F, 0x2C, 0x59),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = career.degree,
                        color = Color(0xFF, 0x6F, 0x00),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Duración: ${career.duration}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expandir",
                    tint = Color(0x0F, 0x2C, 0x59)
                )
            }

            // Expanded Study Plan details
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Divider(color = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.padding(bottom = 12.dp))
                    
                    Text(
                        text = "Descripción del Perfil:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0x0F, 0x2C, 0x59)
                    )
                    Text(
                        text = career.description,
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    Text(
                        text = "Estructura Curricular (Pensum):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0x0F, 0x2C, 0x59),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Display semesters/years
                    career.semesters.forEach { semester ->
                        SemesterSection(semester = semester)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SemesterSection(semester: com.example.data.mock.Semester) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF, 0xF5, 0xEE)) // soft orange paper background
            .padding(12.dp)
    ) {
        Text(
            text = semester.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF, 0x6F, 0x00) // Wavy orange accent
        )
        Spacer(modifier = Modifier.height(6.dp))
        
        semester.subjects.forEach { subject ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = Color(0x0F, 0x2C, 0x59).copy(alpha = 0.5f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = subject.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        if (subject.code.isNotEmpty()) {
                            Text(
                                text = "Código: ${subject.code}",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                Text(
                    text = "${subject.credits} UC",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0x0F, 0x2C, 0x59)
                )
            }
        }
    }
}
