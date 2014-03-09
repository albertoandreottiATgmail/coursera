package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {

    if (c < 0 || r < 0 || c > r) return 0
    if (c == 0 || r == 0) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)

  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def check(chars: List[Char], count: Int): Int = {

      if (count < 0) return -1

      if (chars.isEmpty) return count

      chars.head match {
        case ')' => return check(chars.tail, count - 1)
        case '(' => return check(chars.tail, count + 1)
        case _ => return check(chars.tail, count)
      }
    }
    check(chars, 0) == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def enum(sum: Int, num: List[Int], count: Int): Int = {

      if (sum == money) return count + 1
      if (sum > money | num.isEmpty) return count

      val same = enum(sum + num.head, num, count)
      enum(sum, num.tail, same)

    }

    enum(0, coins, 0)
  }
}
