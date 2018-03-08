package sdes

object ConversionUtils {
  def binaryStringToInt(raw: String, n: Int = 8): Option[Short] = {
    if (raw.length != n || !raw.forall(c => c == '0' || c == '1')) None
    else {
      Some(Integer.parseUnsignedInt(raw, 2).toShort)
    }
  }

  def intTo8BitString(int: Int): String = {
    val res = Integer.toBinaryString(int)
    if (res.length > 8) res.takeRight(8)
    else "0" * (8 - res.length) + res
  }
}
