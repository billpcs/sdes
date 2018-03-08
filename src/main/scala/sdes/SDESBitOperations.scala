package sdes

/**
  * The low level bit operations SDES is based upon
  */
object SDESBitOperations {

  // P10: 3 5 2 7 4 10 1 9 8 6
  def P10(in: Bit10): Bit10 = {
    val perms = Array(3, 5, 2, 7, 4, 10, 1, 9, 8, 6)
    Bit10(perms map (in(_)))
  }

  // P8: 6 3 7 4 8 5 10 9
  def P8(in: Bit10): Bit8 = {
    val perms = Array(6, 3, 7, 4, 8, 5, 10, 9)
    Bit8(perms map (in(_)))
  }

  def shift(in: Bit10): Bit10 = {
    val perms = Array(2, 3, 4, 5, 1, 7, 8, 9, 10, 6)
    Bit10(perms map (in(_)))
  }

  private[this] def IPHelper(in: Bit8, perms: Array[Int]): Bit8 = {
    Bit8(perms map (in(_)))
  }

  // IP: 2 6 3 1 4 8 5 7
  def IP(in: Bit8): Bit8 =
    IPHelper(in, Array(2, 6, 3, 1, 4, 8, 5, 7))

  // IPinv: 4 1 3 5 7 2 8 6
  def IPinv(in: Bit8): Bit8 =
    IPHelper(in, Array(4, 1, 3, 5, 7, 2, 8, 6))

  // E/P: 4 1 2 3 2 3 4 1
  def EP(in: Bit4): Bit8 = {
    val perms = Array(4, 1, 2, 3, 2, 3, 4, 1)
    Bit8(perms map (in(_)))
  }

  // P4: 2 4 3 1
  def P4(in: Bit4): Bit4 = {
    val perms = Array(2, 4, 3, 1)
    Bit4(perms map (in(_)))
  }

  def swap(in: Bit8): Bit8 = {
    val perms = Array(5, 6, 7, 8, 1, 2, 3, 4)
    Bit8(perms map (in(_)))
  }

}
