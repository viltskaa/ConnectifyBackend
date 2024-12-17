package com.flagman.connectify.services

import com.itextpdf.io.font.FontProgramFactory
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName.*
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.opencsv.CSVWriter
import org.apache.commons.lang3.RandomStringUtils
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.FileOutputStream
import java.io.FileWriter
import java.sql.Date
import javax.swing.text.StyleConstants.FontConstants

@Service
class ExportService(
    private val messageService: MessageService
) {
    fun toCsv(chatId: Long): String {
        val filepath = "report/${generateFilename()}.csv"
        val messages = messageService.getAllMessagesByChatId(chatId)
        val writer = CSVWriter(FileWriter(filepath))

        val headers = arrayOf("id", "text", "time", "reply", "author")
        writer.writeNext(headers)

        for (message in messages) {
            var row = arrayOf(
                message.id.toString(),
                message.message,
                message.time,
                message.replyText,
                message.author,
            )
            writer.writeNext(row)
        }

        writer.close()
        return filepath
    }

    fun toPdf(chatId: Long): String {
        val filepath = "report/${generateFilename()}.csv"
        val pdfWriter = PdfWriter(filepath)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)
        val messages = messageService.getAllMessagesByChatId(chatId)

        val res = ClassPathResource("fonts/Arial.ttf")
        val fontProgram = FontProgramFactory.createFont(res.contentAsByteArray)
        val font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H)

        document.add(Paragraph("Сообщения").setFont(font))

        messages.forEach { message ->
            val row = "id: ${message.id} " +
                    "text: ${message.message} " +
                    "time: ${message.time} " +
                    "reply: ${message.replyText} " +
                    "author: ${message.author}"
            document.add(Paragraph(row).setFont(font))
        }

        document.close()
        return filepath
    }

    fun toExcel(chatId: Long): String {
        val filepath = "report/${generateFilename()}.csv"
        val messages = messageService.getAllMessagesByChatId(chatId)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Сообщения")

        val headerRow = sheet.createRow(0)
        arrayOf("id", "text", "time", "reply", "author").forEachIndexed { index, item ->
            headerRow.createCell(index).setCellValue(item)
        }

        messages.forEachIndexed { index, message ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(message.id.toString())
            row.createCell(1).setCellValue(message.message)
            row.createCell(2).setCellValue(message.time)
            row.createCell(3).setCellValue(message.replyText)
            row.createCell(4).setCellValue(message.author)
        }

        val fileOut = FileOutputStream(filepath)
        workbook.write(fileOut)
        workbook.close()
        fileOut.close()

        return filepath
    }

    private fun generateFilename(): String = RandomStringUtils.random(20, true, true);
}