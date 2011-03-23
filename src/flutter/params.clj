(ns flutter.params)

(defn wrap-params
  "given a map of name => value pairs, provide default values for each field.
explicitly passed values override the values in the params map"
  [f params]
  (fn
    ([type attrs name opts]
       (f type attrs name opts (params name)))
    ([type attrs name opts value]
       (f type attrs name opts value))))
