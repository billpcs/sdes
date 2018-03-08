package sdes

/**
  * The class implementing the basic operations of Simplified DES
  */
class SDES(key: Bit10) extends SDESBase {
  def encryptByte(b: Byte) =
    SDESCompositions.encrypt(key)(Bit8(b)).value.toByte

  def decryptByte(b: Byte) =
    SDESCompositions.decrypt(key)(Bit8(b)).value.toByte
}

object SDES {
  def apply(key: Bit10): SDES = new SDES(key)

  def apply(key: String): SDES = new SDES(Bit10(key))

  def apply(key: Int): SDES = new SDES(Bit10(key.toShort))
}
