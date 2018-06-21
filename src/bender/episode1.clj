(ns Solution
  (:gen-class)
  (:require [clojure.string :as str]))

(defn -parse-row [row]
    (str/split row #"|"))

(defn -read-map []
  (let [L (read) C (read) _ (read-line)]
    (loop [i L map []]
      (if (> i 0)
        (let [row (read-line)]
          (binding [*out* *err*]
              (println "DEBUG:" row))
          (recur (dec i) (conj map (-parse-row row))))
        map))))

(def START "@")
(defn -find-start [map]
  (reduce
    (fn [res line]
      (let [{x :x y :y} res row (inc x) column (.indexOf line START)]
        (if (= -1 y)
          {:x row :y column :state :init}
          res)))
    {:x -1 :y -1}
    map))

(defn -solve [{x :x y :y state :state} map])


(defn -main [& args]
    (let [map (-read-map)]
      (-> (-find-start map)
        (-solve map)
        (println "answer"))))

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
