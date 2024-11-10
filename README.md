
# Inventory Management REST API Documentation

## Installation
1. download this repo
2. load maven dependency
3. run the project
   
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

---

## Postman Image
![image](https://github.com/user-attachments/assets/e936870e-1f04-4975-9903-8898cb34b7dd)
![image](https://github.com/user-attachments/assets/3a751a6c-78b8-4fa8-9270-0cb8d38acdb5)
![image](https://github.com/user-attachments/assets/4d99208b-436f-486e-8cc7-c27e5c85a0e5)
![image](https://github.com/user-attachments/assets/98f9fd2d-ba6d-4c99-9fe7-9f86f1364b3f)
![image](https://github.com/user-attachments/assets/ad6f02d3-f0ce-4532-bcf5-a6f6c45d7e95)
![image](https://github.com/user-attachments/assets/465bf63b-5d80-464d-96be-97114325fc84)
![image](https://github.com/user-attachments/assets/482804ed-1aaa-4ce3-8ae0-d294b94b10e6)
