# Description

A simple REST API project to manage stock quotes, as a technical challenge. The idea was to refactor [stock-manager](https://github.com/Igor14Rafa/stock-manager) and [stock-quote-manager](https://github.com/Igor14Rafa/stock-quote-manager).

# StockQuote API

## Overview
The `Stock-Quote-Manager-API`provides REST API endpoints to manage stock quotes. It includes operations for adding a new stock quote, retrieving all stock quotes and fetching a stock quote by its ID. It runs on `/8081` port

## Endpoints

### 1. Add a New Stock Quote
- **URL**: `/stockquote`
- **Method**: `POST`
- **Description**: Adds a new stock quote to the repository.
- **Request Body**:
  ```json
  {
    "id": "string",
    "quote": "string"
  }
- **Responses**:
  * `201 Created`: Successfully added stock quote.
  * `500 Internal Server Error`: An error occurred while adding the stock quote.

### 2. Get All Stock Quotes
- **URL**: `/stockquote`
- **Method**: `GET`
- **Description**: Retrieves a list of all stock quotes.
- **Response Body**:
  ```json
  [
    {
    "id": "AAPL",
    "quote": {
      "2024-12-22": 10
      }
    },
    {
    "id": "GOOGL",
    "quote": {
      "2024-12-22": 10
      }
    }
  ]

- **Responses**:
  * `200 Ok`: Successfully retrieved list of stock quotes.
  * `500 Internal Server Error`: An error occurred while retrieving the list of stock quotes.

### 3. Get Stock Quote by ID
- **URL**: `/stockquote/quote_by_id`
- **Method**: `GET`
- **Description**: Retrieves a stock quote by its ID.
- **Query Parameters**: 
  * `id` (required): The ID of the stock quote.
- **Response Body**:
  ```json
  {
    "id": "string",
    "quote": "string"
  }
- **Responses**:
  * `200 Created`: Successfully retrieved stock quote.
  * `404 Not Found`: Stock quote not found.
  * `500 Internal Server Error`: An error occurred while retrieving the stock quote.

### 4. Clear Stock Cache
- **URL**: `/stockquote/clear_cache`
- **Method**: `DELETE`
- **Description**: Sends a notification to all subscribed API hosts to clear the current stocks in the cache.

- **Responses**:
  * `200 Ok`: Notification sended successfully.
  * `500 Internal Server Error`: An error occurred while sending the notification.

## Examples

### Add a new stock quote
```sh
curl -X POST "http://localhost:8080/stockquote" -H "Content-Type: application/json" -d '{
  "id": "AAPL",
  "quote": {\"2024-12-22\":\"10\"}
}'
```

### Get All Stock Quotes
```sh
curl "http://localhost:8080/stockquote"
```

### Get Stock Quote by ID
```sh
curl "http://localhost:8080/stockquote/quote_by_id?id=AAPL"
```

# Stock API

## Overview
The `Stock-Manager-API`provides REST API endpoints for managing stock quotes. It includes operations for adding a new stock quote, retrieving all stock quotes, fetching a stock quote by its ID, and clearing the stock items cache. It runs on `/8080` port

## Endpoints

### 1. Add a New Stock
- **URL**: `/stocks`
- **Method**: `POST`
- **Description**: Adds a new stock and sends a notification to all the subscripted API services to clear the stock cache.
- **Request Body**:
  ```json
  {
    "id": "string",
    "description": "string"
  }
- **Responses**:
  * `201 Created`: Successfully added stock.
  * `500 Internal Server Error`: An error occurred while adding the stock.

### 2. Get All Stock
- **URL**: `/stocks`
- **Method**: `GET`
- **Description**: Retrieves a list of all stocks.
- **Response Body**:
  ```json
  [
    {
    "id": "AAPL",
    "description": "AAPL stock"
    },
    {
    "id": "GOOGL",
    "description": "GOOGL stock"
    }
  ]

- **Responses**:
  * `200 Ok`: Successfully retrieved list of stocks.
  * `500 Internal Server Error`: An error occurred while retrieving the stocks.

## Examples

### Add a new stock
```sh
curl -X POST "http://localhost:8080/stocks" -H "Content-Type: application/json" -d '{
  "id": "AAPL",
  "description": "AAPL stock"
}'
```

### Get All Stocks
```sh
curl "http://localhost:8080/stocks"
```

# Dependencies
* Java 23
* MySQL 8 (or your preferred database)
* Spring Boot
* Swagger OpenAPI 3

# License
[MIT](https://choosealicense.com/licenses/mit/)