package forcomp

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Anagrams._

@RunWith(classOf[JUnitRunner])
class AnagramsSuite extends FunSuite {

  test("wordOccurrences: abcd") {
    assert(wordOccurrences("abcd").sorted === List(('a', 1), ('b', 1), ('c', 1), ('d', 1)).sorted)
  }

  test("wordOccurrences: Robert") {
    assert(wordOccurrences("Robert").sorted === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)).sorted)
  }



  test("sentenceOccurrences: abcd e") {
    assert(sentenceOccurrences(List("abcd", "e")).sorted === List(('a', 1), ('b', 1), ('c', 1), ('d', 1), ('e', 1)).sorted)
  }



  test("dictionaryByOccurrences.get: eat") {
    assert(dictionaryByOccurrences.get(List(('a', 1), ('e', 1), ('t', 1))).map(_.toSet) === Some(Set("ate", "eat", "tea")))
  }



  test("word anagrams: married") {
    assert(wordAnagrams("married").toSet === Set("married", "admirer"))
  }

  test("word anagrams: player") {
    assert(wordAnagrams("player").toSet === Set("parley", "pearly", "player", "replay"))
  }



  test("subtract: lard - r") {
    val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
    val r = List(('r', 1))
    val lad = List(('a', 1), ('d', 1), ('l', 1))
    assert(subtract(lard, r) === lad)
  }



  test("combinations: []") {
    assert(combinations(Nil) === List(Nil))
  }

  test("combinations: abba") {
    val abba = List(('a', 2), ('b', 2))
    val abbacomb = List(
      List(),
      List(('a', 1)),
      List(('a', 2)),
      List(('b', 1)),
      List(('a', 1), ('b', 1)),
      List(('a', 2), ('b', 1)),
      List(('b', 2)),
      List(('a', 1), ('b', 2)),
      List(('a', 2), ('b', 2))
    )
    assert(combinations(abba).toSet === abbacomb.toSet)
  }



  test("sentence anagrams: []") {
    val sentence = List()
    assert(sentenceAnagrams(sentence) === List(Nil))
  }

 

    test("sentence anagrams: I love you") {
    val sentence = List("I", "love", "you")
    val anas = List(List("Lev", "you", "Io"),
	List("Io", "you", "Lev"), 
	List("Io", "Lev", "you"), 
	List("you", "Io", "Lev"), 
	List("you", "olive"), 
	List("you", "Lev", "Io"), 
	List("Lev", "Io", "you"), 
	List("olive", "you"))
    assert(sentenceAnagrams(sentence).toSet === anas.toSet)
  }
  
  
}
