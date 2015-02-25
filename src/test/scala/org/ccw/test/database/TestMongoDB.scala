package org.ccw.test.database

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class TestMondoDB extends JUnitSuite {
  def connection() {
    import reactivemongo.api._
    import scala.concurrent.ExecutionContext.Implicits.global

    // gets an instance of the driver
    // (creates an actor system)
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    // Gets a reference to the database "plugin"
    val db = connection("mydb")

    // Gets a reference to the collection "acoll"
    // By default, you get a BSONCollection.
    val collection = db("acoll")
  }

  def testMongo() {
    import com.mongodb.casbah.Imports._
    val mongoClient = MongoClient("localhost", 27017)

    val db = mongoClient("mydb")
    val data = db("testData")

    val allDocs = data.find()
    println(allDocs)
    for (doc <- allDocs) println(doc)

  }
}