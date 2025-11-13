package com.josrangel.jetpackdatastoreexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.josrangel.jetpackdatastoreexample.data.UserPreferences
import com.josrangel.jetpackdatastoreexample.ui.theme.JetpackDatastoreExampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackDatastoreExampleTheme {
                val darkModeFlow = remember { UserPreferences.getDarkMode(applicationContext) }
                val darkMode by darkModeFlow.collectAsState(initial = false)

                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        DarkModeToggle(
                            isDark = darkMode,
                            onToggle = {
                                lifecycleScope.launch {
                                    UserPreferences.setDarkMode(applicationContext, it)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkModeToggle(isDark: Boolean, onToggle: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dark Mode: ${if (isDark) "ON" else "OFF"}", color = if (isDark) Color.DarkGray else Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onToggle(!isDark) }) {
            Text("Toggle")
        }
    }
}

