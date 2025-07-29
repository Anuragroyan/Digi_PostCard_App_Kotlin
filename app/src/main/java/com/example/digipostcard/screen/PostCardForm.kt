package com.example.digipostcard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PostCardForm(
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
    isEditing: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hexColor,
            onValueChange = onColorChange,
            label = { Text("Hex Color (e.g. #FF5733)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senderName,
            onValueChange = onSenderNameChange,
            label = { Text("Sender Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senderAddress,
            onValueChange = onSenderAddressChange,
            label = { Text("Sender Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = receiverName,
            onValueChange = onReceiverNameChange,
            label = { Text("Receiver Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = receiverAddress,
            onValueChange = onReceiverAddressChange,
            label = { Text("Receiver Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update PostCard" else "Create PostCard")
        }
    }
}
