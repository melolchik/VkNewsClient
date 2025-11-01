package ru.melolchik.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.melolchik.vknewsclient.ui.theme.MainScreen
import ru.melolchik.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {

               val authLauncher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }
                }

                authLauncher.launch(listOf(VKScope.WALL))
                MainScreen()

            }
        }
    }

@Composable
fun Test(){
    ModalNavigationDrawer(drawerContent = {
        val items = listOf(Icons.Default.Close, Icons.Default.Clear, Icons.Default.Call)

        ModalDrawerSheet {
            Spacer(Modifier.height(12.dp))
            items.forEach { item ->
                NavigationDrawerItem(
                    selected = false,
                    icon = { Icon(item, contentDescription = null) },
                    label = { Text(text = item.name)},
                    onClick = {}
                )
            }
        }
    }) {
        TestScaffold()
    }

}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TestScaffold()
    {
        Scaffold (
            topBar = {
                TopAppBar(title = { Text(text = "Top App Bar") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
            },
            bottomBar = {


                NavigationBar {
                    repeat(4) {
                        NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        })
                    }
                }
            }


        ){
            Text(text = "Scafold content",
                modifier = Modifier.padding(it))
        }
    }

}