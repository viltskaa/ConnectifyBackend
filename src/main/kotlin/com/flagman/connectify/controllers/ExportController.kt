package com.flagman.connectify.controllers

import com.flagman.connectify.services.ExportService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/export")
class ExportController(
    private val exportService: ExportService
) {
    @GetMapping("/csv")
    fun csv(@RequestParam chatId: Long): ResponseEntity<FileSystemResource> {
        val report = exportService.toCsv(chatId)
        return prepareFileResponse(report, MediaType.TEXT_PLAIN)
    }

    @GetMapping("/pdf")
    fun pdf(@RequestParam chatId: Long): ResponseEntity<FileSystemResource> {
        val report = exportService.toPdf(chatId)
        return prepareFileResponse(report, MediaType.APPLICATION_PDF)
    }

    @GetMapping("/excel")
    fun excel(@RequestParam chatId: Long): ResponseEntity<FileSystemResource> {
        val report = exportService.toExcel(chatId)
        return prepareFileResponse(report, MediaType.APPLICATION_OCTET_STREAM)
    }

    private fun prepareFileResponse(
        filename: String,
        mediaType: MediaType
    ): ResponseEntity<FileSystemResource> {
        val file = File(filename)
        val headers = HttpHeaders()
        headers.contentType = mediaType
        headers.setContentDispositionFormData("attachment", filename)
        val entity = ResponseEntity(FileSystemResource(file), headers, HttpStatus.OK)
        file.deleteOnExit()
        return entity
    }
}