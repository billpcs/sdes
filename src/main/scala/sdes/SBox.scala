package sdes

/**
  * The implementation of the SBoxes used in SDES
  */
case class SBox(data: Array[Array[Short]]) {
  def apply(b: Bit4): Bit4 = {
    val row = Bit4(s"${b(1)}${b(4)}").value
    val col = Bit4(s"${b(2)}${b(3)}").value
    Bit4(data(row)(col))
  }
}

object SBox {

  val S0Data: Array[Array[Short]] = Array(
    Array(1, 0, 3, 2),
    Array(3, 2, 1, 0),
    Array(0, 2, 1, 3),
    Array(3, 1, 0, 3)
  ).map(_.map(_.toShort))

  val S1Data: Array[Array[Short]] = Array(
    Array(0, 1, 2, 3),
    Array(2, 0, 1, 3),
    Array(3, 0, 1, 0),
    Array(2, 1, 0, 3)
  ).map(_.map(_.toShort))

  val S0 = SBox(S0Data)
  val S1 = SBox(S1Data)

  def SBoxBlock(in: Bit8): Bit4 = {
    val (lh, rh) = in.split
    val res1 = S0(lh).value
    val res2 = S1(rh).value
    Bit4(((res1 << 2) | res2).toShort)
  }

}
