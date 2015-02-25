package org.ccw.database

import java.sql.ResultSet
import java.sql.PreparedStatement
import scala.collection.mutable.{ Set => MutableSet }
import scala.collection.mutable.ListBuffer

trait ResultHelper {

  def iterateResultSet[T](rs: ResultSet)(f :ResultSet => T) = {
    val colCount = rs.getMetaData.getColumnCount
    while (rs.next()) {
      for (i <- 1 to colCount) {
        val data = rs.getString(i)
        if (data != null) {
          (data.trim())
        }
        f(rs)
      }
    }
  }

  def resultAsList[T](preparedStatement: PreparedStatement, rs2Model: ResultSet => T): List[T] = {
    val rs = preparedStatement.executeQuery()
    val list = ListBuffer[T]()
    while (rs.next()) {
      val model = rs2Model(rs)
      list.append(model)
    }
    list.toList
  }

  def resultAsSet[T](preparedStatement: PreparedStatement, rs2Model: ResultSet => T): Set[T] = {
    val rs = preparedStatement.executeQuery()
    var set = MutableSet[T]()
    while (rs.next()) {
      val model = rs2Model(rs)
      set.+=(model)
    }
    set.toSet
  }
}