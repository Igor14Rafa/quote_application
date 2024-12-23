# Requirements
Docker, Maven, Java (>=8), The [stock-manager](https://github.com/Igor14Rafa/stock-manager) project.

# How to run

In the stock-quote-manager folder, run 

```bash
mvnw package && java -jar target/stock-quote-manager.jar
```
If you don't have the maven installed, go [here](https://maven.apache.org/install.html).
After this, you need to create the docker image

```bash
docker build -t igorrafa/stock-quote-manager .
```

You can change the image's name, but you must change it in the Dockerfile and docker-compose.yml as well.

# Usage
Build the [stock-manager](https://github.com/Igor14Rafa/stock-manager) project and run.

# Endpoints

## [POST] :8081/add - Add a new quote
Create a new quote on a a given stock. The stock must be registered in the stock-manager

### Parameters
*id (String): stock id
*Date (Date): stock date
*Price (Float): stock price

## [GET] :8081/stockquotes -List stockquotes
List all the quotes registered for each stock, in the given format:
```json
{
  "id": "stockId"
  "quotes": {
    "quoteDate": "quotePrice"
  }
}
```


## License
[MIT](https://choosealicense.com/licenses/mit/)
