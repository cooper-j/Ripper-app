package com.hexan.ripper.model

/**
 * Created by James Cooper on 11/04/2018.
 */

class Authentication(
        val access_token: String,
        val token_type: String,
        val refresh_token: String,
        val expires_in: Long,
        val scope: String)