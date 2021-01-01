package br.avcaliani.dmesh.component.order.model

data class Product(
    val id: String = "",
    val description: String = "",
    val quantity: Long = 0,
    val unitPrice: Double = 0.0,
    val price: Double = quantity * unitPrice
)
