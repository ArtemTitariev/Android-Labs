package com.example.lr3.currencyConversion

import com.example.lr3.providers.ExchangeRateProvider

class CurrencyConverter(private val exchangeRateProvider: ExchangeRateProvider) {

    private var amount: Double = 0.0
    private var fromCurrency: String = ""
    private var toCurrency: String = ""

    fun setAmount(value: Double): CurrencyConverter {
        amount = value
        return this
    }

    fun setFromCurrency(value: String): CurrencyConverter {
        fromCurrency = value.uppercase()
        return this
    }

    fun setToCurrency(value: String): CurrencyConverter {
        toCurrency = value.uppercase()
        return this
    }

    fun convert(): Double {
        return this.convert(this.amount, this.fromCurrency, this.toCurrency)
    }

    fun convert(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val exchangeRates = exchangeRateProvider.getExchangeRates()

        val rateFrom = exchangeRates[fromCurrency] ?: this.conversionError(fromCurrency)
        val rateTo = exchangeRates[toCurrency] ?: this.conversionError(toCurrency)

        if (amount < 0) {
            throw Exception("Invalid amount")
        }

        return amount * rateTo / rateFrom
    }

    private fun conversionError(currency: String): Nothing {
        throw Exception("Unknown currency: $currency")
    }
}