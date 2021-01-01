package br.avcaliani.dmesh.component.order

import br.avcaliani.dmesh.component.order.model.Order
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OrderService {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var rabbit: RabbitTemplate

    @Value("\${rabbit.exchange}")
    private lateinit var exchange: String

    @Value("\${rabbit.routingKey}")
    private lateinit var routingKey: String

    fun save(order: Order): Order {
        validate(order)
        log.info("New Message: $order")
        rabbit.convertAndSend(exchange, routingKey, order)
        return order
    }

    private fun validate(order: Order) {
        if (order.customerId.trim() == "") throw IllegalArgumentException("Invalid Customer ID!")
        if (order.country.trim() == "") throw IllegalArgumentException("Invalid Country!")
        if (order.products.isEmpty()) throw IllegalArgumentException("Products not found for this order!")
        order.products.forEach {
            if (it.id.trim() == "") throw IllegalArgumentException("Invalid Product ID!")
            if (it.description.trim() == "") throw IllegalArgumentException("Invalid Product Description!")
            if (it.price < 0) throw IllegalArgumentException("Invalid Product Price!")
            if (it.quantity < 0) throw IllegalArgumentException("Invalid Product Quantity")
        }
    }

}