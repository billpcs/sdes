package sdes

/**
  * The class implementing the basic operations of Triple Simplified DES
  */
class TripleSDES(k1: Bit10, k2: Bit10, k3: Bit10) extends SDESBase {
  def encryptByte(b: Byte) = {
    val operation = {
      SDESCompositions.encrypt(k1) _ andThen
        SDESCompositions.decrypt(k2) andThen
        SDESCompositions.encrypt(k3)
    }
    operation(Bit8(b)).value.toByte
  }

  def decryptByte(b: Byte) = {
    val operation = {
      SDESCompositions.decrypt(k3) _ andThen
        SDESCompositions.encrypt(k2) andThen
        SDESCompositions.decrypt(k1)
    }
    operation(Bit8(b)).value.toByte
  }
}

object TripleSDES {
  def apply(k1: Bit10, k2: Bit10, k3: Bit10): TripleSDES =
    new TripleSDES(k1, k2, k3)

  def apply(k1: String, k2: String, k3: String): TripleSDES =
    new TripleSDES(Bit10(k1), Bit10(k2), Bit10(k3))

  def apply(k1: Int, k2: Int, k3: Int): TripleSDES =
    new TripleSDES(Bit10(k1.toShort), Bit10(k2.toShort), Bit10(k3.toShort))
}
