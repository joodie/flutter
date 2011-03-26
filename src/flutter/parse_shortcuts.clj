(ns flutter.parse-shortcuts
  (:use [clojure.contrib.string :only [as-str]]))

;; code adapted from hiccup.core

(def ^{:doc "Regular expression that parses a CSS-style id and class from a tag name."}
  re-tag
  #"([^\s\.#]+)(?:#([^\s\.#]+))?(?:\.([^\s#]+))?")


(defn parse-shortcuts
  "Parse #id and .classes in a keyword. Returns [tag, id, classes]"
  [name]
  (let [[_ tag id class] (re-matches re-tag (as-str name))]
    [tag id (if class (.replace ^String class "." " "))]))
