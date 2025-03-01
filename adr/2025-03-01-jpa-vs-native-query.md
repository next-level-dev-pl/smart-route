# ADR 2025-03-01 – JPA vs Native Query

## Context

When working with a database in a Java application, we need to decide whether to use JPA (Java Persistence API) queries or native SQL queries for data access. Both approaches have advantages and trade-offs:

- JPA Queries provide an abstraction over SQL, making the code database-agnostic and integrating well with the object-oriented model.
- Native Queries allow writing raw SQL, providing full control over the query and optimizing for performance, but at the cost of losing database portability and ORM benefits.

The key considerations for this decision include:

- Maintainability and readability
- Complexity of queries

## Options Considered

### Option 1: Use JPA Queries

✅ Pros:

- Works well with JPA entities and relationships
- More maintainable in ORM-based applications
- Helps prevent SQL injection when used correctly

❌ Cons:

- Limited support for complex queries (e.g., window functions, CTEs)
- Performance overhead due to additional abstraction
- Harder to fine-tune query execution plans

### Option 2: Use Native SQL Queries

✅ Pros:

- Full access to database-specific features
- More control over indexing and execution plans

❌ Cons:

- Harder to maintain, especially in ORM-driven applications
- Potential risk of SQL injection if not used carefully

### Option 3: Mixed Approach (JPA by default)

✅ Pros:

- Uses JPA for maintainability and abstraction
- Leverages native SQL only where needed for performance (or readability)

❌ Cons:

- Slight increase in complexity (mixing two approaches)
- Requires clear guidelines to avoid misuse

## Decision

We choose **Option 3 – Mixed Approach**.

By default, we will use JPA queries to ensure maintainability and portability. However, in cases where JPA does not support required SQL features efficiently, we will allow native queries with clear justifications.

To enforce this approach, we will:

- Use JPA queries for standard CRUD operations and moderately complex queries.
- Use native SQL only when JPA queries lead to lack required SQL capabilities.

## Consequences

✅ Positive:

- Keeps the codebase clean and maintainable while allowing optimizations.
- Allows leveraging SQL-specific features when needed.

❌ Negative:

- Slight increase in complexity due to a mixed approach.
- Requires team discipline to avoid overusing native queries unnecessarily.