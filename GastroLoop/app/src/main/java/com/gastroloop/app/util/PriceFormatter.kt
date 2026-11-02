
package com.gastroloop.app.util

import java.text.NumberFormat
import java.util.Locale

object PriceFormatter {

    private val costaRicaLocale = Locale.forLanguageTag("es-CR")

    fun format(price: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(costaRicaLocale)
        formatter.maximumFractionDigits = 0
        formatter.minimumFractionDigits = 0

        return formatter.format(price)
    }
}
