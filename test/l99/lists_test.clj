(ns l99.lists-test
  (:require [clojure.test :refer :all]
            [l99.lists :refer :all]))

(defn -seq-of [& s] s)

(deftest test-my-last
  (is (= 4 (my-last '(1 2 3 4))))
  (is (= 1 (my-last '(1))))
  (is (= nil (my-last '())))

(deftest test-my-but-last
  (is (= '(3 4)))
  (my-but-last '(1 2 3 4))
  (is (= '(1 nil) (my-but-last '(1))))
  (is (= '(nil nil) (my-but-last '()))))

(deftest test-element-at
  (is (= 1 (element-at '(1 2 3 4) 0)))
  (is (= 2 (element-at '(1 2 3 4) 1)))
  (is (= 3 (element-at '(1 2 3 4) 2)))
  (is (= 4 (element-at '(1 2 3 4) 3)))
  (is (= nil (element-at '() 0)))
  (is (= nil (element-at '(1) 1))))

(deftest test-length
  (is (= 0 (length '())))
  (is (= 1 (length '(1))))
  (is (= 2 (length '(1 2))))
  (is (= 3 (length '(1 2 3))))
  (is (= 4 (length '(1 2 3 4)))))

(deftest test-reverse
  (is (= '() (reverse '())))
  (is (= '(1) (reverse '(1))))
  (is (= '(2 1) (reverse '(1 2))))
  (is (= '(3 2 1) (reverse '(1 2 3))))
  (is (= '(4 3 2 1) (reverse '(1 2 3 4)))))

(deftest test-my-flatten
  (is (= '() (my-flatten '())))
  (is (= '(1) (my-flatten '(1))))
  (is (= '(1 2) (my-flatten '(1 (2)))))
  (is (= '(1 2 3) (my-flatten '(1 (2 3)))))
  (is (= '(1 2 3 4) (my-flatten '(1 (2 (3 4))))))
  (is (= '(1 2 3 4 5) (my-flatten '(1 (2 (3 4) 5))))))
