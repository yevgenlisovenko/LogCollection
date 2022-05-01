# Assumptions
- one log record in log file takes only one line.
- each log has following format:\
  \<timestamp>\<separator>\<level>\<separator>\<message>\
  For example:\
  ```2022-04-29 19:58:37.522|INFO|Apple80211Set:10289 Processing APPLE80211_IOC_ROAM```\
  Sample log file used for testing you can find [here](https://github.com/yevgenlisovenko/LogCollection/blob/dev/logs/sample.log).
- Log Collection Service has permissions to read files in the log directory.

# Log Collection Service
- The **[service](https://github.com/yevgenlisovenko/LogCollection/tree/dev/log-collection-service)** is written in Java using SpringBoot Framework.
- The service in current implementation returns list of logs sorted descending by timestamps. Logs are always read from the end of the file (to read the latest logs). User has ability to limit the count of returned logs, as well as filter them using different filters.
- The service has HTTP endpoint **POST /v1/get-logs** to retrieve logs.\
  Example of HTTP request:
  ```
  curl --location --request POST 'http://localhost:8081/v1/get-logs'
  --header 'Content-Type: application/json'
  --data-raw '
  {
     "filename": "sample.log",
     "limit": 7,
     "filter": {
        "type": "Contains",
        "filterString": "APPLE"
     }
  }'
  ```
- As shown in example above the JSON body of the request has 3 fields:
    - **filename** - name of the log file. Note, it doesn't include the full path. Path to the directory with log files is configurable and can be changes via service's [properties](https://github.com/yevgenlisovenko/LogCollection/blob/dev/log-collection-service/src/main/resources/application.properties).
    - **limit** - optional field. It limits the count of logs to be returned. If not provided then the service reads all logs from the file.
    - **filter** - optional field. It is used to filter the logs. The **type** of the filter can one of the following:
        - "Contains" - qualifies only logs which contain **filterString** substring
        - "StartsWith" - qualifies only logs which start with **filterString**
        - "EndsWith" - qualifies only logs which end with **filterString**
        - "Regex" - qualifies only logs using regular expression from **filterString**
        - "Level" - qualifies only logs with log level specified via **filterString**
- The service implemented in a way that allow very easily to add new filters. See [Filter Factory](https://github.com/yevgenlisovenko/LogCollection/blob/dev/log-collection-service/src/main/java/com/yevgen/logcollection/filtering/FilterFactory.java).
- The service has configurable [properties](https://github.com/yevgenlisovenko/LogCollection/blob/dev/log-collection-service/src/main/resources/application.properties) for log collection. Their default values are:
  ```
  log-collection.path = /Users/yevgen/cribl/LogCollection/logs
  log-collection.file-reader.buffer-size = 4096
  log-collection.file-reader.encoding = UTF-8
  log-collection.file-reader.column-separator = \\|
  log-collection.file-reader.date-format = yyyy-MM-dd HH:mm:ss.SSS
  ```
- Sample of HTTP response:
  ```json
  {
    "count": 3,
    "logs": [
        {
            "dateTime": "2022-04-29T20:54:26.75",
            "level": "INFO",
            "message": "Apple80211Set:10324 CFType is CFData"
        },
        {
            "dateTime": "2022-04-29T20:54:26.75",
            "level": "INFO",
            "message": "Apple80211Set:10303 Processing APPLE80211_IOC_ROAM dataRef:0x7f9f61ad8ad0"
        },
        {
            "dateTime": "2022-04-29T20:54:26.75",
            "level": "INFO",
            "message": "Apple80211Set:10289 Processing APPLE80211_IOC_ROAM"
        }
    ]
  }
  ```
- The service has exception handling which will return description of the error in HTTP response.
- The service also has health check endpoint which can be useful when the service will be a part of distributed system:\
  **GET /health**
- Most of the code in the project follows [SOLID principles](https://en.wikipedia.org/wiki/SOLID), which allows to make sure that the code is understandable, flexible and maintainable.
  
# Potential enhancements
- Logs may have different format. The service can be modified to allow specifying log format via HTTP request.
- Log files may have multiline logs. For this purpose new implementation of [ILogReader](https://github.com/yevgenlisovenko/LogCollection/blob/dev/log-collection-service/src/main/java/com/yevgen/logcollection/io/logReader/ILogReader.java) can be added to the service.

# Unit testing
- Basic scenarios are covered in [LogCollectionServiceTest.java](https://github.com/yevgenlisovenko/LogCollection/blob/dev/log-collection-service/src/test/java/com/yevgen/logcollection/service/LogCollectionServiceTest.java):
    - testLogCollectionService_FileDoesNotExists
    - testLogCollectionService_EmptyFile
    - testLogCollectionService_WithLimit
    - testLogCollectionService_NoLimit
    - testLogCollectionService_WithLimitGreaterThanLogsCount
    - testLogCollectionService_HandleInvalidLogFormat
    - testLogCollectionService_FilterContains
    - testLogCollectionService_FilterStartsWith
    - testLogCollectionService_FilterEndsWith
    - testLogCollectionService_FilterRegex
    - testLogCollectionService_FilterLevel