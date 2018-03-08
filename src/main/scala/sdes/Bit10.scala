package sdes

/**
  A class to represent 10bit binary numbers
  The value passed to the constructor will be
  converted to two's-complement (10bit)
  i.e the range will be [-512 .. 511]
  */
case class Bit10(private val v: Short) extends BinaryNum {

  def this(s: String) = this(Integer.parseInt(s, 2).toShort)

  def this(ba: Array[Bit]) = this(ba.mkString)

  // whatever the 'v' value is we must convert
  // it to a 10bit twos-complement representation
  // so do sign extension as required
  val value: Short = {
    // if the sign bit is a one, do sign extension
    if (((v >> 9) & 0x1).toByte == 1) (0xFFFFFC00 | (v & 0x3FF)).toShort
    else (v & 0x3FF).toShort
  }

  override def apply(n: Int): Bit =
    Bit(((this.value >> (10 - n)) & 0x1).toByte)

  def map(f: Short => Short) = Bit10(f(value))

  def flatMap(f: Short => Bit10): Bit10 = f(this.value)

  def map2(that: Bit10)(f: (Short, Short) => Short): Bit10 =
    for {
      a <- this
      b <- that
    } yield f(a, b)

  def |^|(that: Bit10): Bit10 = this.map2(that)((a, b) => (a ^ b).toShort)

  def toBits: Array[Bit] = (0 to 10).map(this(_)).toArray

  def binary = {
    val bin = Integer.toBinaryString(value)
    if (value < 0) bin.drop(22) else "0" * (10 - bin.length) + bin
  }

  override def equals(o: scala.Any): Boolean = o match {
    case x: Bit10 => this.value == x.value
    case _ => false
  }
}

/*
  Companion object for Bit10, in order to have convenient
  constructors throughout the library
 */
object Bit10 {
  def apply(v: Short) = new Bit10(v)

  def apply(s: String) = new Bit10(s)

  def apply(a: Array[Bit]) = new Bit10(a)
}
