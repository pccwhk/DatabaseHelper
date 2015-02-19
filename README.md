# DatabaseHelper
A scala database helper for database and DAO 

It borrows the concept from Playframework's DB Api plugin.

This is a scala library, built by Gradle scala plugin. 

Sample usage will be:

```scala
  // create connection pool
  val cp1 = JdbcConnectionPool.create("jdbc:h2:mem:test1", "sa", "sa");
  
  val cp2 = JdbcConnectionPool.create("jdbc:h2:mem:test2", "sa", "sa");
  
  // register with the SimpleDBManager
  SimpleDBManager.addConnectionPool("db1", cp1)
  
  SimpleDBManager.addConnectionPool("db2", cp2)
  
  // in any location of source code
  SimpleDBManager.withThreadLocalConnection("db1"){
  
    connection =>{
    
      //do whatever you want with the connection
      
      //e.g. connection.createStatment()....
    }
    
  }
```
