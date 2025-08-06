package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isPremium: Boolean,
    onPremiumToggle: (Boolean) -> Unit,
    selectedTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
        
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Premium Features",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Premium Status",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = if (isPremium) "Access to all 7 smart templates" else "Access to 1 basic template",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Switch(
                            checked = isPremium,
                            onCheckedChange = { 
                                scope.launch {
                                    onPremiumToggle(it)
                                }
                            }
                        )
                    }
                }
            }
            
            if (isPremium) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "🎉 Premium Active",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "You have access to all smart templates:\n• BBQ Party (15 people)\n• Thanksgiving Dinner\n• Baby Essentials\n• Keto Shopping\n• Camping Trip\n• Quick Dinners Week",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
            
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "App Theme",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    if (!isPremium) {
                        Text(
                            text = "Premium themes (Dark Mode & Paper Notebook) available with premium subscription",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Column(
                        modifier = Modifier.selectableGroup(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val themes = if (isPremium) {
                            listOf(
                                AppTheme.MODERN_LIGHT to "Modern Light",
                                AppTheme.MODERN_DARK to "Modern Dark ⭐", 
                                AppTheme.PAPER to "Paper Notebook ⭐📝"
                            )
                        } else {
                            listOf(
                                AppTheme.MODERN_LIGHT to "Modern Light"
                            )
                        }
                        
                        themes.forEach { (theme, label) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (selectedTheme == theme),
                                        onClick = { 
                                            scope.launch {
                                                onThemeChange(theme)
                                            }
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (selectedTheme == theme),
                                    onClick = null
                                )
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "About Templates",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Smart templates come with pre-filled shopping lists tailored for specific occasions. Each template includes realistic quantities and proper categorization to make shopping faster.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}