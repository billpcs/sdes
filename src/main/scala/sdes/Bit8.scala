package sdes

import ConversionUtils._

/**
  A class to represent 8bit binary numbers
  The value passed to the constructor will be
  converted to two's-complement (8bit)
  i.e the range will be [-128 .. 127]
  */
case class Bit8(v: Int) extends BinaryNum {

  def this(str: String) = this(Integer.parseInt(str, 2).toShort)

  def this(ba: Array[Bit]) = this(ba.mkString)

  // if we convert to byte then we automatically
  // have an 8bit twos-complement number
  val value: Short = v.toByte.toShort

  override def apply(n: Int): Bit = Bit(((this.value >> (8 - n)) & 0x1).toByte)

  def map(f: Short => Short) = Bit8(f(value))

  def flatMap(f: Short => Bit8): Bit8 = f(this.value)

  def map2(that: Bit8)(f: (Short, Short) => Short): Bit8 =
    for {
      a <- this
      b <- that
    } yield f(a, b)

  def |^|(that: Bit8): Bit8 = this.map2(that)((a, b) => (a ^ b).toShort)

  def toBits: Array[Bit] = (1 to 8).map(this(_)).toArray

  def binary: String = intTo8BitString(value)

  def split: (Bit4, Bit4) =
    (Bit4((value >> 4).toShort), Bit4((value & 0xF).toShort))

  override def equals(o: scala.Any): Boolean = o match {
    case x: Bit8 => this.value == x.value
    case _ => false
  }

}

/*
  Companion object for Bit8, in order to have convenient
  constructors throughout the library
 */
object Bit8 {
  def apply(v: Short) = new Bit8(v)

  def apply(s: String) = new Bit8(s)

  def apply(a: Array[Bit]) = new Bit8(a)
}
