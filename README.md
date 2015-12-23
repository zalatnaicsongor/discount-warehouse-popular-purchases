# discount-warehouse-popular-purchases

* To run the tests, run 'mvn verify'
* To run the server, run 'mvn spring-boot:run'

The code is pretty self explanatory, but some notes:

## Tests:

* I only followed the TDD principles for the only class which contains actual business logic: PopularPurchasesService
* The infrastructure services are covered by integration and acceptance tests

## Implementation:

* Checking if the user exists is a synchronous HTTP call, and so is the retrieval of the users five most recent purchases.
* The retrieval of the product info + product purchases are done in an asynchronous way to speed up the processing (+ the requirement stated that the two requests have to be issued in the same time)
* Caching is done by the use of Caffeine, a Java8 caching library. There are no means to invalidate the cache entries for the time being because the remote API does not send any notice that the data has become stale. The TTL of a cache entry is 10 minutes.
* The returned products are ordered by the number of other users who bought the same product, and not by the number of unique buyers.

## Todo:

* Decouple the underlying cache instantiation from the caching decorators, and make the caches configurable (TTL, max size, etc.).
* Provide means (for example an authenticated endpoint) to invalidate a cache entry.

### If you have any questions, feel free to contact me.