# 기능
database stats
database drop
collection names
collection rename


# 실행 
mvn spring-boot:run -Dspring.profiles.active=local
mvn spring-boot:run -Dspring.profiles.active=dev


# database의 상태 보기
http://localhost:8080/admin?databaseName=<databaseName>

# database의 전체 컬렉션명 보기
http://localhost:8080/admin/<databaseName>

# 특정 컬렉션 rename
http://localhost:8080/admin/renameCollection?from=<From databaseName>&to=<To databaseName>&collectionName=<collectionName>

# database내의 전체 컬렉션 rename
http://localhost:8080/admin/renameCollections?from=<From databaseName>&to=<To databaseName>

# 데이터베이스 drop
http://localhost:8080/admin/dropDatabase?databaseName=<databaseName>


