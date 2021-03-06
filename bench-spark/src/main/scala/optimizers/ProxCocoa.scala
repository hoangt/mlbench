package optimizers

import l1distopt.solvers.ProxCoCoAp
import l1distopt.utils.DebugParams
import utils.Functions.{LossFunction, ProxCocoaDataMatrix, Regularizer}
import breeze.linalg.Vector

/**
  * Created by amirreza on 15/04/16.
  */


class ProxCocoa(val data: ProxCocoaDataMatrix,
                loss: LossFunction,
                regularizer: Regularizer,
                params: ProxCocoaParameters,
                debug: DebugParams) extends Optimizer (loss, regularizer){
  override def optimize(): Vector[Double] = {
    val finalAlphaCoCoA = ProxCoCoAp.runProxCoCoAp(data._1, data._2, params.getL1DistOptPar(), debug)
    return finalAlphaCoCoA
  }
}
