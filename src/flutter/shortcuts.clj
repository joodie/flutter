(ns flutter.shortcuts
  (:use flutter.parse-shortcuts))

(defn wrap-shortcuts
  "parse css-style #id and .class from the field type, like hiccup."
  [f]
  (fn [type attrs & args]
    (let [[tag id class] (parse-shortcuts type)
          new-attr {:id id :class class}]
      (if attrs
        (apply f tag (merge new-attr attrs) args)
        (apply f tag new-attr args)))))
