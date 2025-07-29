package com.example.digipostcard.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.digipostcard.model.PostCard
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardListScreen(
    cards: List<PostCard>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onEdit: (PostCard) -> Unit,
    onDelete: (String) -> Unit,
    onAddNew: () -> Unit
) {
    val filteredCards = cards.filter {
        it.message.contains(searchText, ignoreCase = true) ||
                it.senderName.contains(searchText, ignoreCase = true) ||
                it.receiverName.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "📬 PostCardsView Dashboard",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            OutlinedTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            PostCardList(cards = filteredCards, onEdit = onEdit, onDelete = onDelete)
        }
    }
}

@Composable
fun PostCardList(
    cards: List<PostCard>,
    onEdit: (PostCard) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(cards) { card ->
            PostCardItem(
                card = card,
                onEdit = { onEdit(card) },
                onDelete = { onDelete(card.id) }
            )
        }
    }
}


@Composable
fun PostCardItem(
    card: PostCard,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = try {
                Color(card.hexColor.toColorInt())
            } catch (e: Exception) {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Text(
                text = "📮 Postcard",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.DarkGray, thickness = 1.dp)

            // Sender & Receiver Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "From:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = card.senderName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = card.senderAddress,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "To:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = card.receiverName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = card.receiverAddress,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.DarkGray, thickness = 1.dp)

            // Message Section
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "✉️ Message:",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = card.message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Color Code Info
            Text(
                text = "🎨 Background Color: ${card.hexColor}",
                style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Row
            Row {
                Text(
                    text = "✏️ Edit",
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .clickable(onClick = onEdit),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "🗑️ Delete",
                    modifier = Modifier.clickable(onClick = onDelete),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}
