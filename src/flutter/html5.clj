(ns flutter.html5)

(defn wrap-html5-input-fields
  "provide html5 input types:

:email :url :range :date :month :week :time :datetime
:datetime-local :search and :color."
  [f]
  (fn [type attrs name opts value]
    (if (#{:email :url :range :date :month
           :week :time :datetime :datetime-local
           :search :color} type)
      (f :input (assoc attrs :type type) name opts value)
      (f type attrs name opts value))))
