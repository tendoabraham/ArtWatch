package com.example.artwatch.Views.ui

import android.content.DialogInterface

interface Icommunicator {

    fun getMsg(risk: String,
               name: String,
               email: String,
               phone: String,
               otherContact: String,
               bestReach: String,
               desc: String,
               location: String
    )
}