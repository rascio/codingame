(ns Solution
  (:gen-class)
  (:require [clojure.string :as str]))
(defn log [& args]
  (binding [*out* *err*]
    (apply println "DEBUG:" args))
  (last args))

(defn -parse-row [row]
    (str/split row #"|"))

(defn is-free [tiles x y & {state :state}]
  (let [tile (get-in tiles [x y])]
    (and (not= tile "#")
         (or (= state :breaker)
             (not= tile "X")))))

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
  (log "move" x y direction)
  (case direction
    :NORTH [x (dec y)]
    :EAST  [(inc x) y]
    :SOUTH [x (inc y)]
    :WEST  [(dec x) y]
    nil))

(defn find-next [tiles & {position :position directions :directions :as state}]
  (log "find-next" state)
  (->> (log directions)
    (map #(move (:x position) (:y position) %))
    (map #(log %))
    (filter some?)
    (filter #(apply is-free tiles % state))
    first))

(defn solve [map & state]
  (let [next (apply find-next map state)]
    (log "next:" next)))

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
