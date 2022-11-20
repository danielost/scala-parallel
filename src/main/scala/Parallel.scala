package ua.nure.parallel

import scala.collection.parallel.ParSeq
import scala.util.Random

object Parallel {
  def multiThreadedIdiomatic(m1:Seq[Array[Double]], m2:Array[Array[Double]] ) ={
    val res =  Array.fill(m1.length, m2(0).length)(0.0)
    for(row <- m1.indices.par;
        col <- m2(0).indices.par;
        i <- m1.head.indices){
      res(row)(col) += m1(row)(i) * m2(i)(col)
    }
    res
  }

  def main(args: Array[String]): Unit = {
//    val m1 = Array.fill(5,5) { Random.nextDouble() }
//    val m2 = Array.fill(5,5) { Random.nextDouble() }

    val SIZE = 4096 * 4096
    val grades: Seq[Double] = Array.fill(SIZE) { Random.nextDouble() }
    val gradesPar: ParSeq[Double] = grades.par

    var t1 = System.nanoTime
    val gradesReducedPar = gradesPar.map(_ - 5)
    var duration = (System.nanoTime - t1) / 1e9d
    println("Parallel time: " + duration + "s")

    t1 = System.nanoTime
    val gradesReducedSeq = grades.map(_ - 5)
    duration = (System.nanoTime - t1) / 1e9d
    println("Sequential time: " + duration + "s")


//    val (m1, m2) = (Array(Array(2.0, 1.0), Array(1.0, 4.0)), Array(Array(1.0, 2.0, 0.0), Array(0.0, 1.0, 2.0)));
//    val m2 = Array(Array(1.0, 2.0, 0.0), Array(0.0, 1.0, 2.0));

//    multiThreadedIdiomatic(m1,m2) foreach { row => row foreach print; println }
  }
}
