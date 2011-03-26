(ns flutter.html4.text-area
  (:use [hiccup.core :only [escape-html]]))

(defn wrap-text-area
  "provides the :text-area element type, value is placed as the content
  and opts is ignored"
  [f]
  (fn [type attrs name opts value]
    (if (= type :text-area)
      [:textarea (assoc attrs :name name) (escape-html value)]
      (f type attrs name opts value))))
