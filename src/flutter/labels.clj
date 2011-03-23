(ns flutter.labels)

(defn wrap-labels
  "given a labels function, wrap a [:label] tag around each field
containing the content returned by (labels name), followed by the
field. Doesn't wrap fields for which (labels name) is nil."
  [f labels]
  (fn [type attrs name opts value]
    (if-let [content (labels name)]
      [:label content (f type attrs name opts value)]
      (f type attrs name opts value))))
