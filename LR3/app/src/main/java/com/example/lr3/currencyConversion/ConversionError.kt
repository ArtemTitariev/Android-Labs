package com.example.lr3.currencyConversion

sealed class ConversionError(message: String) : Exception(message) {
    object UnknownCurrency : ConversionError("Unknown currency")
    object InvalidAmount : ConversionError("Invalid amount")
}