package com.vikas

import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll

class SparkPiSpec extends AnyFunSuite with BeforeAndAfterAll {

  private var spark: SparkSession = _

  override protected def beforeAll(): Unit = {
    spark = SparkSession.builder()
      .appName("SparkPiSpec")
      .master("local[2]")
      .config("spark.ui.enabled", "false")
      .getOrCreate()
  }

  override protected def afterAll(): Unit = {
    if (spark != null) spark.stop()
  }

  test("SparkSession works for basic operations") {
    val n = 10L
    val count = spark.range(n).count()
    assert(count == n)
  }

  test("SparkPi.computePi is deterministic and close to Math.PI") {
    val pi = SparkPi.computePi(spark, 20000)
    val error = math.abs(pi - Math.PI)
    assert(error < 0.03, s"Estimated pi=$pi is too far from ${Math.PI}")
  }
}
