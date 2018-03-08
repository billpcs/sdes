package sdes

import ConversionUtils._

/**
  A class to represent 4bit binary numbers
  The value passed to the constructor will be
  converted to two's-complement
  i.e the range will be [-8 .. 7]
  */
case class Bit4(private val v: Int) extends BinaryNum {

  def this(s: String) = this(Integer.parseInt(s, 2).toShort)

  def this(ar: Array[Bit]) = this(ar.mkString)

  // consider the input as a 4bit two's complement
  // so do sign extension as required
  val value: Short = {
    if (((v >> 3) & 0x1) == 1) (0xFFFFFFF0 | (v & 0xF)).toShort
    else (v & 0xF).toShort
  }

  override def apply(n: Int): Bit = Bit(((this.value >> (4 - n)) & 0x1).toByte)

  def map(f: Short => Short) = Bit4(f(this.value))

  def flatMap(f: Short => Bit4): Bit4 = f(this.value)

  def map2(that: Bit4)(f: (Short, Short) => Short): Bit4 =
    for {
      a <- this
      b <- that
    } yield f(a, b)

  def |^|(that: Bit4): Bit4 = this.map2(that)((a, b) => (a ^ b).toShort)

  def toBits: Array[Bit] = (1 to 4).map(this(_)).toArray

  def binary = intTo8BitString(value) drop 4

  def concat(that: Bit4): Bit8 =
    Bit8(((this.value << 4) | (that.value & 0xF)).toByte)

  override def equals(o: scala.Any): Boolean = o match {
    case x: Bit4 => this.value == x.value
    case _ => false
  }
}

/*
  Companion object for Bit4, in order to have convenient
  constructors throughout the library
 */
object Bit4 {
  def apply(v: Short) = new Bit4(v)

  def apply(s: String) = new Bit4(s)

  def apply(a: Array[Bit]) = new Bit4(a)
}
