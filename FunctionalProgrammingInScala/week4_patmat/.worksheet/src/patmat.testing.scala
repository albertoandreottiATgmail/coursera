package patmat
import Huffman._
object testing {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(92); 
  println("Welcome to the Scala worksheet");$skip(54); val res$0 = 
  Huffman.createCodeTree("Sweet home Alabama".toList);System.out.println("""res0: patmat.Huffman.CodeTree = """ + $show(res$0));$skip(42); 
  val chars = "Sweet home Alabama".toList;System.out.println("""chars  : List[Char] = """ + $show(chars ));$skip(55); 
  val leaves = times(chars).map(x => Leaf(x._1, x._2));System.out.println("""leaves  : List[patmat.Huffman.Leaf] = """ + $show(leaves ));$skip(58); 

  val tree = createCodeTree("Sweet Home Alabama".toList);System.out.println("""tree  : patmat.Huffman.CodeTree = """ + $show(tree ));$skip(20); 
      println(tree);$skip(56); 
  val coded = encode(tree)("Sweet Home Alabama".toList);System.out.println("""coded  : List[patmat.Huffman.Bit] = """ + $show(coded ));$skip(22); val res$1 = 
  decode(tree, coded);System.out.println("""res1: List[Char] = """ + $show(res$1))}

}
