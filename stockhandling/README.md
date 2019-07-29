SIMPLE STOCK MANAGEMENT REST API
This is a simple stock management rest api project. This is a spring boot application which includes some endpoints for some operations of stock management. These operations are: update stock, get stock of a product, get statistics of given range. Backend code is developed in order to provide always valid state of the stocks. Providing consistency of a system can be a problem when there are different data structures(databases). Different data structures should be updated at the same time in order to prevent inconsistency. This can be provided by using synchronizing or locking mechanisms but it can slow down the system. In this stock management system, it is used only one data structure (Store model) and it keeps all information about stocks and sales records. And this store model is kept in a ConcurrentHashMap in order to reach related store faster and concurrently. Complexity of getting a stock from store is constant. complexity of updating a stock is constant, but updating sales records can be logn, since sales records are kept in a concurrent implementation of treemap. This can be unnecessary, because sales records requests are already sorted. It can be discussed and changed according to requests distribution.    
Code is developed by considering layered architecture. The layers are Controller, Service and Repository.
All these layers have test methods and test coverage of application is 96%.

BUILD AND RUN

This is maven application which uses spring boot. You can build and run application by using maven.
You can build the application by using 'mvn package' command.
After build you can run the jar file using 'java -jar stock-0.0.1-SNAPSHOT.jar' 

FURTHER IMPROVEMENTS

1- ModelMapper can be used for DTO conversion instead of doing manually.
2- Database can be used instead of in memory data structures.
3- InitBinder can be used for conversion of enum values.
4- Controller and Repository test methods depend on offsetTime, tests can give different results with respect to different time. They can be changed and mocked. Time dependency can be solved.
5- Concurrency test method depend on the random stock id, therefore sometimes random ids can be same. In that case assertion fails. It can be changed later for a better consistency

FEEDBACKS

I think the task is well defined and reasonable to complete in 8 hours. It is good to be free to use any tool or framework to complete the task. I can say that I enjoyed while doing it and I learned new things also, since this task was an opportunity for me to try spring boot framework. Maybe I can just mention two little things.

1- In the assignment document, stock and product are mixed. What is stock and what is product I was confused and I tried to understand for a while.
2- Error handling situations can be explained better.

