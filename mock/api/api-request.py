#!/usr/bin/env python3
# @script   api-request.py
# @author   Anthony Vilarim Caliani
# @contact  github.com/avcaliani
# @python   3.7.9
#
# @description
# Mock script to send HTTP Requests to the DMesh API.
# You can find the dataset at https://www.kaggle.com/carrie1/ecommerce-data.
# For more information related to the script check the project 'README.md' or try './api-request.py -h'
#
# @usage
# ./api-request.py 'csv_file' [--api-sleep n] [--debug]
import json
from argparse import ArgumentParser
from time import sleep

import pandas as pd
import requests

API_URL = 'http://localhost:8081'


def green(value):
    """Return text in green color.
  
    Args:
        value: Any string value.

    Returns:
        Green text.
    """
    return f'\033[1;32m{value}\033[00m'


def get_args():
    """Parse script arguments.

    Returns:
        Parsed arguments.
    """
    parser = ArgumentParser(description='DMesh API Mock')
    parser.add_argument('mock_file', help='CSV Mock File')
    parser.add_argument('--api-sleep', type=int, default=1, help='API Request Interval (Seconds)')
    parser.add_argument('--debug', nargs='?', type=bool, const=True, default=False, help='Debug Mode?')
    return parser.parse_args()


def get_data(file_name):
    """Parse CSV data and return it as a list of dictionatries.

    Args:
        file_name: CSV file name.

    Returns:
        List of dictionaries.
    """
    df = pd.read_csv(file_name, engine='python', dtype=str)
    return df \
        .drop(columns=['InvoiceDate']) \
        .groupby(by=['InvoiceNo', 'Country', 'CustomerID']) \
        .agg(lambda x: list(x)) \
        .reset_index() \
        .to_dict('records')


def to_order(record):
    """Parse dataset record into a valid 'Order' object.

    Args:
        record: Record from pandas processing.

    Returns:
        Order (dictionary).
    """
    products = list(zip(record['StockCode'], record['Description'], record['Quantity'], record['UnitPrice']))
    return {
        'customerId': str(record['CustomerID']),
        'country': str(record['Country']),
        'products': list(map(to_product, products))
    }


def to_product(product_tuple):
    """Parse a tuple into valid 'Product' object.

    Args:
        product_tuple: Tuple containing StockCode, Description, Quantity and UnitPrice.

    Returns:
        Product (dictionary).
    """
    return {
        'id': str(product_tuple[0]),
        'description': str(product_tuple[1]),
        'quantity': int(product_tuple[2]),
        'unitPrice': float(product_tuple[3])
    }


def retrieve_token():
    """Retrieve DMesh API token.

    Returns:
        Auth Token.
    """
    response = requests.post(
        f'{API_URL}/api/v1/auth',
        headers={'Content-Type': 'application/json'},
        json={'username': 'admin', 'password': 'admin'}
    )
    print(f'Auth Status: {green(response.status_code)}')
    return response.headers.get('Authorization', None)


def send(order, token, index, debug):
    """Post an Order to the API.

    Args:
        order: Order object to be posted.
        token: API Auth Token.
        index: Request index, just for logging purpose.
        debug: Show debug code?
    """
    url = f'{API_URL}/api/v1/order'
    response = requests.post(
        url,
        headers={'Content-Type': 'application/json', 'Authorization': token},
        json=order
    )
    print(f'({index}) POST {url} ...', end=' ')
    print(f'Status: {green(response.status_code)}')
    if debug:
        print(f'--- Request Body  ---\n{json.dumps(order)}')
        print(f'--- Response Body ---\n{response.json()}')
        print(f'-------- END --------\n\n')


if __name__ == '__main__':
    args = get_args()
    print(f'Args: {args}')

    token = retrieve_token()
    if args.debug:
        print(f'Token: {token}')

    if token is None:
        print(f'The API token couldn\'t be retrieved!')
        exit(1)

    orders = get_data(args.mock_file)
    print(f'Orders: {len(orders)}')

    orders = list(map(to_order, orders))
    for i in range(len(orders)):
        send(orders[i], token, f'{format(i, "05d")}/{len(orders)}', args.debug)
        sleep(args.api_sleep)
    exit(0)
