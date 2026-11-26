package fr.exemple.profil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val couleursClaires = lightColorScheme(
    primary = Color(0xFF215B51),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC9EEE1),
    onPrimaryContainer = Color(0xFF10382E),
    background = Color(0xFFF0F4F1),
    onBackground = Color(0xFF182620),
    surface = Color(0xFFFFFDF8),
    onSurface = Color(0xFF182620),
    surfaceVariant = Color(0xFFE2EBE5),
    onSurfaceVariant = Color(0xFF42534C),
    outline = Color(0xFF65786D)
)

private val couleursSombres = darkColorScheme(
    primary = Color(0xFF9CD7C6),
    onPrimary = Color(0xFF07372E),
    primaryContainer = Color(0xFF244C42),
    onPrimaryContainer = Color(0xFFD0F3E7),
    background = Color(0xFF121B17),
    onBackground = Color(0xFFE2EDE6),
    surface = Color(0xFF1F2A24),
    onSurface = Color(0xFFE2EDE6),
    surfaceVariant = Color(0xFF304139),
    onSurfaceVariant = Color(0xFFC2D2C8),
    outline = Color(0xFF92A69A)
)

private data class Projet(val nom: String, val description: String, val technologie: String)

private data class Profil(
    val nom: String,
    val role: String,
    val description: String,
    val disponibilite: String,
    val competences: List<String>,
    val projets: List<Projet>,
    val email: String
)

private val camille = Profil(
    nom = "Camille Laurent",
    role = "Développeuse Android junior",
    description = "Je crée des applications mobiles accessibles et faciles à prendre en main.",
    disponibilite = "Disponible pour un stage à Lyon",
    competences = listOf("Kotlin", "Jetpack Compose", "Git", "Material 3"),
    projets = listOf(
        Projet("Carnet de randonnée", "Fiche de parcours avec distance, durée et difficulté.", "Jetpack Compose"),
        Projet("Fiche Profil développeur", "Écran de présentation avec cartes et thèmes clair et sombre.", "Material 3")
    ),
    email = "camille@example.com"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProfil {
                EcranProfil(camille, onContact = {
                    // Une future version pourra ouvrir un client de messagerie.
                })
            }
        }
    }
}

@Composable
private fun ThemeProfil(sombre: Boolean = isSystemInDarkTheme(), contenu: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (sombre) couleursSombres else couleursClaires,
        content = contenu
    )
}

@Composable
private fun EcranProfil(profil: Profil, onContact: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { margesSysteme ->
        Box(
            modifier = Modifier.fillMaxWidth().padding(margesSysteme),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.widthIn(max = 600.dp).fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { EnteteProfil(profil) }
                item { TitreSection("Compétences") }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(profil.competences) { competence -> Pastille(competence) }
                    }
                }
                item { TitreSection("Projets") }
                items(profil.projets) { projet -> CarteProjet(projet) }
                item { TitreSection("Contact") }
                item { ZoneContact(profil.email, onContact) }
            }
        }
    }
}

@Composable
private fun EnteteProfil(profil: Profil, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.avatar_camille),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp).clip(CircleShape)
                    )
                    Surface(
                        modifier = Modifier.align(Alignment.BottomEnd).size(16.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Box(Modifier.padding(3.dp)) {
                            Surface(
                                modifier = Modifier.size(10.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                                content = {}
                            )
                        }
                    }
                }
                Column {
                    Text(
                        profil.nom,
                        modifier = Modifier.semantics { heading() },
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        profil.role,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(profil.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            Pastille(profil.disponibilite)
        }
    }
}

@Composable
private fun TitreSection(titre: String, modifier: Modifier = Modifier) {
    Text(
        text = titre,
        modifier = modifier.padding(top = 6.dp).semantics { heading() },
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun Pastille(texte: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            texte,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun CarteProjet(projet: Projet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(projet.nom, style = MaterialTheme.typography.titleSmall)
            Text(
                projet.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                projet.technologie,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ZoneContact(email: String, onContact: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("Adresse e-mail") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onContact, modifier = Modifier.fillMaxWidth()) {
            Text("Contacter Camille")
        }
    }
}

@Preview(name = "Profil clair", widthDp = 390, heightDp = 820, showBackground = true)
@Composable
private fun ApercuClair() {
    ThemeProfil(sombre = false) { EcranProfil(camille, onContact = {}) }
}

@Preview(name = "Profil sombre", widthDp = 390, heightDp = 820, showBackground = true)
@Composable
private fun ApercuSombre() {
    ThemeProfil(sombre = true) { EcranProfil(camille, onContact = {}) }
}
