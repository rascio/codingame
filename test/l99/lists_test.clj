(ns l99.lists-test
  (:require [clojure.test :refer :all]
            [l99.lists :refer :all]))

(deftest test-my-last
  (is (= 4 (my-last '(1 2 3 4))))
  (is (= 1 (my-last '(1))))
  (is (= nil (my-last ()))))

(deftest test-my-but-last
  (is (= '(3 4)))
  (my-but-last '(1 2 3 4))
  (is (= '(1 nil) (my-but-last '(1))))
  (is (= '(nil nil) (my-but-last ()))))

(deftest test-element-at
  (is (= 1 (element-at '(1 2 3 4) 0)))
  (is (= 2 (element-at '(1 2 3 4) 1)))
  (is (= 3 (element-at '(1 2 3 4) 2)))
  (is (= 4 (element-at '(1 2 3 4) 3)))
  (is (= nil (element-at () 0)))
  (is (= nil (element-at '(1) 1))))

(deftest test-length
  (is (= 0 (length ())))
  (is (= 1 (length '(1))))
  (is (= 2 (length '(1 2))))
  (is (= 3 (length '(1 2 3))))
  (is (= 4 (length '(1 2 3 4)))))

(deftest test-my-reverse
  (is (= () (my-reverse ())))
  (is (= '(1) (my-reverse '(1))))
  (is (= '(2 1) (my-reverse '(1 2))))
  (is (= '(3 2 1) (my-reverse '(1 2 3))))
  (is (= '(4 3 2 1) (my-reverse '(1 2 3 4)))))

(deftest test-palindrome?
  (is (= true (palindrome? ())))
  (is (= true (palindrome? '(1))))
  (is (= false (palindrome? '(1 2))))
  (is (= true (palindrome? '(1 2 1))))
  (is (= false (palindrome? '(1 2 3 4))))
  (is (= false (palindrome? '(1 2 2 4))))
  (is (= true (palindrome? '(1 2 2 1)))))

(deftest test-my-flatten
  (is (= () (my-flatten ())))
  (is (= '(1) (my-flatten '(1))))
  (is (= '(1 2) (my-flatten '(1 (2)))))
  (is (= '(1 2 3) (my-flatten '(1 (2 3)))))
  (is (= '(1 2 3 4) (my-flatten '(1 (2 (3 4))))))
  (is (= '(1 2 3 4 5) (my-flatten '(1 (2 (3 4) 5))))))

(deftest test-compress
  (is (= '(1) (compress '(1))))
  (is (= '(1 2) (compress '(1 2))))
  (is (= '(1 2 1) (compress '(1 2 1))))
  (is (= '(1 2 3) (compress '(1 1 2 3))))
  (is (= '(1 2 4) (compress '(1 2 2 4))))
  (is (= '(1 2 1 2 3) (compress '(1 2 2 1 1 1 1 1 1 1 2 2 2 2 3 3 3)))))
;  (is (= '() (compress ()))))

(deftest test-pack
  (is (= '((1)) (pack '(1))))
  (is (= '((1 1)) (pack '(1 1))))
  (is (= '((1) (2)) (pack '(1 2))))
  (is (= '((1) (2 2)) (pack '(1 2 2))))
  (is (= '((1 1) (2) (3)) (pack '(1 1 2 3))))
  (is (= '((1 1) (2 2 2) (3)) (pack '(1 1 2 2 2 3)))))
;  (is (= '() (pack ()))))

(deftest test-encode
  (is (= '((1 1)) (encode '(1))))
  (is (= '((2 1)) (encode '(1 1))))
  (is (= '((1 1) (1 2)) (encode '(1 2))))
  (is (= '((1 1) (2 2)) (encode '(1 2 2))))
  (is (= '((2 1) (1 2) (1 3)) (encode '(1 1 2 3))))
  (is (= '((2 1) (3 2) (1 3)) (encode '(1 1 2 2 2 3)))))
;  (is (= '() (pack ()))))

(deftest test-encode-modified
  (is (= '(1) (encode-modified '(1))))
  (is (= '((2 1)) (encode-modified '(1 1))))
  (is (= '(1 2) (encode-modified '(1 2))))
  (is (= '(1 (2 2)) (encode-modified '(1 2 2))))
  (is (= '((2 1) 2 3) (encode-modified '(1 1 2 3))))
  (is (= '((2 1) (3 2) 3) (encode-modified '(1 1 2 2 2 3)))))
;  (is (= '() (pack ()))))

(deftest test-decode
  (is (= '(1) (decode '(1))))
  (is (= '(1 1) (decode '((2 1)))))
  (is (= '(1 2) (decode '(1 2))))
  (is (= '(1 2 2 2) (decode '(1 (3 2)))))
  (is (= '(1 1 2 3) (decode '((2 1) 2 3))))
  (is (= '(1 1 2 2 2 3 3 3 3) (decode '((2 1) (3 2) (4 3))))))
;  (is (= '() (pack ()))))

(deftest test-dupli
  (is (= '(1 1) (dupli '(1))))
  (is (= '(2 2 1 1) (dupli '(2 1))))
  (is (= '(1 1 3 3 2 2) (dupli '(1 3 2)))))

(deftest test-repli
  (is (= '(1 1) (repli '(1) 2)))
  (is (= '(2 2 2 1 1 1) (repli '(2 1) 3)))
  (is (= '(1 1 1 1 3 3 3 3 2 2 2 2) (repli '(1 3 2) 4))))
;  (is (= '() (pack ()))))

(deftest test-drop-every
  (is (= '(1) (drop-every '(1 2) 2)))
  (is (= '(1 3 5 7 9) (drop-every '(1 2 3 4 5 6 7 8 9) 2)))
  (is (= '(1 2 4 5) (drop-every '(1 2 3 4 5) 3))))
;  (is (= '() (pack ()))))
