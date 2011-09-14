(ns flutter.selected
  "tools for handling multi-valued parameters")

(defn selected?
  "if values is sequential or a set, check if values contains value
otherwise, check if value is equal to values"
  [value values]
  (if (sequential? values)
    (contains? (set values) value)
    (if (set? values)
      (contains? values value)
      (= values value))))
