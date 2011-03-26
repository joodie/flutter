(ns flutter.no-field)

(defn no-field
  "throw an exception when called. can be used as the end point of
the following wrappers."
  [& args]
  (throw (Exception. (str "Cannot process field with spec " args))))

