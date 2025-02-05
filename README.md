# Coding Exercise - Live Odds Services 

## Assumption 1: 
We will use Kafka because its useful for real-time event streaming, ensuring scalability, fault tolerance, message ordering all that we need for this exercise.

Kafka is designed to handle both **thread safety** and **fault tolerance**.

### **1. Thread Safety in Apache Kafka**

-   **Producer:** Kafka producers are **thread-safe**, meaning a single `KafkaProducer` instance can be safely used by multiple threads. It's recommended to reuse a single producer instance rather than creating a new one for each message.
-   **Consumer:** Kafka consumers are **not completely thread-safe**. Each thread should have its own `KafkaConsumer` instance. If multiple threads need to consume from the same topic, a **Consumer Group** should be used to distribute the processing across multiple threads or processes.
-   **AdminClient:** Like the producer, `AdminClient` is **thread-safe** and can be used concurrently by multiple threads.

### **2. Fault Tolerance in Apache Kafka**

Kafka has built-in fault tolerance mechanisms, including:

-   **Data replication:** Each **partition** in a Kafka topic can be **replicated** across multiple broker nodes. If the leader partition fails, one of the replicas takes over as the new leader.
-   **Acknowledgment policies for producers:** Producers can specify how many acknowledgments (`acks`) they require before considering a message successfully written (`acks=all` ensures maximum durability).
-   **Consumer group failover:** If a consumer in a **consumer group** fails, partition assignments are automatically rebalanced among the remaining consumers.
-   **Log segments and recovery:** Kafka stores data in log segments, allowing brokers to recover state from disk after a restart.
-   **Zookeeper coordination** (in older versions): Zookeeper manages **leader election** for brokers and partitions, ensuring automatic recovery from failures
