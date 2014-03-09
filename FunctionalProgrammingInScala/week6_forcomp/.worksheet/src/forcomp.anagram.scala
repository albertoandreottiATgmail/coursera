package forcomp

object anagram {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(77); 
  println("Welcome to the Scala worksheet");$skip(313); val res$0 = 
  
  //Anagrams.wordOccurrences("MississIpi")
  
  //Anagrams.sentenceOccurrences(List("En el lago",  "titicaca una vieja se ahogo"))
  //Anagrams.wordAnagrams("case")
  
  //Anagrams.combinations(Anagrams.sentenceOccurrences(List("Linux", "rulez")))
  Anagrams.subtract(List(('r', 3), ('a', 4)), List(('r', 1)));System.out.println("""res0: forcomp.Anagrams.Occurrences = """ + $show(res$0));$skip(229); val res$1 = 
  //Anagrams.sentenceAnagrams(List("Linux", "rulez"))
  //val sentence = List("yes", "man")
  //Anagrams.combinations(Anagrams.sentenceOccurrences(sentence))
   Anagrams.dictionaryByOccurrences.get(Anagrams.wordOccurrences("o"));System.out.println("""res1: Option[List[forcomp.Anagrams.Word]] = """ + $show(res$1))}
}
