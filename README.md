## Banking Platform
Is application service built on microservice architecture. It is made up of 3 services *Customer*, *Account* and *Cards*.

It is built in a such away:-

**Customer**: When you fetch a customer using ID it will return customer, account and cards details associated with that customer. We are achieving this making API call to the other microservices.

## Cards Microservice

Base url

```http://localhost:8082/api/v1/cards```

### Get card details by ID
This returns all information about the card which includes the account number, and customer details

```
curl --location 'http://localhost:8082/api/v1/cards/202'
```
#### Response
```json
    {
  "customer": {
    "firstName": "Jane",
    "lastName": "Doe",
    "otherName": "Smith"
  },
  "accounts": [
    {
      "id": 2,
      "accountNumber": "123485674",
      "iban": "DTBKENLI52123445",
      "bicSwift": "SWIFTKEN121234",
      "accountType": null,
      "cards": [
        {
          "id": 152,
          "cardNumber": "2244-2345-078&",
          "cardType": "CREDIT",
          "cvv": "067",
          "pan": "23455",
          "aliasName": "DOE DOE",
          "expiryDate": null,
          "accountID": 2
        },
        {
          "id": 202,
          "cardNumber": "2244-2345-0780",
          "cardType": "PRE-PAID",
          "cvv": "067",
          "pan": "23455",
          "aliasName": "DOE DOE",
          "expiryDate": null,
          "accountID": 2
        },
        {
          "id": 252,
          "cardNumber": "2244-2345-0780",
          "cardType": "PRE-PAID",
          "cvv": "067",
          "pan": "23455",
          "aliasName": "DOE DOE",
          "expiryDate": null,
          "accountID": 2
        }
      ]
    }
  ]
}
```
### Cards update
**Request**
```
    PUT http://localhost:8082/api/v1/cards
Content-Type: application/json

{
  "id": 202,
  "cardNumber": "2244-2345-0780-1234",
  "cardType": "PRE-PAID",
  "cvv": "067",
  "pan": "23455",
  "aliasName": "JOHN SMITH",
  "accountID": 1
}
```

**Response**
```json
    {
  "id": 202,
  "cardNumber": "2244-2345-0780",
  "cardType": "PRE-PAID",
  "cvv": "067",
  "pan": "23455",
  "aliasName": "JOHN SMITH",
  "expiryDate": null,
  "accountID": 2
}
```