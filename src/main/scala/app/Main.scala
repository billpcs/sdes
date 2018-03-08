package app

import sdes.{SDES, TripleSDES}

/**
  * A simple application to demonstrate the usage of the library
  */
object Main {


  def main(args: Array[String]): Unit = {

    /*
        A 10bit number for the key.
        You can also specify it as a binary string
    */
    val key: Short = 12

    /*
        Create a "encryptor/decryptor" instance of SDES
    */
    val sdes = SDES(key)

    // Some data to encrypt
    val dataArray: Array[Byte] = Array(-10, 5, 3, 9, 11) map (_.toByte)

    // Encrypt them
    val encryptedArray = sdes encryptByteArray dataArray

    // And decrypt them again
    val decryptedArray = sdes decryptByteArray encryptedArray

    println("\n\n--- START OF SIMPLE DES EXAMPLE ---")
    println(s"Initial array: ${dataArray.mkString(" ")}")
    println(s"Encrypted array: ${encryptedArray.mkString(" ")}")
    println(s"Decrypted array:${decryptedArray.mkString(" ")}")
    val same = if (dataArray sameElements decryptedArray) "" else "NOT "
    println(s"The two arrays are ${same}equal")
    println("--- END OF SIMPLE DES EXAMPLE ---\n\n")

    /*
      ### A second test using triple SDES
    */

    /*
        TripleSDES needs three keys
        You can specify them as binary strings too.
    */
    val tsdes = TripleSDES(10.toShort, 11.toShort, 12.toShort)

    val dataArrayT: Array[Byte] = Array(-10, 5, 3, 9, 11) map (_.toByte)

    val encryptedArrayT = tsdes encryptByteArray dataArrayT

    val decryptedArrayT = tsdes decryptByteArray encryptedArrayT

    println("--- START OF TRIPLE SDES EXAMPLE ---")
    println(s"Initial array: ${dataArrayT.mkString(" ")}")
    println(s"Encrypted array: ${encryptedArrayT.mkString(" ")}")
    println(s"Decrypted array:${decryptedArrayT.mkString(" ")}")
    val sameT = if (dataArray sameElements decryptedArrayT) "" else "NOT "
    println(s"The two arrays are ${sameT}equal")
    println("--- END OF TRIPLE SDES EXAMPLE ---")

  }


}
