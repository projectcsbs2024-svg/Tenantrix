package com.global.tenantrix.util

import android.content.Context
import android.graphics.*
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {

    fun generateRentReceipt(
        context: Context,
        tenantName: String,
        propertyName: String,
        month: String,
        year: Int,
        rentAmount: Double,
        amountPaid: Double,
        paymentMethod: String,
        paymentDate: String,
        notes: String? = null,
        fineAmount: Double = 0.0
    ): File? {

        val pdfWidth = 595   // A4 size (points)
        val pdfHeight = 842

        val document = android.graphics.pdf.PdfDocument()
        val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(pdfWidth, pdfHeight, 1).create()
        val page = document.startPage(pageInfo)

        val canvas = page.canvas
        val titlePaint = Paint().apply {
            textSize = 22f
            color = Color.BLACK
            typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        }

        val textPaint = Paint().apply {
            textSize = 16f
            color = Color.BLACK
        }

        var y = 60

        canvas.drawText("TENANTRIX RENT RECEIPT", 160f, y.toFloat(), titlePaint)
        y += 40

        canvas.drawText("Tenant Name: $tenantName", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Property: $propertyName", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Month: $month $year", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Rent Amount: ₹$rentAmount", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Amount Paid: ₹$amountPaid", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Payment Method: $paymentMethod", 40f, y.toFloat(), textPaint); y += 30
        canvas.drawText("Payment Date: $paymentDate", 40f, y.toFloat(), textPaint); y += 40

        if (fineAmount > 0) {
            canvas.drawText("Fine Applied: ₹$fineAmount", 40f, y.toFloat(), textPaint)
            y += 30
        }

        if (!notes.isNullOrEmpty()) {
            canvas.drawText("Notes: $notes", 40f, y.toFloat(), textPaint)
            y += 30
        }

        canvas.drawLine(40f, (y + 20).toFloat(), 550f, (y + 20).toFloat(), Paint().apply { color = Color.LTGRAY })

        y += 60

        canvas.drawText("Signature ____________________", 300f, y.toFloat(), textPaint)

        document.finishPage(page)

        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "TenantrixReceipts")
        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, "RentReceipt_${tenantName}_${month}_$year.pdf")

        return try {
            val output = FileOutputStream(file)
            document.writeTo(output)
            document.close()
            output.close()

            Toast.makeText(context, "PDF saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
