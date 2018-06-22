(ns Solution
  (:gen-class)
  (:require [clojure.string :as str]))
(defn log [& args]
  (binding [*out* *err*]
    (apply println "DEBUG:" args))
  args)

(defn -parse-row [row]
    (str/split row #"|"))

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
          {:x column :y row :state :init}
          res)))
    {:x -1 :y -1}
    map))

(defn directions [direction x y]
  (case direction
    :NORTH [x (dec y)]
    :EAST  [(inc x) y]
    :SOUTH [x (inc y)]
    :WEST  [(dec x) y]
    nil))

(defn move [{x :x y :y :as state} dir map]
  (let [[next-x next-y] (directions dir x y)]
    (case (get-in map [next-x next-y])
      " " (assoc state :dir dir :x next-x :y next-y)
      nil)))

(defn find-direction [state m & dir]
  (->> dir
    (map #(move state % m))
    (map #(do (log %) %))
    (filter some?)
    first))

(defmulti -solve (fn [state & args] (:state state)))
(defmethod -solve :init [state map]
  (let [next (find-direction state map :SOUTH :EAST :NORTH :WEST)]
    (println (:dir next))))
(defmethod -solve :default [a b]
  (println "default" a b))

(defn -main [& args]
    (let [map (-read-map)]
      (-> (-find-start map)
        (-solve map))))

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
