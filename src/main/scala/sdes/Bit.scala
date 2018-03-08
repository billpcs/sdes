package sdes

/**
  * A class to keep a bit as a standalone data-type
  */

case class Bit(private val v: Byte) {
  val value = v % 2
  override def toString: String = value.toString
}
