package ua.nure.parallel

import scala.util.Random

object Parallel {
  private val SIZE = 1000
  private val ITERATIONS_AM = 10

  private def multiplyMatrices(m1:Array[Array[Double]], m2:Array[Array[Double]] ): Array[Array[Double]] ={
    val res =  Array.fill(m1.length, m2(0).length)(0.0)
    for(row <- m1.indices.par;
        col <- m2(0).indices.par;
        i <- m1.head.indices) {
      res(row)(col) += m1(row)(i) * m2(i)(col)
    }
    res
  }

  def main(args: Array[String]): Unit = {
    val (m1, m2) = (
      Array.fill(SIZE,SIZE) { Random.nextDouble() },
      Array.fill(SIZE,SIZE) { Random.nextDouble() }
    );

    var (min, sum) = (
      Double.MaxValue,
      0.0
    )

    for (i <- 0 until ITERATIONS_AM) {
      val start = System.nanoTime
      multiplyMatrices(m1,m2)
      var duration = (System.nanoTime - start) / 1e9d
      sum += duration
      min = Math.min(min,duration)
    }

    println("Best time:\t\t" + min + "s")
    println("Average time:\t" + sum / ITERATIONS_AM + "s")
  }
}
