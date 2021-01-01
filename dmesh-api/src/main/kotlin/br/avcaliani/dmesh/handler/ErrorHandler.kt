package br.avcaliani.dmesh.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.FileNotFoundException

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    private val log: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun generic(ex: Exception): ResponseEntity<*> {
        log.error("Unhandled Error: ${ex.message}", ex)
        return ResponseEntity(listOf(ex.message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException::class)
    fun fileNotFound(ex: Exception): ResponseEntity<*> {
        log.error("File not found! File: ${ex.message}", ex)
        return ResponseEntity(listOf(ex.message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgs(ex: Exception): ResponseEntity<*> {
        return ResponseEntity(listOf(ex.message), HttpStatus.NOT_ACCEPTABLE);
    }

}