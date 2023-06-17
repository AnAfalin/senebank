# Senebank

Rest API, написанный на языке Java с использованием фреймворка Spring. 
Бэкенд сервис для обеспечения выполнения основных бизнес-процессов банковской компании: обслуживание клиентов и их счетов, переводы между счетами, расчет кредитов и тому подобное.

### Технологический стэк
* Java в качестве языка программирования;
* Spring Boot (контейнер сервлетов, реализация бизнес-логики);
* Java Persistence API - для реализации ORM взаимодействия с базой данных;
* Spring Security - авторизация и аутентификация пользователей, настройка безопасности для эндпоинтов;
* JWT - для использования в качестве сессии пользователя.
* PostgreSQL - для хранения данных приложения 

## Endpoints
Описание основных эндпоинтов приложения:

### Авторизация и регистрация, продление токена

**Регистрация пользователя**

При регистрации предусмотрена ошибка о недоступном email, т.к. с вводимым email уже зарегистрирован пользователь. 
Так же, вводимые данные при регистрации проходят валидацию: email должен быть в формате email, пароль должен содержать минимальное допустимое количество символов.

```url 
[HTTP POST] /auth/reg
```

Принимает JSON следующего формата:
```json
{
    "email": "example@mail.ru",
    "password": "123456"
}
```

Возвращает JSON в формате:
```json
{
    "status": "201 CREATED",
    "message": "User with login='example@mail.ru' successfully created"
}
```

**Авторизация пользователя**

```url 
[HTTP POST] /login
```

Принимает JSON следующего формата:
```json
{
    "email": "example@mail.ru",
    "password": "password"
}
```

При верных переданных данных возвращает JSON в формате:
```json
{
    "email": "me@mail.ru",
    "accessToken": {
        "token": "Barer string",
        "expirationDataTime": "date time"
    },
    "refreshToken": {
        "token": "Barer string",
        "expirationDataTime": "date time"
    }
}
```

В случае неверных переданных данных возвращает JSON в формате:
```json
{
    "timestamp": "2023-05-26 13:02:52",
    "status": "INTERNAL_SERVER_ERROR",
    "message": "Password not match"
}
```
___

### Обновление access токена

**После успешной авторизации все запросы выполняются с заголовком Authorization, в который добавляется ключ выданного токена**
По истечении действия access токена, пользователю может быть выдан новый access токен путем перехода по эндпоинту /api/refresh-token с передачей данных refresh токена.

**Получение нового access токена**

```url
[HTTP POST] /api/refresh-token
```

Принимает JSON следующего формата:
```json
{
    "token": "Barer string"
}
```

Возвращает JSON в формате:
```json
{
    "email": "me@mail.ru",
    "accessToken": {
        "token": "Barer string",
        "expirationDataTime": "date time"
    },
    "refreshToken": {
        "token": "Barer string",
        "expirationDataTime": "date time"
    }
}
```
___

### Счета

**Открытие счета**

```url
[HTTP POST] /accounts
```

Принимает JSON следующего формата:
```json
{
    "userId" : 2,
    "isOverdraft" : true
}
```

Возвращает JSON в формате:
```json
{
    "status": "201 CREATED",
    "success": true,
    "message": "Account successful created"
}
```

**Информация о счете**

```url
[HTTP GET] /accounts/{accountId}
```

Возвращает JSON в формате:
```json
{
"user": {
        "email": "example@mail.ru",
        "registrationDate": "date"
    },
    "isOverdraft": false,
    "balance": 500
}
```

**Удаление аккаунта**

```url
[HTTP DELETE] /accounts/{accountId}
```

Возвращает JSON в формате:
```json
{
    "status": "200 OK",
    "success": true,
    "message": "User with id='number' successful deleted"
}
```
___

### Транзакции

**Перевод денежных средств**

```url
[HTTP POST] /transactions
```

Принимает JSON следующего формата:
```json
{
    "userId" : 1,
    "accountFromId" : 1,
    "accountToId" : 2,
    "payload": 150
}
```

Возвращает JSON в формате:
```json
{
    "status": "200 OK",
    "success": true,
    "message": "The transfer of 150 from account with id='1' to account with id='2' has been successfully completed"
}
```

**Информация о транзакциях (для админа)**

```url
[HTTP GET] /admin/transactions
```

Возвращает JSON в формате:
```json
[
    {
        "id": 1,
        "userId": 1,
        "accountFromId": 1,
        "accountToId": 2,
        "payload": 150
    }
]
```  
___

### Пользователи

**Удаление пользователя (для админа)**

```url 
[HTTP DELETE] /admin/users/{userId}
```

Возвращает JSON в формате:
```json
{
    "status": "200 OK",
    "message": "User with id='number' successfully deleted"
}
```

