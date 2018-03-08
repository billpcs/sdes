import sdes.{Bit8, Bit10}
import sdes.SDESBitOperations._
import sdes.{SDES, SDESCompositions}
import org.scalatest.FunSuite

/*
  This whole unit test is based on an example we solved
  inside the lecture, thus I knew the correct values that
  the implementation should output if done correctly.
 */

class UnitTests extends FunSuite {
  val plaintext = Bit8("10100101")
  val key = Bit10("0010010111")
  val t1 = Bit10("1000010111")
  val t2 = Bit10("0000101111")
  val k1 = Bit8("00101111")
  val t4 = Bit8("01110100")
  val t5 = Bit8("00101000")
  val t6 = Bit8("00000111")

  test("P10 of the key") {
    assert(P10(key) == t1)
  }

  test("shift of t1") {
    assert(shift(t1) == t2)
  }

  test("P8 of t2") {
    assert(P8(t2) == k1)
  }

  test("IP of plaintext") {
    assert(IP(plaintext) == t4)
  }

  test("expansion permutation of right half of t4") {
    val (lh, rh) = t4.split
    assert(EP(rh) == t5)
  }

  test("t5 xor k1") {
    assert((t5 |^| k1) == t6)
  }

  test("fk output") {
    assert(SDESCompositions.fk(k1)(t4) == Bit8("10010100"))
  }

  // some overall tests taken from another implementation
  test("[1] k: 0000000001, v: 00000000, r: 10110101") {
    assert(
      SDESCompositions.encrypt(Bit10("0000000001"))(Bit8("00000000")) == Bit8(
        "10110101"))
  }

  test("[2] k: 1000000001, v: 00000001, r: 00100110") {
    assert(
      SDESCompositions.encrypt(Bit10("1000000001"))(Bit8("00000001")) == Bit8(
        "00100110"))
  }

  test("[3] k: 1111000011, v: 10100101, r: 10011101") {
    assert(
      SDESCompositions.encrypt(Bit10("1111000011"))(Bit8("10100101")) == Bit8(
        "10011101"))
  }

  test("[4] k: -61, v: -91, r: -99") {
    val sdes = SDES((-61).toShort)
    sdes.encryptByte(-91) == -99
  }

}
