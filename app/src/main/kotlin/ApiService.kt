// ApiService.kt
package com.example.housemanager

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Header

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val permission: String
)

data class SignupRequest(val username: String, val password: String)
data class SignupResponse(val message: String)
data class ProtectedResponse(val logged_in_as: String)
data class RefreshResponse(val access_token: String, val current_user: String)

interface ApiService {
    @POST("/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/protected")
    fun getProtected(@Header("Authorization") token: String): Call<ProtectedResponse>

    @POST("/refresh")
    fun refresh(@Header("Authorization") token: String): Call<RefreshResponse>
}
