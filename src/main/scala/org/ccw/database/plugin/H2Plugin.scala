package org.ccw.database.plugin

import org.h2.jdbcx.JdbcConnectionPool
import org.ccw.database.SimpleDBManager

object H2Plugin {

  def cloneTableToMemory(sourceDBName: String, sql: String) = {

    SimpleDBManager.withThreadLocalConnection(sourceDBName) {
      c =>
        {
          val s = c.createStatement()
          val rs = s.executeQuery(sql)
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
            
            println(s"$colName $colType $precision $scale")
            sb.append(metaData.getColumnName(i)).append(",")
          }
          
          // get the data 
          while (rs.next) {
            
          }
          
        }

    }
    /*
    val c = cp1.getConnection
    val s = c.createStatement()
    s.execute("")
    * 
    */
  }

}