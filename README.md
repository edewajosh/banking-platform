## Customer Microservice
**Customer fields**
1. id - customer id **mandatory** and **Unique**
2. firstName - First Name, **Mandatory**
3. lastName - Last Name, **Mandatory**
4. otherName - Other Name, **Optional**

## Cards
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
