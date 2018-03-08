import sdes.{Bit4, Bit8}
import sdes.{SDES, TripleSDES}
import sdes.SDESCompositions._
import sdes.SDESBitOperations._
import sdes.ConversionUtils._

import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.{forAll, forAllNoShrink}

class PropertyBasedTests extends Properties("BinN") {

  val binElementGenerator = Gen.chooseNum(0, 1)
  val byteGenerator =
    Gen.chooseNum(Byte.MinValue, Byte.MaxValue).map(_.toShort)
  val halfByteGenerator = Gen.chooseNum(-8, 7).map(_.toShort)
  val binStringGenerator = (n: Int) =>
    Gen.listOfN(n, binElementGenerator).map(_.mkString)
  val stringGenerator = (n: Int) =>
    Gen.listOf(n, Gen.alphaChar).map(_.mkString)

  val binPairGenerator = (n: Short) =>
    for {
      a <- binStringGenerator(n)
      b <- binStringGenerator(n)
    } yield (a, b)

  val bytePairGenerator = for {
    a <- byteGenerator
    b <- byteGenerator
  } yield (a, b)

  val halfBytePairGenerator = for {
    a <- halfByteGenerator
    b <- halfByteGenerator
  } yield (a, b)

  val stringKeyPairGenerator = (n: Int) =>
    for {
      a <- stringGenerator(n)
      k <- binStringGenerator(10)
    } yield (a, k)

  val byteKeyPairGenerator = for {
    a <- byteGenerator
    k <- binStringGenerator(10)
  } yield (a, k)

  val byte3KeyPairGenerator = for {
    a <- byteGenerator
    k1 <- binStringGenerator(10)
    k2 <- binStringGenerator(10)
    k3 <- binStringGenerator(10)
  } yield (a, k1, k2, k3)

  property("binary representation bit4") = forAllNoShrink(halfByteGenerator) {
    hb: Short =>
      val b4 = Bit4(hb).binary
      val res = Integer.toBinaryString(hb)
      val padded =
        if (res.length < 4) "0" * (4 - res.length) + res else res.takeRight(4)
      padded == b4
  }

  property("correct range bit4") =
    forAll(Gen.chooseNum(Short.MinValue, Short.MaxValue)) { i: Short =>
      Bit4(i).value <= 7 && Bit4(i).value >= -8
    }

  property("split a bit8") = forAllNoShrink(binStringGenerator(8)) {
    str: String =>
      val (a, b) = str splitAt 4
      val bit8 = Bit8(binaryStringToInt(str).get)
      val (af, bf) = bit8.split
      a == af.binary && b == bf.binary
  }

  property("concat two bit4") = forAllNoShrink(binPairGenerator(4)) {
    case (s1, s2) =>
      val (b1, b2) =
        (binaryStringToInt(s1, 4).get, binaryStringToInt(s2, 4).get)
      (Bit4(b1) concat Bit4(b2)).binary == (s1 + s2)
  }

  property("xor bit8") = forAll(bytePairGenerator) {
    case (a, b) =>
      (Bit8(a) |^| Bit8(b)).value == (a ^ b)
  }

  property("xor bit4") = forAll(halfBytePairGenerator) {
    case (a, b) =>
      (Bit4(a) |^| Bit4(b)).value == (a ^ b)
  }

  property("swap by 4") = forAll(byteGenerator) { a: Short =>
    swap(swap(Bit8(a))) == Bit8(a)
  }

  property("fk must cancel out") = forAll(bytePairGenerator) {
    case (a: Short, b: Short) =>
      val pt = Bit8(a)
      val k1 = Bit8(b)
      fk(k1)(fk(k1)(pt)) == pt
  }

  property("decrypt the encrypted (for bytes) [lib.SDES]") =
    forAllNoShrink(byteKeyPairGenerator) {
      case (a: Short, k: String) =>
        val sdes = SDES(k)
        (sdes.encryptByte _ andThen sdes.decryptByte)(a.toByte) == a
    }

  property("decrypt the encrypted (for bytes) [3SDES]") =
    forAllNoShrink(byte3KeyPairGenerator) {
      case (a: Short, k1: String, k2: String, k3: String) =>
        val sdes = TripleSDES(k1, k2, k3)
        (sdes.encryptByte _ andThen sdes.decryptByte)(a.toByte) == a
    }

}
