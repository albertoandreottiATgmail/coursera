package patmat
import Huffman._
object testing {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  Huffman.createCodeTree("Sweet home Alabama".toList)
                                                  //> res0: patmat.Huffman.CodeTree = Fork(Fork(Fork(Leaf( ,2),Leaf(m,2),List( , m
                                                  //| ),4),Fork(Fork(Leaf(b,1),Leaf(h,1),List(b, h),2),Fork(Leaf(A,1),Leaf(S,1),Li
                                                  //| st(A, S),2),List(b, h, A, S),4),List( , m, b, h, A, S),8),Fork(Fork(Fork(Lea
                                                  //| f(t,1),Leaf(w,1),List(t, w),2),Fork(Leaf(l,1),Leaf(o,1),List(l, o),2),List(t
                                                  //| , w, l, o),4),Fork(Leaf(a,3),Leaf(e,3),List(a, e),6),List(t, w, l, o, a, e),
                                                  //| 10),List( , m, b, h, A, S, t, w, l, o, a, e),18)
  val chars = "Sweet home Alabama".toList         //> chars  : List[Char] = List(S, w, e, e, t,  , h, o, m, e,  , A, l, a, b, a, m
                                                  //| , a)
  val leaves = times(chars).map(x => Leaf(x._1, x._2))
                                                  //> leaves  : List[patmat.Huffman.Leaf] = List(Leaf( ,2), Leaf(A,1), Leaf(S,1), 
                                                  //| Leaf(a,3), Leaf(b,1), Leaf(e,3), Leaf(h,1), Leaf(l,1), Leaf(m,2), Leaf(o,1),
                                                  //|  Leaf(t,1), Leaf(w,1))

  val tree = createCodeTree("Sweet Home Alabama".toList)
                                                  //> tree  : patmat.Huffman.CodeTree = Fork(Fork(Fork(Leaf( ,2),Leaf(m,2),List( ,
                                                  //|  m),4),Fork(Fork(Leaf(S,1),Leaf(b,1),List(S, b),2),Fork(Leaf(A,1),Leaf(H,1),
                                                  //| List(A, H),2),List(S, b, A, H),4),List( , m, S, b, A, H),8),Fork(Fork(Fork(L
                                                  //| eaf(t,1),Leaf(w,1),List(t, w),2),Fork(Leaf(l,1),Leaf(o,1),List(l, o),2),List
                                                  //| (t, w, l, o),4),Fork(Leaf(a,3),Leaf(e,3),List(a, e),6),List(t, w, l, o, a, e
                                                  //| ),10),List( , m, S, b, A, H, t, w, l, o, a, e),18)
      println(tree)                               //> Fork(Fork(Fork(Leaf( ,2),Leaf(m,2),List( , m),4),Fork(Fork(Leaf(S,1),Leaf(b,
                                                  //| 1),List(S, b),2),Fork(Leaf(A,1),Leaf(H,1),List(A, H),2),List(S, b, A, H),4),
                                                  //| List( , m, S, b, A, H),8),Fork(Fork(Fork(Leaf(t,1),Leaf(w,1),List(t, w),2),F
                                                  //| ork(Leaf(l,1),Leaf(o,1),List(l, o),2),List(t, w, l, o),4),Fork(Leaf(a,3),Lea
                                                  //| f(e,3),List(a, e),6),List(t, w, l, o, a, e),10),List( , m, S, b, A, H, t, w,
                                                  //|  l, o, a, e),18)
  val coded = encode(tree)("Sweet Home Alabama".toList)
                                                  //> coded  : List[patmat.Huffman.Bit] = List(0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1,
                                                  //|  1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 
                                                  //| 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0)
  decode(tree, coded)                             //> res1: List[Char] = List(S, w, e, e, t,  , H, o, m, e,  , A, l, a, b, a, m, a
                                                  //| )

}