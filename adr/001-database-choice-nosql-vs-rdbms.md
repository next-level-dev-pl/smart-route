# Database choice: NoSql vs RDBMS

Date: **2025-03-01**

## Context

For our application, we need to choose between a relational database (RDBMS) and a NoSQL database. The choice will impact scalability, consistency, and data modeling.

Key considerations include:

- Data structure – Do we need strict schemas or flexible data models?
- Transactions – Is ACID compliance a priority?
- Query complexity – Do we need advanced querying, joins, and aggregations?

## Options Considered

### Option 1: Use one of RDBMSes (e.g., PostgreSQL, MySQL, MariaDB)

✅ Pros:

- Strong ACID compliance (ensuring data consistency)
- Well-suited for structured data with relationships
- Mature ecosystem with broad community support
- Advanced querying capabilities (joins, transactions, indexing)
- Some RDBMSes supports JSONs (like PostgresSQL)

❌ Cons:

- Can require complex schema migrations over time

## Option 2: Use one of NoSQL Databases (e.g., MongoDB, Cassandra, DynamoDB)

✅ Pros:

- Schema flexibility (ideal for semi-structured or unstructured data)
- Designed for high availability and horizontal scaling
- Better suited for large-scale distributed applications

❌ Cons:

- Weaker consistency guarantees (eventual consistency in some cases)
- Lack of native support for complex joins and transactions
- Potentially higher complexity in query design and data integrity management

## Option 3: Hybrid Approach (Use PostgreSQL + NoSQL for Specific Use Cases)

✅ Pros:

- RDBMS for structured, relational data
- NoSQL for flexible, high-scale workloads (e.g., caching, logs, real-time analytics)
- Balances consistency and scalability

❌ Cons:

- Increased system complexity due to multiple storage solutions
- Requires clear guidelines on when to use each database type

## Decision

We choose **Option 1 – PostgreSQL as our primary database**.

PostgreSQL offers the best balance between performance, flexibility, and reliability for our needs.

It provides:

- ACID transactions for strong consistency.
- Rich SQL features for complex queries and reporting.
- JSONB support for semi-structured data, reducing the need for NoSQL.
- Scalability options via extensions like Citus or logical replication.
- While NoSQL solutions can be valuable in certain scenarios (e.g., caching, high-throughput event storage), our current requirements favor a structured, relational approach.

## Consequences

✅ Positive:

- Ensures data integrity with ACID compliance.
- Supports both relational and semi-structured data via JSONB.
- Widely adopted with strong community and ecosystem.

❌ Negative:

- Requires schema management and migrations.