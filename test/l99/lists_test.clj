(ns l99.lists-test
  (:require [clojure.test :refer :all]
            [l99.lists :refer :all]))

(deftest test-my-last
  (is (= 'd (my-last '(a b c d))))
  (is (= 'a (my-last '(a))))
  (is (= nil (my-last nil))))
