package com.example.dailynews


object ColorPicker {
    val colors =
        arrayOf("#0cf5c6","#0cf50c","#34d5eb","#000000","#0d52db","#ff0505","#9e0000")
    var colorIndex = 1
    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }
}