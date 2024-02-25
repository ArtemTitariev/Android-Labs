package com.example.lr2

fun main(){
    var unitFrom = "";
    try {
        unitFrom = getUnitInput("Choose a unit of measure: ");
    }
    catch (e: Exception) {
        println(e.message);
        return;
    }

    print("Enter number: ");
    val number : Double?;
    try {
        number = readln().toDouble();
    } catch (e: Exception) {
        println("You entered not a number");
        return;
    }

    var unitTo = "";
    try {
        unitTo =
            getUnitInput("Select the unit of measurement to which you want to convert the number: ");
    }
    catch (e: Exception) {
        println(e.message);
        return;
    }

    val result = convertUnit(number, unitFrom, unitTo);

    println("Result: $number $unitFrom = $result $unitTo");
}

fun printUnit(message: String) {
    println(message);
    println("   millimeter -> mm");
    println("   centimeters -> cm");
    println("   meters -> m");
    println("   kilometers -> km");
}

fun checkUnit(unit: String) = unit in listOf<String>("mm", "cm", "dm", "m", "km");

fun getUnitInput(message: String): String {
    printUnit(message);

    print("Unit of measure: ");
    val unit: String? = readlnOrNull();

    if (unit == null || !checkUnit(unit)) {
        throw Exception("Incorrect unit of measure!");
    }

   return unit;
}

// map (
//  key = unitFrom,
//  value = map (
//      key = unitTo
//      value = factor
//   )
// )
fun convertUnit(value: Double, fromUnit: String, toUnit: String): Double {
    val conversionMap = mapOf(
        "mm" to mapOf("cm" to 0.1, "m" to 0.001, "km" to 0.000001),
        "cm" to mapOf("mm" to 10.0, "m" to 0.01, "km" to 0.00001),
        "m" to mapOf("mm" to 1000.0, "cm" to 100.0, "km" to 0.001),
        "km" to mapOf("mm" to 1000000.0, "cm" to 100000.0, "m" to 1000.0)
    );

    return conversionMap[fromUnit]?.get(toUnit)?.times(value) ?: 0.0;
}
