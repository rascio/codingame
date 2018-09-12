(ns l99.lists)

;http://www.ic.unicamp.br/~meidanis/courses/mc336/2006s2/funcional/L-99_Ninety-Nine_Lisp_Problems.html

(defn my-last
    "ind the last box of a list."
    [[head & tail]]
    (if (nil? tail)
      head
      (my-last tail)))

(defn my-but-last
  "Find the last but one box of a list.
      Example:
      * (my-but-last '(a b c d))
      (C D)"
  [[first second & tail]]
  (if (nil? tail)
    (seq [first second])
    (my-but-last (cons second tail))))

(defn element-at
  "P03 (*) Find the K'th element of a list.
        The first element in the list is number 1.
        Example:
        * (element-at '(a b c d e) 3)
        C"
  [[head & tail] index]
  (if (= 0 index)
    head
    (element-at tail (dec index))))

(defn length
  "P04 (*) Find the number of elements of a list."
  [[head & tail]]
  (if (and (nil? tail)
           (nil? head))
    0
    (inc (length tail))))
