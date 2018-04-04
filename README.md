This is a small prototype, where we have hardcoded a user with the credentials: admin and passowrd.

For authentication:

``curl -i -H "Content-Type: application/json" -X POST -d '{
      "username": "admin",
      "password": "password"
  }' http://localhost:8080/login``

This will return an Authorization header that should be sent in the next request. For instance:

``curl -i -H "Content-Type: application/json" 
-H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyMzE5NTk5OH0.J1-u4mAU8VxrkJilJ0wLavxjtwYAZgUH9laWhb3-h-D-8NPL9hOiaJhBqoUJPnUSXnOEbKlhv3L6GlnGFUwaBg" -X GET http://localhost:8080/sources``