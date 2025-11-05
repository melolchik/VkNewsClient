package ru.melolchik.vknewsclient.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SideEffectTest(number : MyNumber){
    Column {
        LazyColumn {
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
        number.a = 5

        LazyColumn {
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
    }

}

data class MyNumber(var a: Int)