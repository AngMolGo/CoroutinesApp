package com.example.coroutinesapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Realiza una tarea síncrona y cancela en cualquier instante su ejecución
 * Crea dos contadores de tiempo a la aplicación.
 * Anida las subrutinas del contador que pueden estar dentro de funciones suspend
 * Los dos contadores deberán estar sincronizados y se ejecutará el segundo al terminar la ejecución del primero.
 * Agrega un tercer botón que pueda cancelar los dos procesos de los contadores en cualquier instante.
 * Clases y recursos necesarios
 * Ocupa nuevamente la aplicación de Corrutinas.
 */

class MainViewModel : ViewModel() {

    var resultState by mutableStateOf("")
        private set

    var countTime by mutableIntStateOf(0)
        private set

    private var oneCount by mutableStateOf(false)

    fun fetchData(){

        val job1 = viewModelScope.launch {
            for (i in 1..5){
                delay(1000)
                countTime = i
            }
            oneCount = true
        }

        val job2 = viewModelScope.launch {
            delay(5000)
            resultState = "Respuesta obtenida de la web"
        }

        if (oneCount){
            job1.cancel()
            oneCount = false
        }
    }

    // Esta función bloquea el hilo principal
    fun bloqueoApp(){
        Thread.sleep(5000)
        resultState = "Respuesta obtenida de la web"
    }



}