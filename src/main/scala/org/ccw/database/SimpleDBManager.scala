package org.ccw.database

import java.sql.Connection
import javax.sql.DataSource
import scala.collection.concurrent.TrieMap

object SimpleDBManager extends DB {
  
  val dataSourceMap =  new TrieMap[String, DataSource]()
  
  def registerConnectionPool(name :String, datasource :DataSource) {
     dataSourceMap.put(name, datasource)
  }
  
  override protected def getConnection(connectionName: String): Option[Connection] = {
    if (dataSourceMap.get(connectionName) != None){
      Some(dataSourceMap.get(connectionName).get.getConnection)
    }
    else None
  }
}