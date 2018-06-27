(ns Solution
  (:gen-class)
  (:require [clojure.string :as str]))
(defn log [& args]
  (binding [*out* *err*]
    (apply println "DEBUG:" args))
  args)

(defn -parse-row [row]
    (str/split row #"|"))

(defn is-free [map state tile state]
  (and (not= tile "#")
       (or (= state :breaker)
           (not= tile "X"))))

(defn -read-map []
  (let [L (read) C (read) _ (read-line)]
    (loop [i L map []]
      (if (> i 0)
        (let [row (read-line)]
          (log row)
          (recur (dec i) (conj map (-parse-row row))))
        map))))

(def START "@")
(defn -find-start [map]
  (reduce
    (fn [{x :x y :y :as res} line]
      (let [row (inc y) column (.indexOf line START)]
        (if (= -1 x)
          {:x column :y row}
          res)))
    {:x -1 :y -1}
    map))

(defn move [x y direction]
  (case direction
    :NORTH [x (dec y)]
    :EAST  [(inc x) y]
    :SOUTH [x (inc y)]
    :WEST  [(dec x) y]
    nil))

(defn find-next [map & state]
  (->> (:directions state)
    (map #(move map %))
    (map #(do (log %) %))
    (filter some?)
    first))

(defn solve [map & state]
  (loop [next (find-next state map (:directions state))]))


(defn -main [& args]
    (let [map (-read-map)]
      (solve map
          :position (-find-start map)
          :state :init
          :directions [:SOUTH :EAST :NORTH :WEST])))

(def input "10 10
##########
#        #
#  S   W #
#        #
#  $     #
#        #
#@       #
#        #
#E     N #
##########")
(defn test []
  (binding [*in* (new clojure.lang.LineNumberingPushbackReader
                   (clojure.java.io/reader
                     (clojure.java.io/input-stream
                       (.getBytes input))))]
    (-main)))
