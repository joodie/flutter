(ns flutter.core)

(defn field
  "throw an exception when called. can be used as the end point of
the wrapper functions"
  [type attrs name opts value]
  (throw (Exception. (str "Cannot process field with spec "
                          [type attrs name opts value]))))

