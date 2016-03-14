import Functions.{Unregularized, Regularizer}
import breeze.linalg.DenseVector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

import scala.math._

/**
  * Created by amirreza on 09/03/16.
  */
class LogisticRegression(data: RDD[LabeledPoint],
                         //No regularizer term by default:
                         regularizer: Regularizer = new Unregularized,
                         lambda: Double = 0.01) {
  val ITERATIONS = 50

  def train(): DenseVector[Double] ={
    // Initialize w to zero
    val D = data.first().features.size
    var w = DenseVector.fill(D){0.0}
    println("Initial w: " + w)

    for (i <- 1 to ITERATIONS) {
      val gradient = data.map { p =>
        DenseVector(p.features.toArray) *
          (1.0 / (1.0 + exp(- p.label * (w.dot(DenseVector(p.features.toArray))))) - 1.0) *
          p.label
      }.reduce(_ + _)
      w -= (gradient + lambda * regularizer.subgradient(w))
    }
    println("Final w: " + w)
    return w;
  }
}
