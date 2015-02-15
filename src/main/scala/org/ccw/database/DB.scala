package org.ccw.database

import java.sql.Connection
import javax.sql.CommonDataSource
import scala.collection.concurrent.Map
import scala.collection.concurrent.TrieMap

trait DB {

  def getConnection(connectionName: String): Connection

  val threadLocalMap = new ThreadLocal[Map[String, Connection]] {
    override def initialValue(): Map[String, Connection] = {
      new TrieMap[String, Connection]()
    }
  }

  def withThreadLocalConnection(connectionName: String)(body: Connection => Unit) = {
    val map = threadLocalMap.get
    var isFirst = false
    val c = map.get(connectionName)
    var connection: Connection = null
    if (c.isEmpty) {
      isFirst = true
      connection = getConnection(connectionName)
      map.put(connectionName, connection)
    } else {
      connection = c.get
    }

    try {
      body(connection)
    } finally {
      if (isFirst) {
        connection.close()
      }
    }
  }
}