package br.avcaliani.dmesh.rabbit


import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Value("\${rabbit.queue}")
    private lateinit var queueName: String

    @Value("\${rabbit.exchange}")
    private lateinit var exchange: String

    @Value("\${rabbit.routingKey}")
    private lateinit var routingKey: String

    @Bean
    fun queue() = Queue(queueName, false)

    @Bean
    fun exchange() = DirectExchange(exchange)

    @Bean
    fun binding(queue: Queue, exchange: DirectExchange): Binding =
        BindingBuilder.bind(queue).to(exchange).with(routingKey)

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = producerJackson2MessageConverter()
        return rabbitTemplate
    }
}