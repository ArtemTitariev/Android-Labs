package com.example.lr3.providers

interface ExchangeRateProvider {
    fun getExchangeRates(): Map<String, Double>

    fun getExchangeRatesNames(): Set<String>
}