package com.example.housemanager;

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.content.Context

interface LoginCallback {
    fun onLoginResult(result: String)
}

class Connect_to_Backend {
    private lateinit var mSocket: Socket
    private val TAG = "Connect_to_Backend" // 로그를 구분하기 위한 TAG 설정
    var accessToken: String? = null
    var refreshToken: String? = null
    var permission: String? = null

    init {
        try {

            // 서버 URL 설정
            //mSocket = IO.socket("https://10.0.2.2:8000")
            mSocket = IO.socket("https://hm.jftt.kr") //For Real Docker Server
            //mSocket = IO.socket("https://10.0.2.2:5000") //For localhost TEST
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
            } catch (e: Exception) {
                Log.e(TAG, "연결 실패: ", e)
            }

            // 서버로부터 메시지 받기
            mSocket.on("receive_message") { args ->
                Log.d(TAG, "메시지: " + args[0])
                if (args[0] is Boolean) {
                    val success = args[0] as Boolean
                    handleLoginResponse(success)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "서버 연결 실패: ", e)  // 예외와 함께 로그를 남깁니다.
        }
    }


    fun login(context: Context, username: String, password: String, callback: LoginCallback) {
        val data = JSONObject()
        data.put("username", username)
        data.put("password", password)
        //mSocket.emit("login", data)
        Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()

        val loginRequest = LoginRequest(username = username, password = password)
        RetrofitInstance.api.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    accessToken = response.body()?.access_token
                    refreshToken = response.body()?.refresh_token
                    permission = response.body()?.permission
                    Log.d(
                        TAG,
                        "Login successful by $permission, access token: $accessToken, refresh token: $refreshToken"
                    )
                    // 토큰을 저장하고 보호된 경로에 접근할 때 사용
                    callback.onLoginResult("Successful by $permission")
                } else {
                    val loginResult = response.message()
                    callback.onLoginResult(loginResult)
                    Log.d(TAG, "Login failed: ${loginResult}")
                    Toast.makeText(context, "Login failed: ${loginResult}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val loginResult = t.message
                callback.onLoginResult(loginResult ?: "Unknown error")
                Log.e(TAG, "Login error: ${loginResult}")
                Toast.makeText(context, "Unknown error: ${loginResult}", Toast.LENGTH_SHORT).show()
            }
        })
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