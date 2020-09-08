# oauth-example

## cURL
```bash
curl --location --request GET 'http://localhost:8080/oauth/check_token?token=7f766c27-bb69-405f-82d7-65e1a4f16b78'

curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic ZXhhbXBsZUNsaWVudDpleGFtcGxlU2VjcmV0' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials'
```