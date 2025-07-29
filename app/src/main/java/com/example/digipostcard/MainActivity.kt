package com.example.digipostcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.navigation.compose.*
import com.example.digipostcard.model.PostCard
import com.example.digipostcard.screen.PostCardFormScreen
import com.example.digipostcard.screen.PostCardListScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("postCards")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            var message by remember { mutableStateOf("") }
            var hexColor by remember { mutableStateOf("#FFEB3B") }
            var senderName by remember { mutableStateOf("") }
            var senderAddress by remember { mutableStateOf("") }
            var receiverName by remember { mutableStateOf("") }
            var receiverAddress by remember { mutableStateOf("") }
            var editingId by remember { mutableStateOf<String?>(null) }
            var cards by remember { mutableStateOf(listOf<PostCard>()) }
            var searchText by remember { mutableStateOf("") }

            val navController = rememberNavController()

            fun fetchCards() {
                collection.get().addOnSuccessListener { snapshot ->
                    cards = snapshot.documents.mapNotNull { it.toObject(PostCard::class.java) }
                }
            }

            fun saveCard() {
                val trimmedColor = if (hexColor.startsWith("#")) hexColor else "#$hexColor"
                val card = PostCard(
                    id = editingId ?: UUID.randomUUID().toString(),
                    message = message,
                    hexColor = trimmedColor,
                    senderName = senderName,
                    senderAddress = senderAddress,
                    receiverName = receiverName,
                    receiverAddress = receiverAddress
                )
                collection.document(card.id).set(card).addOnSuccessListener {
                    message = ""
                    hexColor = "#FFEB3B"
                    senderName = ""
                    senderAddress = ""
                    receiverName = ""
                    receiverAddress = ""
                    editingId = null
                    fetchCards()
                    navController.navigate("list")
                }
            }

            fun deleteCard(id: String) {
                collection.document(id).delete().addOnSuccessListener {
                    fetchCards()
                }
            }

            LaunchedEffect(Unit) {
                fetchCards()
            }

            MaterialTheme {
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        PostCardListScreen(
                            cards = cards,
                            searchText = searchText,
                            onSearchTextChange = { searchText = it },
                            onEdit = { card ->
                                message = card.message
                                hexColor = card.hexColor
                                senderName = card.senderName
                                senderAddress = card.senderAddress
                                receiverName = card.receiverName
                                receiverAddress = card.receiverAddress
                                editingId = card.id
                                navController.navigate("form")
                            },
                            onDelete = { deleteCard(it) },
                            onAddNew = { navController.navigate("form") }
                        )
                    }

                    composable("form") {
                        PostCardFormScreen(
                            message = message,
                            hexColor = hexColor,
                            senderName = senderName,
                            senderAddress = senderAddress,
                            receiverName = receiverName,
                            receiverAddress = receiverAddress,
                            onMessageChange = { message = it },
                            onColorChange = { hexColor = it },
                            onSenderNameChange = { senderName = it },
                            onSenderAddressChange = { senderAddress = it },
                            onReceiverNameChange = { receiverName = it },
                            onReceiverAddressChange = { receiverAddress = it },
                            onSubmit = { saveCard() },
                            isEditing = editingId != null,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
