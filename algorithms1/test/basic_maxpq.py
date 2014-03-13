import unittest
from src.maxpq import MaxPQ

class TestBasicMaxPQOperations(unittest.TestCase):

    def setUp(self):
        pass

    def test_insertion(self):
        # No elements are lost
        self.queue = MaxPQ(10)
        inserted = set(['a', 'b', 'c','d'])
        
        for element in inserted:
            self.queue.insert(element)
            
        retrieved = set()
        
        while not self.queue.isEmpty():
            retrieved.add(self.queue.delMax())
        
        self.assertTrue(self.queue.isEmpty())
        self.assertEquals(retrieved, inserted, 'Insertion failure')
     
    def test_retrieval(self):
        
        queue = MaxPQ(10) 
        # The order is respected
        inserted = set(['z', 'x', 'k','d', 'a'])
        sortedKeys = sorted(list(inserted))
        sortedKeys.reverse() 
        
        for element in inserted:
            queue.insert(element)
        
        for key in sortedKeys:    
            self.assertEqual(queue.delMax(), key)
                
if __name__ == '__main__':
    unittest.main()
