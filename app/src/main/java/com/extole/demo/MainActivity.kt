package com.extole.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.extole.android.sdk.Extole
import com.extole.demo.ui.theme.ExtoleDemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        GlobalScope.launch {
            val extole = Extole.init(
                "referral.moneybase.com", "extole-mobile-test",
                context = this@MainActivity
            )
            val (ctaZone, campaign) = extole.fetchZone("advocate_mobile_experience")
            Log.e("Extole", "Zone response: ${ctaZone}, campaign: ${campaign}")
            runOnUiThread {
                setContent {
                    ExtoleDemoTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                shareSubject = ctaZone?.get("sharing.email.subject")?.toString() ?: "ERROR: EXTOLE REQUEST DIDN'T WORK",
                                shareMessage = ctaZone?.get("sharing.email.message")?.toString() ?: "ERROR: EXTOLE REQUEST DIDN'T WORK",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(shareSubject: String, shareMessage: String, modifier: Modifier = Modifier) {
    Text(
        text = "Share Subject: $shareSubject!",
        modifier = modifier
    )
    Text(
        text = "Share Message: $shareMessage!",
        modifier = Modifier.padding(top = 150.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExtoleDemoTheme {
        Greeting("Demo Subject", "Demo Message")
    }
}