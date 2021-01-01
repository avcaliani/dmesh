package br.avcaliani.dmesh.data

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.nio.file.Paths

@Service
class DataService {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${datalake}")
    private lateinit var datalake: String

    fun getFile(fileType: String, date: String): Resource {
        val filename = getFilename(fileType, date)
        val resource = UrlResource(Paths.get(filename).toUri())
        log.info("File: $filename")
        if (!resource.exists() || !resource.isReadable)
            throw FileNotFoundException(filename)
        return resource
    }

    private fun getFilename(fileType: String, date: String) = datalake +
            "/ORDER" +
            "/${fileType.trim().toUpperCase()}" +
            "/${date.trim()}" +
            "/order-${fileType.trim().toLowerCase()}-${date.trim()}.csv"

}