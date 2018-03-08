package sdes

import sdes.SDESBitOperations._
import sdes.SBox._

/**
  * The low level composition chain SDES is based upon
  * (for the functions see SDESBitOperations.scala and SBox.scala)
  */
object SDESCompositions {

  def fk(k: Bit8)(a: Bit8): Bit8 = {
    val (lh, rh) = a.split
    val sBoxInput = EP(rh) |^| k
    val sBoxResult = SBoxBlock(sBoxInput)
    val perm = P4(sBoxResult)
    val res = perm |^| lh
    res concat rh
  }

  def encrypt(key: Bit10)(plaintext: Bit8): Bit8 = {
    val k1 = (P10 _ andThen shift andThen P8)(key)
    val k2 = (P10 _ andThen shift andThen shift andThen P8)(key)
    (IPinv _ compose fk(k2) compose swap compose fk(k1) compose IP)(plaintext)
  }

  def decrypt(key: Bit10)(cyphertext: Bit8): Bit8 = {
    val k1 = (P10 _ andThen shift andThen P8)(key)
    val k2 = (P10 _ andThen shift andThen shift andThen P8)(key)
    (IPinv _ compose fk(k1) compose swap compose fk(k2) compose IP)(cyphertext)
  }

}
