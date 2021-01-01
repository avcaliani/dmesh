package br.avcaliani.dmesh.data.controller.v1

import br.avcaliani.dmesh.data.DataService
import br.avcaliani.dmesh.util.V1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$V1/data")
class DataController {

    @Autowired
    private lateinit var service: DataService

    @GetMapping("/files/{fileType}/{date}")
    fun download(@PathVariable fileType: String, @PathVariable date: String): ResponseEntity<Resource> {
        val file = service.getFile(fileType, date)
        return ResponseEntity.ok()
            .header(CONTENT_DISPOSITION, "attachment; filename=\"${file.filename}\"")
            .contentLength(file.contentLength())
            .contentType(APPLICATION_OCTET_STREAM)
            .body(InputStreamResource(file.inputStream));
    }

}