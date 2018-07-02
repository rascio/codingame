(ns Solution
  (:gen-class)
  (:require [clojure.string :as str]))
(defn log [& args]
  (binding [*out* *err*]
    (apply println "DEBUG:" args))
  (last args))

(defn -parse-row [row]
  (into [] (map str row)))

(defn is-free? [tiles x y {breaker :breaker}]
  ; (log "is-free" x y state)
  (let [tile (get-in tiles [y x])]
    ; (log x y tile
      (and some?
        (not= tile "#")
        (or breaker
          (not= tile "X")))))

(defn -read-map []
  (let [L (read) C (read) _ (read-line)]
    (loop [i L map []]
      (if (> i 0)
        (let [row (read-line)]
          ; (log row)
          (recur (dec i) (conj map (-parse-row row))))
        map))))

(def START "@")
(defn -find-start [tiles]
  (reduce
    (fn [{x :x y :y :as res} line]
      (let [column (inc y) row (.indexOf line START)]
        ; (log "x" x "y" y row line)
        (if (not= -1 row)
          ; (log "found"
            (reduced {:x row :y column})
          {:x x :y (inc y)})))
    {:x -1 :y -1}
    tiles))

(defn move [x y direction]
  ; (log "move" x y direction)
  (case direction
    :NORTH {:x x        :y (dec y)}
    :EAST  {:x (inc x)  :y y}
    :SOUTH {:x x        :y (inc y)}
    :WEST  {:x (dec x)  :y y}
    nil))

(defn find-next [tiles {position :position directions :directions
                        inverted :inverted :as state}]
  ; (log "find-next" state)
  (->> (if inverted (reverse directions) directions)
    (map #(move (:x position) (:y position) %))
    ; (map #(log %))
    (filter some?)
    (filter (fn [{y :y x :x}] (is-free? tiles x y state)))
    first))

(defn solve [tiles {directions :directions breaker :breaker
                    inverted :inverted :as state}]
  (let [next (find-next tiles state)
        tile (get-in tiles [(:y next) (:x next)])]
    ; (letfn [(change-direction (fn [d] (assoc (solve tiles
    ;                                            (assoc state :directions '(d)))
    ;                                     :directions directions)))]
      (log "next:" next)
      (assoc
        (case tile
          "N" (assoc (solve tiles (assoc state :directions '(:NORTH)))
                :directions directions)
          "S" (assoc (solve tiles (assoc state :directions '(:SOUTH)))
                :directions directions)
          "W" (assoc (solve tiles (assoc state :directions '(:WEST)))
                :directions directions)
          "E" (assoc (solve tiles (assoc state :directions '(:EAST)))
                :directions directions)
          "B" (assoc state :breaker (not breaker))
          "I" (assoc state :inverted (not inverted))
          " " state
          "$" :win
          (log "EH?!" tile))
        :position
        next)))

(defn -main [& args]
    (let [map (-read-map)]
      (->> {:position (-find-start map)
                  :directions '(:SOUTH :EAST :NORTH :WEST)}
        (solve map)
        (solve map))))

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
