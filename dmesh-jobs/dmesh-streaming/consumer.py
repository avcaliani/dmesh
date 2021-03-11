#!/usr/bin/env python3
# @script   consumer.py
# @author   Anthony Vilarim Caliani
# @contact  github.com/avcaliani
#
# @python   3.9.*
#
# @description
# DMesh RabbitMQ consumer.
#
# @usage
# ./consumer.py
from pika import BlockingConnection, ConnectionParameters

def callback(ch, method, properties, body):
    print("\n\n [x] Received %r" % body)

def main():
    connection = BlockingConnection(ConnectionParameters(host='rabbitmq'))
    channel = connection.channel()
    channel.queue_declare(queue='dmesh-order')
    channel.basic_consume(queue='dmesh-order', on_message_callback=callback, auto_ack=True)
    print('[*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted!')
        exit(0)
