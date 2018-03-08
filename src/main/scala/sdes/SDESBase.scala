package sdes

/**
  * The base trait for both Triple SDES and Single SDES
 */


trait SDESBase {

  def encryptByte(b: Byte): Byte

  def decryptByte(b: Byte): Byte

  def encryptByteArray(b: Array[Byte]): Array[Byte] =
    b map encryptByte

  def decryptByteArray(b: Array[Byte]): Array[Byte] =
    b map decryptByte

}
