package com.example.lr3.providers

class ManualExchangeRateProvider : ExchangeRateProvider {

    private var exchangeRates : Map<String, Double> = mapOf(
        "USD" to 1.0,
        "UAH" to 31.0,
        "EUR" to 0.86,
        "GBP" to 0.73, // фунт
        "PLN" to 3.99,  // злотий
        "JPY" to 109.85, // єна
        "AUD" to 1.35,   // австралійський долар
    )

    override fun getExchangeRates(): Map<String, Double> {
        return exchangeRates;
    }

    override fun getExchangeRatesNames(): Set<String> {
        return this.exchangeRates.keys;
    }
}