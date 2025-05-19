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
      "responseHeader": {
        "statusCode": "0",
        "statusMessage": "Success",
        "messageCode": "200",
        "messageCodeDescription": "Request Successful"
      },
      "primaryData": {
        "customer": {
          "id": 102,
          "firstName": "Michael",
          "lastName": "Thomas",
          "otherName": ""
        }
      }
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

### GET: Customer Details By ID
**Request**
```
GET http://localhost:8080/api/v1/customer/55
Accept: application/json
```

**Response**
```json
    {
      "responseHeader": {
        "statusCode": "0",
        "statusMessage": "Success",
        "messageCode": "200",
        "messageCodeDescription": "Request Successful"
      },
      "primaryData": {
        "customer": {
          "id": 55,
          "firstName": "Jane",
          "lastName": "Thomas",
          "otherName": "Drake"
        }
      }
    }
```

## Accounts Microservice
### CREATE Account
**Request**
```
    POST http://localhost:8081/api/v1/account
    Content-Type: application/json
    
    {
      "accountNumber": "6004485679",
      "iban": "DTBKENLI784485674",
      "bicSwift": "SWIFTKEN121234",
      "customerId": 56
    }
```
**Response**
```json
    {
      "customer": {
        "id": 56,
        "firstName": "Richard",
        "lastName": "Thomas",
        "otherName": "Drake"
      },
      "accounts": [
        {
          "id": 102,
          "accountNumber": "6004485679",
          "iban": "DTBKENLI784485674",
          "bicSwift": "SWIFTKEN121234",
          "customerId": 56,
          "accountType": null
        }
      ]
    }
```
## GET Account by AccountNumber
**Request**
```
    GET http://localhost:8081/api/v1/account/6004485679
    Accept: application/json
```

**Response**
```json
    {
        "customer": {
        "id": 56,
        "firstName": "Richard",
        "lastName": "Thomas",
        "otherName": "Drake"
        },
        "accounts": [
            {
              "id": 102,
              "accountNumber": "6004485679",
              "iban": "DTBKENLI784485674",
              "bicSwift": "SWIFTKEN121234",
              "customerId": 56,
              "accountType": null
            }
        ]
  }
```
## Cards Microservice

Base url

```http://localhost:8082/api/v1/cards```

### Create Card details for Account
**Request**
```
    POST http://localhost:8082/api/v1/cards
    Content-Type: application/json
    
    {
      "cardNumber": "8978-2345-0780-1234",
      "cardType": "DEBIT",
      "cvv": "067",
      "pan": "23455",
      "aliasName": "JOHN SMITH",
      "accountID": 54
    }
```
**Successful Response**
```json
    {
      "responseHeader": {
        "statusCode": "0",
        "statusMessage": "Success",
        "messageCode": "201",
        "messageCodeDescription": "Card created successfully"
      },
      "primaryData": {
        "customer": {
          "firstName": "Michael",
          "lastName": "Thomas",
          "otherName": ""
        },
        "accounts": [
          {
            "id": 57,
            "accountNumber": "684485679",
            "iban": "DTBKENLI784485674",
            "bicSwift": "SWIFTKEN121234",
            "accountType": null,
            "cards": [
              {
                "id": 652,
                "cardNumber": "8800-2345-0780-1234",
                "cardType": "CREDIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JOHN SMITH",
                "expiryDate": null,
                "accountID": 57
              },
              {
                "id": 702,
                "cardNumber": "8800-2345-0780-1234",
                "cardType": "DEBIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JOHN SMITH",
                "expiryDate": null,
                "accountID": 57
              }
            ]
          }
        ]
      }
    }
```
**Unsuccessful Response with the same card type**
```json
    {
      "responseHeader": {
        "statusCode": "1",
        "statusMessage": "Failed",
        "messageCode": "400",
        "messageCodeDescription": "Card type exists for the account"
      },
      "primaryData": null
    }
```
**Unsuccessful Response with the more than 2 card types**
```json
    {
      "responseHeader": {
        "statusCode": "1",
        "statusMessage": "Failed",
        "messageCode": "400",
        "messageCodeDescription": "Account has 2 already created cards"
      },
      "primaryData": null
    }
```


### Get card details by ID
This returns all information about the card which includes the account number, and customer details

```
curl --location 'http://localhost:8082/api/v1/cards/202?unmask=true'
```
#### Response
```json
    {
      "responseHeader": null,
      "primaryData": {
        "customer": {
          "firstName": "John",
          "lastName": "Smith",
          "otherName": null
        },
        "accounts": [
          {
            "id": 1,
            "accountNumber": "12345674",
            "iban": "DTBKENLI52123445",
            "bicSwift": "SWIFTKEN121234",
            "accountType": null,
            "cards": [
              {
                "id": 1,
                "cardNumber": "1234-2345-6789",
                "cardType": "DEBIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JANE DOE DOE",
                "expiryDate": null,
                "accountID": 1
              },
              {
                "id": 2,
                "cardNumber": "2234-2345-6789",
                "cardType": "DEBIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JANE DOE DOE",
                "expiryDate": null,
                "accountID": 1
              },
              {
                "id": 52,
                "cardNumber": "2234-2345-6789",
                "cardType": "DEBIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JANE DOE DOE",
                "expiryDate": null,
                "accountID": 1
              },
              {
                "id": 102,
                "cardNumber": "2234-2345-0789",
                "cardType": "CREDIT",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JANE DOE DOE",
                "expiryDate": null,
                "accountID": 1
              },
              {
                "id": 302,
                "cardNumber": "2244-2345-0780-1234",
                "cardType": "PRE-PAID",
                "cvv": "XXX",
                "pan": "DTBXXX",
                "aliasName": "JOHN DOE",
                "expiryDate": null,
                "accountID": 1
              }
            ]
          }
        ]
      }
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

### NOTE
### DATABASE
Create database in POSTGRES 

```create database dtb```

Then update the ```application.yml``` of the 3 services with correct **_password_** and **_username_**

#### APPLICATION PORTS
* 8080 - Customer Application
* 8081 - Account Application
* 8082 - Card Application