# SpringBootTest
restful demo for springboot framework with bearer authentication and cool groovy tests


# Foreword
Hi everyone who looking for fast start with springboot,jwt authentication, rest framework, spock unitesting,it the place.
there you can get all instruments to make it work.

## First step
This step will make for you, a user and authenticate him, so response will appear with jwt token
and add a user to your curent repository

curl -X POST \
  http://localhost:8080/token/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"email":"test","password":"test"}'
  
 In response will appear authorization header with jwt token, which should be copied to other requst headers for authorization 
 "Authorization":"Bearer aabbccdd_ee"
 
## Next steps
you can also check your user with current response token
curl -X GET \
  http://localhost:8080/account/ \
  -H 'authorization: Bearer aabbccdd_ee' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 51f536ef-0b31-79a1-ff35-b9a21c645c93'
  
  response:
  {
    "id": 1,
    "email": "va",
    "password": "some pass"
}

also you can check 
restful api for some enteties what i created as example

## GET
curl -X GET \
  http://localhost:8080/entities/ \
  -H 'authorization: Bearer aabbccdd_ee' \
  -H 'cache-control: no-cache'
  
  get ist of all entities
  
  ## POST
  curl -X POST \
  http://localhost:8080/entities/ \
  -H 'authorization: authorization: Bearer aabbccdd_ee' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"title":"vadim","body":"body","price":1.04}'

  saving entity

   ## PUT
  curl -X PUT
  http://localhost:8080/entities/1 \
  -H 'authorization: authorization: Bearer aabbccdd_ee' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"title":"vadim","body":"body","price":1.04}'

updating entity
  
  ## DELETE
curl -X DELETE
  http://localhost:8080/entities/1 \
  -H 'authorization: Bearer aabbccdd_ee' \
  -H 'cache-control: no-cache' \
  deliting entity
