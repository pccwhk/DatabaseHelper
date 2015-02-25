package org.ccw.test.database

import org.scalatest.junit.JUnitSuite
import scala.collection.mutable.ListBuffer
import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import org.h2.jdbcx.JdbcConnectionPool
import org.ccw.database.SimpleDBManager

object TestHelper {

  val cp1 = JdbcConnectionPool.create(
    "jdbc:h2:mem:test1;INIT=runscript from 'classpath:h2Init.sql' ", "sa", "sa");

  val cp2 = JdbcConnectionPool.create(
    "jdbc:h2:mem:test2;INIT=runscript from 'classpath:h2Init.sql' ", "sa", "sa");

  val name1 = "h2InMem1"
  val name2 = "h2InMem2"

  SimpleDBManager.registerConnectionPool(name1, cp1)
  SimpleDBManager.registerConnectionPool(name2, cp2)
}

class TestHelper extends JUnitSuite {

  def fakeDataService1() {
    SimpleDBManager.withThreadLocalConnection(TestHelper.name1) {
      c =>
        {
          println("call fake service 1")
          println("fake service 1 " + c.getClientInfo)
        }
    }
  }

  def fakeDataService2() {
    SimpleDBManager.withThreadLocalConnection(TestHelper.name1) {
      c =>
        {
          println("call fake service 2")
          println("fake service 2 " + c.getClientInfo)
          fakeDataService1
        }
    }
  }

  @Test def testDBManager() {
    SimpleDBManager.withThreadLocalConnection(TestHelper.name1) {
      c =>
        {
          println("testng Db manager now")
          fakeDataService2
        }
    }

  }

  @Test def verifyTestResourceClassPath() {

    val a = TestHelper.cp1.getConnection
    val s = a.createStatement()
    val rs = s.executeQuery("select * from stock ")
    val metaData = rs.getMetaData

    val colCount = metaData.getColumnCount
    val sb = new StringBuilder
    for (i <- 1 to colCount) {

      val colType = metaData.getColumnType(i)
      val colName = metaData.getColumnName(i)
      val colTypeName = metaData.getColumnTypeName(i)
      val precision = metaData.getPrecision(i)
      val scale = metaData.getScale(i)
      val tableName = metaData.getTableName(i)
      
      println(s"$colName $colType $precision $scale $tableName $colTypeName")
      sb.append(metaData.getColumnName(i)).append(",")
    }
    sb.deleteCharAt(sb.length - 1)
    sb.append("\r\n")

    while (rs.next()) {
      for (i <- 1 to colCount) {
        val data = rs.getString(i)
        if (data != null) {
          sb.append(data.trim()).append(",")
        }
      }
      sb.deleteCharAt(sb.length - 1)
      sb.append("\r\n")
    }
    println(sb.toString)
  }
}