package com.example.housemanager;

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.HostnameVerifier

class Connect_to_Backend {
    private lateinit var mSocket: Socket
    private val TAG = "Connect_to_Backend" // 로그를 구분하기 위한 TAG 설정

    init {
        try {/*
            // SSL/TLS 인증서 무시
            val trustManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), java.security.SecureRandom())

            val okHttpClient = OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustManager)
                .hostnameVerifier { _, _ -> true }
                .build()


            // IO.Options 인스턴스에 OkHttpClient 설정을 적용
            val options = IO.Options()
            options.callFactory = okHttpClient
            options.webSocketFactory = okHttpClient*/


            // 서버 URL 설정
            mSocket = IO.socket("https://10.0.2.2:5000")
            try {
                Log.d(TAG, "서버 연결시도중...")
                mSocket.connect()
                mSocket.on(Socket.EVENT_CONNECT) {
                    Log.d(TAG, "서버에 실제로 연결됨")
                }.on(Socket.EVENT_CONNECT_ERROR) { args ->
                    if (args[0] is Exception) {
                        val e = args[0] as Exception
                        Log.e(TAG, "연결 에러 발생: ", e)
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "연결 실패: ", e)
            }

            // 서버로부터 메시지 받기
            mSocket.on("receive_message") { args ->
                Log.d(TAG, "최초메시지: " + args[0])
                if (args[0] is Boolean) {
                    val success = args[0] as Boolean
                    handleLoginResponse(success)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "서버 연결 실패: ", e)  // 예외와 함께 로그를 남깁니다.
        }
    }

    fun login(username: String, password: String) {
        val data = JSONObject()
        data.put("username", username)
        data.put("password", password)
        mSocket.emit("login", data)
    }

    private fun handleLoginResponse(success: Boolean) {
        if (success) {
            // 로그인 성공 처리
            println("Login successful")
        } else {
            // 로그인 실패 처리
            println("Login failed")
        }
    }

    fun disconnect() {
        mSocket.disconnect()
    }
}