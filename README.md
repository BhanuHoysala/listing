# Car Listing REST service

### Execution Steps

- Import in your favorite IDE and bootstrap the application as any other Spring boot application

### Running Application as docker container (execute the below commands in order)

```sh
docker image build -t listing-app .
docker container run -p 8080:8080 listing-app
```

### REST API exposed

#### example consumptions

- Search over listings
```sh
GET http://localhost:8080/vehicle_listings/v1/search?make=audi
```
- To get all the listings
```sh
GET http://localhost:8080/vehicle_listings/v1/
```
- To get all the listings of a dealer
```sh
GET http://localhost:8080/vehicle_listings/v1/dealer_id
```
- To post listing(s)
```sh
POST localhost:8080/vehicle_listings/v1/dealer_id
```
- To post listing(s) by uploading csv file
```sh
POST localhost:8080/vehicle_listings/v1/upload_csv/dealer_id
```