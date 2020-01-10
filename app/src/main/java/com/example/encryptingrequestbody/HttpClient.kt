package com.example.encryptingrequestbody

import android.util.Base64
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class HttpClient {

    init {
        System.loadLibrary("native-lib")
    }

    external fun clientSecret(): String

    val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e(HttpClient::class.java.name, e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            Log.d(HttpClient::class.java.name, response.toString())
        }
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .build()

    fun sendTransaction(transaction: Transaction) {
        val body = JSONObject()
        body.put("name", transaction.accountHolder)
        body.put("account_number", transaction.accountNumber)
        body.put("amount", transaction.amount)
        try {
            run(body)
        } catch (e: Exception) {
            Log.e(HttpClient::class.java.name, e.toString())
        }
    }

    @Throws(IOException::class)
    fun run(json: JSONObject) {
        val bodyString = json.toString()
        val timeStamp = System.currentTimeMillis()
        val auth = timeStamp.toString() + URL + bodyString
        val hmac = getHmacHash(auth, clientSecret())
        val body = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .post(body)
            .addHeader("authorisation", hmac)
            .url(URL)
            .build()

        request.method
        client.newCall(request)
            .enqueue(callback)
    }

    @Throws(Exception::class)
    private fun getHmacHash(dataToHash: String, secretKey: String): String {
        val shaHasher = Mac.getInstance("HmacSHA256")
        shaHasher.init(SecretKeySpec(secretKey.toByteArray(), "HmacSHA256"))
        val hash = shaHasher.doFinal(dataToHash.toByteArray())
        val hmac = Base64.encodeToString(hash, Base64.NO_WRAP)
        Log.d(HttpClient::class.java.name, dataToHash)
        Log.d(HttpClient::class.java.name, secretKey)
        Log.d(HttpClient::class.java.name, hmac)
        return hmac
    }

    companion object {
        const val URL = "https://hmacexample.api.jarredmartin.me/v1/user/transfer"
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

    }
}