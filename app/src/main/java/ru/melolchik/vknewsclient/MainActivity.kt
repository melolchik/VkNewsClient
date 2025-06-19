package ru.melolchik.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.melolchik.vknewsclient.ui.theme.PostCard
import ru.melolchik.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertDialog(onDismissRequest = { },
                confirmButton = { TextButton(onClick = {}) {
                    Text(text = "Confirm")
                }},
                title = { Text(text = "Title")},
                text = { Text(text = "Message")},
                dismissButton = { TextButton(onClick = { }) {
                    Text(text = "Dismiss")
                }}

                )

//            VkNewsClientTheme {
//                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
//                    .fillMaxSize()
//                    .padding(8.dp)
//                ){
//                    PostCard()
//                }
//
//
//            }
        }
    }



    @Composable
    fun ColumnScope.TestText(text : String, count : Int)
    {
        repeat(count){
            Text(text = text, modifier = Modifier.weight(1f))
        }
    }

}