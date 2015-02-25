package org.ccw.database.main

import org.ccw.database.SimpleDBManager

object main {

  def main(args: Array[String]) {
    println("this is the mainclass")

    /**
     *   
     // create one internal h2 database for demo 
     val cp1 = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa");

    // register the connection pool 
    SimpleDBManager.registerConnectionPool("InMemTest", cp1)
  
    SimpleDBManager.withThreadLocalConnection(sourceDBName) {
      connection => {
        // perform DB operation here with the connection
      
      }
    }
     * 
     */
  }


}