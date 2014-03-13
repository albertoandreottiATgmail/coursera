class MaxPQ(object):
    
    
    def __init__(self, capacity):
        
        self._N = 0
        self._pq = [None] * (capacity + 1) 
        
    def isEmpty(self):
        return self._N == 0
    
    def delMax(self):
        
        if self._N != 0:
            top = self._pq[1]
        else:
            raise EmptyQueueException
        
        self._pq[1] = self._pq[self._N]
        self._pq[self._N] = None
        self._sink(1)
        
        self._N -= 1
   
        return top
    
    def insert(self, key):
        
        self._N = newIndex = self._N + 1
        assert(len(self._pq) >= newIndex, 'Not enough space in the heap')
        self._pq[newIndex] = key
        self._swim(newIndex) 
    
    def _swim(self, k):
        
        while k > 1 and self._less(k/2, k):
            self._exchange(k, k/2)
            k = k / 2
            
    def _sink(self, k):
        
        while self._pq[k] < max(self._pq[2 * k], self._pq[2 * k + 1]):
            if self._pq[2 * k] > self._pq[2 * k + 1]:
                self._exchange(k, 2 * k)
            else:
                self._exchange(k, 2 * k + 1)
                
            k = 2 * k
            
    
    def _less(self, k, j):
        return self._pq[k] < self._pq[j]
    
    def _exchange(self, k, j):
        
        tmp = self._pq[k]
        self._pq[k] = self._pq[j]
        self._pq[j] = tmp 

#TODO: make this immutable        
class Key(object):
    
    def __init__(self, key):
        self._key = key
    
class EmptyQueueException(Exception):
    pass    
        