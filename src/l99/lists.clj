(ns l99.lists)

;http://www.ic.unicamp.br/~meidanis/courses/mc336/2006s2/funcional/L-99_Ninety-Nine_Lisp_Problems.html

(defn my-last
    "ind the last box of a list."
    [[head & tail]]
    (if (nil? tail)
      head
      (my-last tail)))
