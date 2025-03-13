package com.example.coroutinesapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Realiza una tarea síncrona y cancela en cualquier instante su ejecución
 * Crea dos contadores de tiempo a la aplicación.
 * Anida las subrutinas del contador que pueden estar dentro de funciones suspend
 * Los dos contadores deberán estar sincronizados y se ejecutará el segundo al terminar la ejecución del primero.
 * Agrega un tercer botón que pueda cancelar los dos procesos de los contadores en cualquier instante.
 */

class MainViewModel(
    initialCount: Int = 0,
    initialResult: String = ""
) : ViewModel() {

    var resultState by mutableStateOf("")
        private set

    var countTime by mutableIntStateOf(0)
        private set

    var countTime_2 by mutableIntStateOf(0)
            private set

    var btnCancelEnable by mutableStateOf(false)
        private set

    private var oneCount by mutableStateOf(false)

    private var job1: Job? = null
    private var job2: Job? = null

    fun fetchData() {
        // Cancela trabajos anteriores si existen
        job1?.cancel()
        job2?.cancel()
        btnCancelEnable = true
        countTime = 0
        countTime_2 = 0
        resultState = ""


        // Lanza la primera corrutina para el contador
        job1 = viewModelScope.launch {
            for (i in 1..5) {
                delay(1000)
                countTime = i
            }

            resultState = "Buscando..."

            // Lanza la segunda corrutina para obtener datos de la "web"
            job2 = viewModelScope.launch {
                for (i in 1..5) {
                    delay(1000)
                    countTime_2 = i
                }
                resultState = "[Respuesta obtenida de la web]"
            }

        }


    }

    fun cancelJobs() {
        job1?.cancel()
        job2?.cancel()
        btnCancelEnable = false
        countTime = 0
        countTime_2 = 0
        resultState = "Operación cancelada"

    }
}