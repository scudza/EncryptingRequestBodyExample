package com.example.hmacrequestbody

data class Transaction(
    val accountHolder: String,
    val accountNumber: String,
    val amount: Double
)