(ns flutter.params)

(defn wrap-params
  "given a map of name => value pairs, provide values for each field.
like wrap-optionals, attrs and opts are optional, but attrs must be a map.

when given the full five parameters, override the values in the params map."
  [f params]
  (fn
    ([type attrs name opts]
       (f type attrs name opts (params name)))
    ([type attrs name opts value]
       (f type attrs name opts value))
    ([type name]
       (f type nil name nil (params name)))
    ([type a2 a3]
       (if (map? a2)
         (f type a2 a3 nil (params name))
         (f type nil a2 a3 (params name))))))
