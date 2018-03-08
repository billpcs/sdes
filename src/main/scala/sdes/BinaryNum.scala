package sdes


/**
  * A base trait for all the different sizes
  * (in bits) of the binary numbers
  */
trait BinaryNum {
  /*
    The decimal value of the current binary number
   */
  val value: Short

  /*
    Returns the binary representation of the number
   */
  def binary: String

  /*
    Get's the i'th bit, counting
    from 1 and starting from the MSB
  */
  def apply(n: Int): Bit

  /*
    Creates an array of bits
    from the current binary number
   */
  def toBits: Array[Bit]

  /*
    Shows the binary number in decimal and binary
   */
  override def toString: String = s"$value (0b$binary)"
}
