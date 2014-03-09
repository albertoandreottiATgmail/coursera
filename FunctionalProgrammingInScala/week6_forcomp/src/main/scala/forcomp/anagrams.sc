package forcomp

object anagram {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  //Anagrams.wordOccurrences("MississIpi")
  
  //Anagrams.sentenceOccurrences(List("En el lago",  "titicaca una vieja se ahogo"))
  //Anagrams.wordAnagrams("case")
  
  //Anagrams.combinations(Anagrams.sentenceOccurrences(List("Linux", "rulez")))
  Anagrams.subtract(List(('r', 3), ('a', 4)), List(('r', 1)))
                                                  //> res0: forcomp.Anagrams.Occurrences = List((a,4), (r,2))
  //Anagrams.sentenceAnagrams(List("Linux", "rulez"))
  //val sentence = List("yes", "man")
  //Anagrams.combinations(Anagrams.sentenceOccurrences(sentence))
   Anagrams.dictionaryByOccurrences.get(Anagrams.wordOccurrences("o"))
                                                  //> res1: Option[List[forcomp.Anagrams.Word]] = None
}