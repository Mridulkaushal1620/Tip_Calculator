package com.example.tipcalculator2

import junit.framework.TestCase.assertEquals
import java.text.NumberFormat
import org.junit.Assert
import org.junit.Test


class Tipcalculator2 {
    @Test
    fun cal() {

        val amount = 10.00
        val tip = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount, tip, false)
        assertEquals(expectedTip, actualTip)
    }
}