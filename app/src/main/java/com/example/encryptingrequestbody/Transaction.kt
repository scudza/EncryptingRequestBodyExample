package com.example.encryptingrequestbody

data class Transaction(
    val accountHolder: String,
    val accountNumber: String,
    val amount: Double
)