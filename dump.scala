
def dump(s: String): Unit = {
  import scala.collection.mutable.StringBuilder
  val buf = new StringBuilder(64)
  val chars = new StringBuilder(32)

  def dumpLine = {
    print(buf)
    print("  ")
    println(chars)
    buf.setLength(0)
    chars.setLength(0)
  }

  for (i <- 0 until s.length) {
    if (i != 0) {
      if (i % 16 == 0) dumpLine
      else if (i % 8 == 0) buf.append("  ")
    }

    buf.append("%02x ".format(s(i).toInt))
    chars.append(s(i))
  }

  val extra = s.length % 16
  for (i <- 0 to extra if i != 0) buf.append("   ")
  if (extra < 9) buf.append("  ")
  dumpLine
}
