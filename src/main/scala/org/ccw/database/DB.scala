package org.ccw.database

import java.sql.Connection
import javax.sql.CommonDataSource
import scala.collection.concurrent.Map
import scala.collection.concurrent.TrieMap

trait DB {

  protected[this] def getConnection(connectionName: String): Option[Connection]

  val threadLocalMap = new ThreadLocal[Map[String, Connection]] {
    override def initialValue(): Map[String, Connection] = {
      new TrieMap[String, Connection]()
    }
  }

  def withNewConnection(connectionName: String)(body: Connection => Unit) = {
    var connectionOpt = getConnection(connectionName)
    if (connectionOpt == None) {
      throw new Exception("Connection Name not found")
    } else {
      try {
        body(connectionOpt.get)
      } finally {
        connectionOpt.get.close()
      }
    }
  }

  def withThreadLocalConnection(connectionName: String)(body: Connection => Unit) = {
    val map = threadLocalMap.get
    var isFirst = false
    var connectionOpt: Option[Connection] = map.get(connectionName)
    var connection: Connection = null

    if (map.get(connectionName).isEmpty) {
      isFirst = true
      connectionOpt = getConnection(connectionName)
      if (connectionOpt != None) {
        map.put(connectionName, connectionOpt.get)
        connection = connectionOpt.get
      }
    } else {
      connection = map.get(connectionName).get
    }

    if (connection != null) {
      try {
        body(connection)
      } finally {
        if (isFirst) {
          connection.close()
        }
      }

    } else {
      throw new Exception("Connection not found")
    }

  }
}