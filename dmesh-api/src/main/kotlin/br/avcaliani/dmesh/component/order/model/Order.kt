package br.avcaliani.dmesh.component.order.model

import java.util.*

data class Order(
    val date: Date = Date(),
    val customerId: String = "",
    val country: String = "",
    val products: List<Product> = emptyList()
)
