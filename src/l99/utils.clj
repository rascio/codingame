(ns l99.utils)

(defn when-match
  [predicate]
  (fn [v map]
    (if (predicate v)
      (map v)
      v)))
