package com.example.digipostcard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardFormScreen(
    message: String,
    hexColor: String,
    senderName: String,
    senderAddress: String,
    receiverName: String,
    receiverAddress: String,
    onMessageChange: (String) -> Unit,
    onColorChange: (String) -> Unit,
    onSenderNameChange: (String) -> Unit,
    onSenderAddressChange: (String) -> Unit,
    onReceiverNameChange: (String) -> Unit,
    onReceiverAddressChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isEditing: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit PostCard" else "New PostCard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PostCardForm(
                message = message,
                hexColor = hexColor,
                senderName = senderName,
                senderAddress = senderAddress,
                receiverName = receiverName,
                receiverAddress = receiverAddress,
                onMessageChange = onMessageChange,
                onColorChange = onColorChange,
                onSenderNameChange = onSenderNameChange,
                onSenderAddressChange = onSenderAddressChange,
                onReceiverNameChange = onReceiverNameChange,
                onReceiverAddressChange = onReceiverAddressChange,
                onSubmit = onSubmit,
                isEditing = isEditing
            )
        }
    }
}
