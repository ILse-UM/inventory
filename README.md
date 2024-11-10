
# Inventory Management REST API Documentation

## Endpoints

### Category API

#### Get All Categories
- **URL**: `/api/category`
- **Method**: `GET`
- **Response**: 
  - `200 OK` - List of all categories
- **Response Body**:
  ```json
  [
    {
        "id": 1,
        "name": "Category Name 1"
    },
    {
        "id": 2,
        "name": "Category Name 2"
    }
  ]
  ```

#### Get Category by ID
- **URL**: `/api/category/{id}`
- **Method**: `GET`
- **Response**:
  - `200 OK` - Category found
  - `404 Not Found` - Category not found
- **Response Body**:
  ```json
  {
    "id": 1,
    "name": "Category Name",
    "description": "Category Description"
  }
  ```

#### Create Category
- **URL**: `/api/category/create`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "name": "Category Name",
  }
  ```
- **Response**:
  - `201 Created` - Category created successfully
- **Response Body**:
  ```json
  {
    "id": 1,
    "name": "Category Name",
  }
  ```

#### Update Category
- **URL**: `/api/category/{id}/update`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "name": "Updated Category Name",
  }
  ```
- **Response**:
  - `200 OK` - Category updated successfully
  - `404 Not Found` - Category not found

#### Delete Category
- **URL**: `/api/category/{id}/delete`
- **Method**: `DELETE`
- **Response**:
  - `204 No Content` - Category deleted successfully

---

### Item API

#### Get All Items (with Pagination)
- **URL**: `/api/item`
- **Method**: `GET`
- **Query Parameters**:
  - `page` (optional): Default is `0`
  - `size` (optional): Default is `10`
- **Response**:
  - `200 OK` - List of items
- **Response Body**:
  ```json
  [
    {
      "id": 1,
      "barcode": 1123415,
      "name": "Item Name",
      "category": "Category Name",
      "description": "Item Description",
      "amount": 100,
      "purchasePrice": 500,
      "sellPrice": 700,
      "image": "image_url"
    }
  ]
  ```

#### Get Item by ID
- **URL**: `/api/item/{id}`
- **Method**: `GET`
- **Response**:
  - `200 OK` - Item found
  - `404 Not Found` - Item not found
- **Response Body**:
  ```json
  {
    "id": 1,
    "barcode": 6381723,
    "name": "Item Name",
    "category": "Category Name",
    "description": "Item Description",
    "amount": 100,
    "purchasePrice": 500,
    "sellPrice": 700,
    "image": "image_url"
  }
  ```

#### Create Item
- **URL**: `/api/item/create`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "barcode" : 641620,
    "name": "Item Name",
    "category": "Category Name",
    "description": "Item Description",
    "amount": 100,
    "purchasePrice": 500,
    "sellPrice": 700,
    "image": "image_url"
  }
  ```
- **Response**:
  - `201 Created` - Item created successfully
- **Response Body**:
  ```json
  {
    "id": 1,
    "barcode" : 641620,
    "name": "Item Name",
    "category": "Category Name",
    "description": "Item Description",
    "amount": 100,
    "purchasePrice": 500,
    "sellPrice": 700,
    "image": "image_url"
  }
  ```

#### Update Item
- **URL**: `/api/item/{id}/update`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "barcode": 345678,
    "name": "Updated Item Name",
    "category": "Updated Category Name",
    "description": "Updated Description",
    "amount": 120,
    "purchasePrice": 550,
    "sellPrice": 750,
    "image": "new_image_url"
  }
  ```
- **Response**:
  - `200 OK` - Item updated successfully
  - `404 Not Found` - Item not found

#### Delete Item
- **URL**: `/api/item/{id}/delete`
- **Method**: `DELETE`
- **Response**:
  - `200 OK` - Item deleted successfully
  - **Response Body**:
    ```json
    {
      "message": "Item deleted"
    }
    ```

---

### Transaction API

#### Get All Transactions (with Pagination)
- **URL**: `/api/transaction`
- **Method**: `GET`
- **Query Parameters**:
  - `page` (optional): Default is `0`
  - `size` (optional): Default is `10`
- **Response**:
  - `200 OK` - List of transactions
- **Response Body**:
  ```json
  [
    {
      "id": 123,
      "transactionDate": "2024-10-27T12:34:56",
      "transactionType": "PURCHASE",
      "items": [
        {
          "item": {
            "id": 1,
            "name": "Item Name",
            "category": "Category Name",
            "description": "Item description",
            "amount": 100,
            "purchasePrice": 50,
            "sellPrice": 75,
            "image": "image_url"
          },
          "amount": 5
        }
      ],
      "totalPrice": 500,
      "description": "New stock purchase"
    }
  ]
  ```

#### Get Transaction by ID
- **URL**: `/api/transaction/{id}`
- **Method**: `GET`
- **Response**:
  - `200 OK` - Transaction found
  - `404 Not Found` - Transaction not found
 
- **Response Body**:
  ```json
  {
    "id": 123,
    "transactionDate": "2024-10-27T12:34:56",
    "transactionType": "PURCHASE",
    "items": [
      {
        "item": {
          "id": 1,
          "name": "Item Name",
          "category": "Category Name",
          "description": "Item description",
          "amount": 100,
          "purchasePrice": 50,
          "sellPrice": 75,
          "image": "image_url"
        },
        "amount": 5
      }
    ],
    "totalPrice": 500,
    "description": "New stock purchase"
  }
  ```

### Create Transaction

- **URL:** `/api/transaction/create`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "transactionType": "PURCHASE",
    "items": [
      {
        "id": 1,
        "amount": 5
      },
      {
        "id": 2,
        "amount": 3
      }
    ],
    "description": "New stock purchase"
  }
  ```
- **Response**:
  - `201 Created` - Transaction created successfully
- **Response Body**:
  ```json
  {
    "id": 123,
    "transactionDate": "2024-10-27T12:34:56",
    "transactionType": "PURCHASE",
    "items": [
      {
        "item": {
          "id": 1,
          "name": "Item Name",
          "category": "Category Name",
          "description": "Item description",
          "amount": 100,
          "purchasePrice": 50,
          "sellPrice": 75,
          "image": "image_url"
        },
        "amount": 5
      },
      {
        "item": {
          "id": 2,
          "name": "Another Item",
          "category": "Category Name",
          "description": "Description",
          "amount": 50,
          "purchasePrice": 30,
          "sellPrice": 60,
          "image": "image_url"
        },
        "amount": 3
      }
    ],
    "totalPrice": 500,
    "description": "New stock purchase"
  }
  ```

#### Update Transaction
- **URL**: `/api/transaction/{id}/update`
- **Method**: `PUT`
- **Request Body**:
  ```json
    {
      "transactionType": "SALE",
      "items": [
        {
          "id": 1,
          "amount": 2
        }
      ],
      "description": "Sale transaction"
    }
  ```
- **Response**:
  - `200 OK` - Transaction updated successfully
  - `404 Not Found` - Transaction not found
 
- **Response Body**:
  ```json
  {
    "id": 123,
    "transactionDate": "2024-10-27T12:34:56",
    "transactionType": "SALE",
    "items": [
      {
        "item": {
          "id": 1,
          "name": "Item Name",
          "category": "Category Name",
          "description": "Item description",
          "amount": 100,
          "purchasePrice": 50,
          "sellPrice": 75,
          "image": "image_url"
        },
        "amount": 2
      }
    ],
    "totalPrice": 150,
    "description": "Sale transaction"
  }
  ```

#### Delete Transaction
- **URL**: `/api/transaction/{id}/delete`
- **Method**: `DELETE`
- **Response**:
  - `200 OK` - Transaction deleted successfully
  - **Response Body**:
    ```json
    {
      "message": "Transaction deleted"
    }
    ```

