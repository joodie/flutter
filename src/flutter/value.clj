(ns flutter.value
  (:use [hiccup.core :only [escape-html]]))

(defn wrap-value
  "Adds flutter type :value, which just returns the value passed in"
  [f]
  (fn [type attrs name opts value]
    (if (= type :value)
      value
      (f type attrs name opts value))))

(defn wrap-value-as-html
  "Adds flutter types :html and :h, returning the given value, html-encoded"
  [f]
  (fn [type attrs name opts value]
    (if (or (= type :h)
            (= type :html))
      (escape-html value)
      (f type attrs name opts value))))
