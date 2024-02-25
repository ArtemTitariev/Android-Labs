package com.example.lr3

import com.example.lr3.currencyConversion.CurrencyConverter
import com.example.lr3.providers.ExchangeRateProvider
import com.example.lr3.providers.ManualExchangeRateProvider

fun main() {
    val provider: ExchangeRateProvider = ManualExchangeRateProvider();
    val exchangeRates = provider.getExchangeRatesNames()

    println("Available currencies: \n${exchangeRates.joinToString(",\t")}")

    print("Enter the source currency: ")
    var fromCurrency = ""
    try {
        fromCurrency = getCurrencyInput()
    }
    catch (e: Exception) {
        println(e.message)
        return
    }

    print("Enter amount: ")
    val amount : Double?
    try {
        amount = readln().toDouble()
    } catch (e: Exception) {
        println("You entered not a number!")
        return
    }

    if (amount < 0) {
        println("Invalid amount")
        return
    }

    print("Enter the target currency: ")
    var toCurrency = ""
    try {
        toCurrency = getCurrencyInput()
    }
    catch (e: Exception) {
        println(e.message);
        return;
    }

    val result : Double
    try {
    result = CurrencyConverter(provider)
        .setFromCurrency(fromCurrency)
        .setToCurrency(toCurrency)
        .setAmount(amount)
        .convert()
    } catch (e: Exception) {
        println(e.message)
        return
    }

    println("$amount $fromCurrency is equal to $result $toCurrency")
}

fun getCurrencyInput(): String = readlnOrNull()?.uppercase() ?: throw Exception("Incorrect currency!")