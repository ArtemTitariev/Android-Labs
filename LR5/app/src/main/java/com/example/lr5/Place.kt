package com.example.lr5

data class Place (
         val name: String,
         val description: String,
         val address: String,
         val costOfVisit: Double
    ) {
    init {
        // IllegalArgumentException
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(description.isNotEmpty()) { "Description cannot be empty" }
        require(address.isNotEmpty()) { "Address cannot be empty" }
        require(costOfVisit >= 0) { "Cost of visit cannot be negative" }
    }
}