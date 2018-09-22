(ns l99.lists
  (:require [l99.utils :as u]))

(def when-not-empty
  (u/when-match
    (fn [[head & tail]]
      (and (nil? head) (nil? tail)))))

;http://www.ic.unicamp.br/~meidanis/courses/mc336/2006s2/funcional/L-99_Ninety-Nine_Lisp_Problems.html

(defn my-last
    "P01 (*) Find the last box of a list.
      Example:
      * (my-last '(a b c d))
      (D)
      "
    [[head & tail]]
    (if (nil? tail)
      head
      (my-last tail)))

(defn my-but-last
  "P02 (*) Find the last but one box of a list.
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
  (if (nil? head)
    0
    (inc (length tail))))

(defn my-reverse
  "P05 (*) Reverse a list."
  ([elements] (my-reverse elements '()))
  ([[head & tail] res]
   (if (nil? head)
     '()
     (if (nil? tail)
       (cons head res)
       (my-reverse tail (cons head res))))))

(defn palindrome?
  "P06 (*) Find out whether a list is a palindrome.
      A palindrome can be read forward or backward; e.g. (x a m a x)."
  [elements]
  (= elements (my-reverse elements)))

(defn my-flatten
  "P07 (**) Flatten a nested list structure.
      Transform a list, possibly holding lists as elements into a `flat' list by replacing each list with its elements (recursively).
      Example:
      * (my-flatten '(a (b (c d) e)))
      (A B C D E)"
  ([[head & tail]]
   (if (nil? head)
     '()
     (let [res (my-flatten tail)]
       (if (seq? head)
         (-> head
             my-flatten
             (concat res))
         (cons head res))))))

(defn compress
  "P08 (**) Eliminate consecutive duplicates of list elements.
      If a list contains repeated elements they should be replaced with a single copy of the element. The order of the elements should not be changed.
      Example:
      * (compress '(a a a a b c c a a d e e e e))
      (A B C A D E)"
  [[head & tail]]
  (if (nil? tail)
    (seq [head])
    (let [[last & _ :as res] (compress tail)]
      (if (= head last)
        res
        (cons head res)))))

(defn pack
  "P09 (**) Pack consecutive duplicates of list elements into sublists.
      If a list contains repeated elements they should be placed in separate sublists.
      Example:
      * (pack '(a a a a b c c a a d e e e e))
      ((A A A A) (B) (C C) (A A) (D) (E E E E))"
  [[head & tail]]
  (if (nil? tail)
    (seq [[head]])
    (let [[[last & _ :as current] & tail :as res] (pack tail)]
      (if (= head last)
        (cons (cons head current) tail)
        (cons (seq [head]) res)))))

(defn encode
  "P10 (*) Run-length encoding of a list.
      Use the result of problem P09 to implement the so-called run-length encoding data compression method. Consecutive duplicates of elements are encoded as lists (N E) where N is the number of duplicates of the element E.

      Example:
      * (encode '(a a a a b c c a a d e e e e))
      ((4 A) (1 B) (2 C) (2 A) (1 D)(4 E))"
  [elements]
  (for [chars (pack elements)
        :let [encoded (seq [(length chars) (my-last chars)])]]
    encoded))

(defn encode-modified
  "P11 (*) Modified run-length encoding.
      Modify the result of problem P10 in such a way that if an element has no duplicates it is simply copied into the result list. Only elements with duplicates are transferred as (N E) lists.

      Example:
      * (encode-modified '(a a a a b c c a a d e e e e))
      ((4 A) B (2 C) (2 A) D (4 E))"
  [elements]
  (for [[char & _ :as chars] (pack elements)
        :let [size (length chars)
              encoded (if (= 1 size)
                        char
                        (seq [size char]))]]
    encoded))

(defn -resolve
  [e]
  (if (seq? e)
    (let [[size char] e] (repeat size char))
    (seq [e])))
(defn decode
  "P12 (**) Decode a run-length encoded list.
      Given a run-length code list generated as specified in problem P11. Construct its uncompressed version."
  [[head & tail]]
  (let [chunk (apply -resolve [head])]
    (if (nil? tail)
      (seq chunk)
      (concat chunk (decode tail)))))

(defn dupli
  "P14 (*) Duplicate the elements of a list.
      Example:
      * (dupli '(a b c c d))
      (A A B B C C C C D D)"
  [elements]
  (mapcat #(repeat 2 %) elements))

(defn repli
  "P15 (**) Replicate the elements of a list a given number of times.
      Example:
      * (repli '(a b c) 3)
      (A A A B B B C C C)"
  [elements n]
  (mapcat #(repeat n %) elements))

(defn drop-every
  "P16 (**) Drop every N'th element from a list.
      Example:
      * (drop '(a b c d e f g h i k) 3)
      (A B D E G H K)"
  ([elements n] (drop-every elements n 1))
  ([[head & tail] n i]
   (if (nil? head)
     '()
     (let [res (drop-every tail n (inc i))
           modulo (mod i n)]
       (if (> modulo 0)
         (conj res head)
         res)))))
