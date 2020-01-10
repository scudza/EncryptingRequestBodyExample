package com.example.encryptingrequestbody

import androidx.lifecycle.ViewModel

class Viewmodel(val network: HttpClient) : ViewModel() {

    fun send() {
        network.sendTransaction(Transaction("Jarred Martin", "1234567890xx", 50.0))
    }

}