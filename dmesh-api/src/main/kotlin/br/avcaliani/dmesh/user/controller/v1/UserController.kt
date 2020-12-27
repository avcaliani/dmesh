package br.avcaliani.dmesh.user.controller.v1

import br.avcaliani.dmesh.user.UserService
import br.avcaliani.dmesh.util.V1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$V1/me")
class UserController {

    @Autowired
    private lateinit var service: UserService

    @GetMapping
    fun me() = ResponseEntity.ok(service.myself()!!)
}
