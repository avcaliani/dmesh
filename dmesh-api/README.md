# 🎲 DMesh API
By Anthony Vilarim Caliani

![#](https://img.shields.io/badge/licence-MIT-lightseagreen.svg) ![#](https://img.shields.io/badge/spring--boot-2.4.x-darkseagreen.svg)

## API Usage
Execute the following commands on your terminal.
```bash
API_URL="http://localhost:8080"
```

### Authentication
```bash
# api_auth USERNAME PASSWORD
api_auth() {
  HEADER_AUTH=$(
    curl -s -D - -o /dev/null \
      -H "Content-Type: application/json" \
      -d "{ \"username\": \"$1\", \"password\": \"$2\" }" \
      -X POST "$API_URL/api/v1/auth" | grep "Bearer"
  ) 
  echo "Token -> $HEADER_AUTH"
}

# Valid Users
api_auth "admin"       "admin"
api_auth "de-customer" "123456"
api_auth "da-customer" "123456"
```

### Who am I?
```bash
curl -H $HEADER_AUTH "$API_URL/api/v1/me" | json_pp
```

### Order API
```bash
curl \
  -H $HEADER_AUTH \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "17850",
    "country": "United Kingdom",
    "products": [
      {
        "id": "85123A",
        "description": "WHITE HANGING HEART T-LIGHT HOLDER",
        "quantity": 6,
        "unitPrice": 2.55
      },
      {
        "id": "71053",
        "description": "WHITE METAL LANTERN",
        "quantity": 6,
        "unitPrice": 3.39
      }
    ]
  }' \
  -X POST "$API_URL/api/v1/order" | json_pp
```

### Data APIs
```bash
# api_download FILE_TYPE DATE (yyyyMMdd)
api_download() {
  mkdir .temp
  curl -H $HEADER_AUTH "$API_URL/api/v1/data/files/$1/$2" --output ".temp/order-$1-$2.csv"
  ls -lh .temp
}

# Downloading Files
api_download "consolidated" "20201230"
api_download "records"      "20201230"
```

## Links
 - [Medium: Auth + Spring Boot](https://medium.com/@jonssantana/authentication-e-authorization-usando-springboot-kotlin-382681024d08)
