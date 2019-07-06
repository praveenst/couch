Description
===========

Document loading tool:
 * This tool inserts Json documents into couchbase using couchbase Java sdk based on an user given document size and count.
 * The tool uses any fixed schema documented in `Schema` section below.
 * It can uses couchbase batch APIs to be faster and efficient for document inserts that is 10 or more.
 * The interface may be create n number of documents with distinct keys e.g. key1, key2 …, and arbitrary values.
 * Looking at Couchbase UI (localhost:8091) on the Bucket tab you see the ops per second be non-zero.


Requirements
============

 * Download Couchbase 6.0.1 [![Download](https://www.couchbase.com/downloads)
 * Install Couchbase and configure a bucket, traditionally called “default”
 * Install Java SDK for the language and platform of your choice from [![Java2.7](https://docs.couchbase.com/java-sdk/2.7/start-using-sdk.html)

Bucket
-------

* buckets - `default` - The name used for the Couchbase bucket
* server used to test - localhost
* authentication credentials - administrator/administrator


Resources/Providers
===================

### Schema for default bucket

 *`id` - row id
 *`firstName` - first name string generated using faker.name() api
 *`lastName` - last name, string generated using faker.name() api
 *`phone` - auto generated string that has format (999)-999-9999
 *`isVegan`,  `isArmyVeteran` - couple of boolean variables
 *`age` - integer
 *`lastUpdated` - current local system time

### Attribute Parameters

* `# of documents` - The id of the Couchbase cluster, typically "default", defaults to the resource name
* `username` - The username to use to authenticate with Couchbase
* `password` - The password to use to authenticate with Couchbase

### Maven Dependencies

* Faker - to automatically create data for some of the attributes like `firstName` and `lastName`
* couchbase Java Client 2.7

        <dependency>
            <groupId>com.couchbase.client</groupId>
            <artifactId>java-client</artifactId>
            <version>2.7.7</version>
        </dependency>
        <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>0.12</version>
        </dependency>

### Example Usage:

* For smaller number of documents, say 5 - any number less than 10, the tool iteratively creates records


```
Enter # of Documents:
5
Enter couchbase username:
administrator
Enter couchbase password:
administrator
Jul 06, 2019 2:57:57 PM com.couchbase.client.core.CouchbaseCore <init>
INFO: CouchbaseEnvironment: {sslEnabled=false, sslKeystoreFile='null', sslTruststoreFile='null', sslKeystorePassword=false, sslTruststorePassword=false, sslKeystore=null, sslTruststore=null, bootstrapHttpEnabled=true, bootstrapCarrierEnabled=true, bootstrapHttpDirectPort=8091, bootstrapHttpSslPort=18091, bootstrapCarrierDirectPort=11210, bootstrapCarrierSslPort=11207, ioPoolSize=4, computationPoolSize=4, responseBufferSize=16384, requestBufferSize=16384, kvServiceEndpoints=1, viewServiceEndpoints=12, queryServiceEndpoints=12, searchServiceEndpoints=12, configPollInterval=2500, configPollFloorInterval=50, networkResolution=NetworkResolution{name='auto'}, ioPool=NioEventLoopGroup, kvIoPool=null, viewIoPool=null, searchIoPool=null, queryIoPool=null, analyticsIoPool=null, coreScheduler=CoreScheduler, memcachedHashingStrategy=DefaultMemcachedHashingStrategy, eventBus=DefaultEventBus, packageNameAndVersion=couchbase-java-client/2.7.7 (git: 2.7.7, core: 1.7.7), retryStrategy=BestEffort, maxRequestLifetime=75000, retryDelay=ExponentialDelay{growBy 1.0 MICROSECONDS, powers of 2; lower=100, upper=100000}, reconnectDelay=ExponentialDelay{growBy 1.0 MILLISECONDS, powers of 2; lower=32, upper=4096}, observeIntervalDelay=ExponentialDelay{growBy 1.0 MICROSECONDS, powers of 2; lower=10, upper=100000}, keepAliveInterval=30000, continuousKeepAliveEnabled=true, keepAliveErrorThreshold=4, keepAliveTimeout=2500, autoreleaseAfter=2000, bufferPoolingEnabled=true, tcpNodelayEnabled=true, mutationTokensEnabled=false, socketConnectTimeout=1000, callbacksOnIoPool=false, disconnectTimeout=25000, requestBufferWaitStrategy=com.couchbase.client.core.env.DefaultCoreEnvironment$4@3c19aaa5, certAuthEnabled=false, coreSendHook=null, forceSaslPlain=false, compressionMinRatio=0.83, compressionMinSize=32, compressionEnabled=true, operationTracingEnabled=true, operationTracingServerDurationEnabled=true, tracer=ThresholdLogTracer, orphanResponseReportingEnabled=true, orphanResponseReporter=DefaultOrphanResponseReporter, keyValueServiceConfig=KeyValueServiceConfig{minEndpoints=1, maxEndpoints=1, pipelined=true, idleTime=0}, queryServiceConfig=QueryServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, searchServiceConfig=SearchServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, viewServiceConfig=ViewServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, analyticsServiceConfig=AnalyticsServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, queryTimeout=75000, viewTimeout=75000, searchTimeout=75000, analyticsTimeout=75000, kvTimeout=2500, connectTimeout=5000, dnsSrvEnabled=false, propagateParentSpan=true}
Jul 06, 2019 2:58:00 PM com.couchbase.client.core.node.CouchbaseNode signalConnected
INFO: Connected to Node 127.0.0.1
Jul 06, 2019 2:58:00 PM com.couchbase.client.core.config.DefaultConfigurationProvider upsertBucketConfig
INFO: Selected network configuration: default
Jul 06, 2019 2:58:00 PM com.couchbase.client.core.config.DefaultConfigurationProvider$8 call
INFO: Opened bucket default
Creating Documents in couchbase 'Default' bucket with 5of records....
THE CONTENT IS: {"firstName":"Laurel","lastName":"Breitenberg","lastUpdated":"2019-07-06T14:58:02.082105","phone":"(510)-256-7194","isVegan":true,"isArmyVeteran":false,"age":51}
JsonDocument{id='8e105a6b-9ed1-49c7-a33b-55daf74070ef', cas=1562450282294804480, expiry=0, content={"firstName":"Laurel","lastName":"Breitenberg","lastUpdated":"2019-07-06T14:58:02.082105","phone":"(510)-256-7194","isVegan":true,"isArmyVeteran":false,"age":51}, mutationToken=null}
THE CONTENT IS: {"firstName":"Louie","lastName":"Langworth","lastUpdated":"2019-07-06T14:58:02.411107","phone":"(111)-427-2429","isVegan":false,"isArmyVeteran":true,"age":1}
JsonDocument{id='b7579625-f01f-47ff-93f0-27d321aa5f6b', cas=1562450282418798592, expiry=0, content={"firstName":"Louie","lastName":"Langworth","lastUpdated":"2019-07-06T14:58:02.411107","phone":"(111)-427-2429","isVegan":false,"isArmyVeteran":true,"age":1}, mutationToken=null}
THE CONTENT IS: {"firstName":"Britney","lastName":"Parisian","lastUpdated":"2019-07-06T14:58:02.426590","phone":"(782)-669-1086","isVegan":false,"isArmyVeteran":false,"age":63}
JsonDocument{id='398c96ed-dbaa-4659-8a97-22b3f2694db3', cas=1562450282428628992, expiry=0, content={"firstName":"Britney","lastName":"Parisian","lastUpdated":"2019-07-06T14:58:02.426590","phone":"(782)-669-1086","isVegan":false,"isArmyVeteran":false,"age":63}, mutationToken=null}
THE CONTENT IS: {"firstName":"Kasandra","lastName":"Nicolas","lastUpdated":"2019-07-06T14:58:02.436365","phone":"(168)-834-1112","isVegan":true,"isArmyVeteran":true,"age":34}
JsonDocument{id='c18f6833-2a84-44e1-a1fc-73f07b0970f2', cas=1562450282439311360, expiry=0, content={"firstName":"Kasandra","lastName":"Nicolas","lastUpdated":"2019-07-06T14:58:02.436365","phone":"(168)-834-1112","isVegan":true,"isArmyVeteran":true,"age":34}, mutationToken=null}
THE CONTENT IS: {"firstName":"Krista","lastName":"Lind","lastUpdated":"2019-07-06T14:58:02.446411","phone":"(119)-256-353","isVegan":true,"isArmyVeteran":false,"age":42}
JsonDocument{id='6e76f95e-4e3f-4b38-840a-35393c715ffa', cas=1562450282447962112, expiry=0, content={"firstName":"Krista","lastName":"Lind","lastUpdated":"2019-07-06T14:58:02.446411","phone":"(119)-256-353","isVegan":true,"isArmyVeteran":false,"age":42}, mutationToken=null}

Process finished with exit code 0

```
* For larger number of documents, say 10 - any number 10 or more, the tool iteratively creates records

```
Creating Data Writer instance...
Enter # of Documents:
10
Enter couchbase username:
administrator
Enter couchbase password:
administrator
Jul 06, 2019 3:01:41 PM com.couchbase.client.core.CouchbaseCore <init>
INFO: CouchbaseEnvironment: {sslEnabled=false, sslKeystoreFile='null', sslTruststoreFile='null', sslKeystorePassword=false, sslTruststorePassword=false, sslKeystore=null, sslTruststore=null, bootstrapHttpEnabled=true, bootstrapCarrierEnabled=true, bootstrapHttpDirectPort=8091, bootstrapHttpSslPort=18091, bootstrapCarrierDirectPort=11210, bootstrapCarrierSslPort=11207, ioPoolSize=4, computationPoolSize=4, responseBufferSize=16384, requestBufferSize=16384, kvServiceEndpoints=1, viewServiceEndpoints=12, queryServiceEndpoints=12, searchServiceEndpoints=12, configPollInterval=2500, configPollFloorInterval=50, networkResolution=NetworkResolution{name='auto'}, ioPool=NioEventLoopGroup, kvIoPool=null, viewIoPool=null, searchIoPool=null, queryIoPool=null, analyticsIoPool=null, coreScheduler=CoreScheduler, memcachedHashingStrategy=DefaultMemcachedHashingStrategy, eventBus=DefaultEventBus, packageNameAndVersion=couchbase-java-client/2.7.7 (git: 2.7.7, core: 1.7.7), retryStrategy=BestEffort, maxRequestLifetime=75000, retryDelay=ExponentialDelay{growBy 1.0 MICROSECONDS, powers of 2; lower=100, upper=100000}, reconnectDelay=ExponentialDelay{growBy 1.0 MILLISECONDS, powers of 2; lower=32, upper=4096}, observeIntervalDelay=ExponentialDelay{growBy 1.0 MICROSECONDS, powers of 2; lower=10, upper=100000}, keepAliveInterval=30000, continuousKeepAliveEnabled=true, keepAliveErrorThreshold=4, keepAliveTimeout=2500, autoreleaseAfter=2000, bufferPoolingEnabled=true, tcpNodelayEnabled=true, mutationTokensEnabled=false, socketConnectTimeout=1000, callbacksOnIoPool=false, disconnectTimeout=25000, requestBufferWaitStrategy=com.couchbase.client.core.env.DefaultCoreEnvironment$4@3c19aaa5, certAuthEnabled=false, coreSendHook=null, forceSaslPlain=false, compressionMinRatio=0.83, compressionMinSize=32, compressionEnabled=true, operationTracingEnabled=true, operationTracingServerDurationEnabled=true, tracer=ThresholdLogTracer, orphanResponseReportingEnabled=true, orphanResponseReporter=DefaultOrphanResponseReporter, keyValueServiceConfig=KeyValueServiceConfig{minEndpoints=1, maxEndpoints=1, pipelined=true, idleTime=0}, queryServiceConfig=QueryServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, searchServiceConfig=SearchServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, viewServiceConfig=ViewServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, analyticsServiceConfig=AnalyticsServiceConfig{minEndpoints=0, maxEndpoints=12, pipelined=false, idleTime=300}, queryTimeout=75000, viewTimeout=75000, searchTimeout=75000, analyticsTimeout=75000, kvTimeout=2500, connectTimeout=5000, dnsSrvEnabled=false, propagateParentSpan=true}
Jul 06, 2019 3:01:43 PM com.couchbase.client.core.node.CouchbaseNode signalConnected
INFO: Connected to Node 127.0.0.1
Jul 06, 2019 3:01:44 PM com.couchbase.client.core.config.DefaultConfigurationProvider upsertBucketConfig
INFO: Selected network configuration: default
Jul 06, 2019 3:01:44 PM com.couchbase.client.core.config.DefaultConfigurationProvider$8 call
INFO: Opened bucket default
Creating Documents in couchbase 'Default' bucket with 10of records....

```

### Testing:

* Navigate to couchbase Query Editor
* As a function of last `NUMBER_OF_RECORDS` records created, use this query to test
```
SELECT d.*
    FROM default AS d
    ORDER BY d.lastUpdated DESC
    LIMIT `NUMBER_OF_RECORDS`;
```



License and Author
==================

* Author:: Praveen Thangavelu (<praveenst@gmail.com>)
