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

(defn -solve [map])
    

(defn -main [& args]
    ; (binding [*out* *err*]
    ;   (println "Debug messages..."))
    (let [map (-read-map)]
    ; Write answer to stdout
      (println "answer")))
