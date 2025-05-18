## Banking Platform
Is application service built on microservice architecture. It is made up of 3 services *Customer*, *Account* and *Cards*.

It is built in a such away:-

**Customer**: When you fetch a customer using ID it will return customer, account and cards details associated with that customer. We are achieving this making API call to the other microservices.
## Customer Microservice

### POST: Create Customer
The request persists the customer details to the database table

**Request**
```
    POST http://localhost:8080/api/v1/customer HTTP/1.1
    Content-Type: application/json
    
    {
    "firstName": "Michael",
    "lastName": "Thomas",
    "otherName": ""
    }
```
**Response**
```json
    {
      "id": 102,
      "firstName": "Michael",
      "lastName": "Thomas",
      "otherName": ""
    }
```
### GET: Paginated list of Customer Details

**Request**
```
    curl --location 'http://localhost:8080/api/v1/customer?page=1&size=3'
```

**Response**
```json
    [
  {
    "id": 53,
    "firstName": "Ronald",
    "lastName": "Daniel",
    "otherName": "Drake"
  },
  {
    "id": 54,
    "firstName": "Jane",
    "lastName": "Daniel",
    "otherName": "Drake"
  },
  {
    "id": 55,
    "firstName": "Jane",
    "lastName": "Thomas",
    "otherName": "Drake"
  }
]
```
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