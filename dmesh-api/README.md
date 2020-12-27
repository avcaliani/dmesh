https://medium.com/@jonssantana/authentication-e-authorization-usando-springboot-kotlin-382681024d08


curl -H 'Content-Type: application/json' -d '{ "username": "admin", "password": "admin" }' -X POST -v "http://localhost:8080/api/v1/auth"
curl -H 'Content-Type: application/json' -d '{ "username": "de-customer", "password": "123456" }' -X POST -v "http://localhost:8080/api/v1/auth"
curl -H 'Content-Type: application/json' -d '{ "username": "da-customer", "password": "123456" }' -X POST -v "http://localhost:8080/api/v1/auth"

TOKEN="..."
curl -H 'Content-Type: application/json' -H "Authorization: $TOKEN" -v "http://localhost:8080/api/v1/me"
