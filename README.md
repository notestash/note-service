# notes-core  
  
This is a microservice dedicated to saving notes with attachments.

Attachments contain ids of files saved to the file storage microservice in advance.

Requires an OAuth2 provider.

---
Example request to Core Service with HTTPie for searching and pagination (all params are optional):  
```  
http get "localhost:8080/notes?page=0&size=2&sort=message,asc&sort=id,desc"  
```