package ru.melolchik.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import ru.melolchik.vknewsclient.ui.theme.AuthState
import ru.melolchik.vknewsclient.ui.theme.LoginScreen
import ru.melolchik.vknewsclient.ui.theme.MainScreen
import ru.melolchik.vknewsclient.ui.theme.MainViewModel
import ru.melolchik.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel : MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)
                when(authState.value){
                    AuthState.Authorized -> MainScreen()
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }

    @Composable
    fun Test() {
        ModalNavigationDrawer(drawerContent = {
            val items = listOf(Icons.Default.Close, Icons.Default.Clear, Icons.Default.Call)

            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        selected = false,
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(text = item.name) },
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
    fun TestScaffold() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Top App Bar") },
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


        ) {
            Text(
                text = "Scafold content",
                modifier = Modifier.padding(it)
            )
        }
    }

}