package com.daniluk.covid_19.utils


//Добавление пробелов через 3 цифры с конца строки
fun String.addSpaceToString(): String {
    var outstr = StringBuffer(this).reverse().toString()
    outstr = outstr.replace(Regex("(.{3})"), "$1 ")
    outstr = StringBuffer(outstr).reverse().toString()
    return outstr
}
