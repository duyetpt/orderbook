# Order book system design
https://drive.google.com/file/d/14f0XkInpeRm5I47uwYkO3w6toSlfpwDy/view?usp=sharing

## Components
- Matching service
- Matching Engine(ME)
- Presentation service
- Position service
- MEWorker
- Kafka
- Redis
- Timeseries DB (mongo, postgres, cassandra, bigtable,...)

## Matching Service
- Receive order from client
- Persist order to DB
- Push order to Matching Engine
- Push order to MEWorker (for ME resconstruct after restart)

## Matching Engine
- Receive order from Matching Engine
- Matching bid and ask
- Push order operation to MEWorker
  - add order
  - update order(quantity, remove)

## MEWorker
Consolidate order data for ME recover after crash or restart 
- receive new Order
- receive operation order from ME
- Providing api to ME get data and recover

## Position service
- Receive matching from ME and process
- Providing api about Position for another service

## Presentation service
- Providing apis for client about order book, order, position,...