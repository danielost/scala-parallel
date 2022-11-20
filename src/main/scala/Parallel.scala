package ua.nure.parallel

object Parallel {
  def multiThreadedIdiomatic(m1:Seq[Array[Double]], m2:Array[Array[Double]] ) ={
    val res =  Array.fill(m1.length, m2(0).length)(0.0)
    for(row <- (0 until m1.length).par;
        col <- (0 until m2(0).length).par;
        i   <- 0 until m1(0).length){
      res(row)(col) += m1(row)(i) * m2(i)(col)
    }
    res
  }

  def main(args: Array[String]): Unit = {
//    val m1 = Array.fill(5,5) { Random.nextDouble() }
//    val m2 = Array.fill(5,5) { Random.nextDouble() }

    val m1 = Array(Array(2.0, 1.0), Array(1.0, 4.0));
    val m2 = Array(Array(1.0, 2.0, 0.0), Array(0.0, 1.0, 2.0));

    multiThreadedIdiomatic(m1,m2) foreach { row => row foreach print; println }
  }
}
