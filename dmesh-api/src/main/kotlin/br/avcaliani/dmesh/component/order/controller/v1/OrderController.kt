package br.avcaliani.dmesh.component.order.controller.v1

import br.avcaliani.dmesh.component.order.OrderService
import br.avcaliani.dmesh.component.order.model.Order
import br.avcaliani.dmesh.util.V1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$V1/order")
class OrderController {

    @Autowired
    private lateinit var service: OrderService

    @PostMapping
    fun save(@RequestBody order: Order) = ResponseEntity.status(CREATED).body(service.save(order))

}