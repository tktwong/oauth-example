# oauth-example

## cURL
```bash
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic YXBwMUNsaWVudDphcHAxU2VjcmV0' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JWT-TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwiaWF0IjoxNTk3MDM0MzIzfQ.bBFDotSligK-o8tRQ76tGbpXQyP03Ag8aYjXDBFfOVI' \
--data-urlencode 'grant_type=client_credentials'

curl --location --request GET 'http://localhost:8080/oauth/check_token?token=28f4a7da-b3ad-486b-b05b-b5fe8bcd6d4e' \
--header 'Cookie: JWT-TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwiaWF0IjoxNTk3MDM0MzIzfQ.bBFDotSligK-o8tRQ76tGbpXQyP03Ag8aYjXDBFfOVI'

curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic YXBwMUNsaWVudDphcHAxU2VjcmV0' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JWT-TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwiaWF0IjoxNTk3MDM0MzIzfQ.bBFDotSligK-o8tRQ76tGbpXQyP03Ag8aYjXDBFfOVI' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=terry' \
--data-urlencode 'password=password'

curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic YXBwMUNsaWVudDphcHAxU2VjcmV0' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JWT-TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwiaWF0IjoxNTk3MDM0MzIzfQ.bBFDotSligK-o8tRQ76tGbpXQyP03Ag8aYjXDBFfOVI' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'refresh_token=f5171057-b9b5-4cf4-96e3-3af0c74434af'
```