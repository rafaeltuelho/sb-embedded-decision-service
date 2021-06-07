# Building the Code

```
mvn spring-boot:run
```

# API Docs (Swagger v2)

http://localhost:8091/webjars/swagger-ui/index.html?url=/v2/api-docs

# Executing the Service

Endpoint: http://localhost:8090/api/v1/decisions/post

## Using cUrl

```
curl -X POST http://localhost:8090/api/v1/decisions/post \
-H 'Content-Type: application/json' \
-d '
{
    "person": {
        "name": "Rafael",
        "age": 37
    }
}
'
```

Example output:

```
{
    "isAnAdult": "true"
}
```
