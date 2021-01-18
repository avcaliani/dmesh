# DMesh
By Anthony Vilarim Caliani

![#](https://img.shields.io/badge/licence-MIT-lightseagreen.svg)

`TODO` Add a description about the idea and about the project.<br>

![diagram](.docs/diagram.jpg)

## Quick Start
```bash
# Starting some services...
# - MySQL + Adminer
# - Rabbit MQ
# - DMesh API
docker-compose up -d

# When you finish
docker-compose down
```

### Mock
```bash
# Starting mock script
python3 --version # Python 3.7.9

# Get dataset from Kaggle and add "data.csv" into "./mock/api/" directory
cd mock/api && 
    python3 -m venv .venv &&
    source .venv/bin/activate &&
    pip install -r requirements.txt

# "-h" flag for further information
./api-request.py data.csv

# When you finish
deactivate
```

### Development Details
 - [API Docs](./dmesh-api/README.md)
 - [Spark Jobs](./dmesh-jobs/README.md)

## Related Links
- [Docker Hub: RabbitMQ](https://hub.docker.com/_/rabbitmq)
- [Docker Hub: MySQL](https://hub.docker.com/_/mysql)
- [Kaggle: e-Commerce Data](https://www.kaggle.com/carrie1/ecommerce-data)
- [Medium: RabbitMQ + Docker](https://medium.com/xp-inc/rabbitmq-com-docker-conhecendo-o-admin-cc81f3f6ac3b)
