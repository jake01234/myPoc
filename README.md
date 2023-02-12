# myPoc
1.go to properties change the following db info as yours.
spring.datasource.username = root
spring.datasource.password = password

2.run this program

3.for your first user, you need to use POSTMAN

POST
http://localhost:9191/api/auth/signup

{
"username": "jake01",
"name": "JAKE",
"ssn": 123456,
"age": 20,
"email": "jake01@gmail.com",
"password": "jake01111",
"city": "mycity",
"address": "myaddy",
"phone": "12345678"
}

4.go to http://localhost:9191/
login with the credential you just created
