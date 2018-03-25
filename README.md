# Simplified DES in Scala

### Simplified Data Encryption Standard implemented in Scala

This is a very inefficient implementation of SDES I wrote as a small project for an introductory semester course in Computer and Network Security.

Given the fact that I knew it was going to be inefficient I mainly tried to apply some practices I learned in [Functional Programming In Scala](https://www.manning.com/books/functional-programming-in-scala), for example monads and **function composition**.

There are also tests which I like to think of as extensive -although I have no idea how extensive they really are. It was just the first time I gave up some time to write some tests and it really helped during the implementation of the algorithm. In other words I can confirm: It is not "giving up" time.

It also includes an implementation of the Triple Simplified DES, which is just an extension of SDES.

The "specification" **pdf** I used is [this one](https://app.box.com/s/06vnp1hiu3) which is appendix-C of the book _Cryptography and Network Security_ by _Stallings_.


### Quick Start

```
$ git clone https://github.com/billpcs/sdes
$ cd sdes
$ sbt console
```


```scala
scala> import sdes.SDES
import sdes.SDES

scala> val crypt = SDES(12)
crypt: sdes.SDES = sdes.SDES@3d19a90c

scala> crypt.encryptByte(12)
res0: Byte = -112

scala> crypt.decryptByte(res0)
res1: Byte = 12
```

For a bit more detailed approach you can have a look at `Main.scala` and the source code itself.

Run all the tests with:
```
$ sbt test
```

A blog post with some more details [here](https://billpcs.github.io/2018/03/sdes-in-scala.html)
