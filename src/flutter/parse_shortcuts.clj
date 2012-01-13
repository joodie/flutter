(ns flutter.parse-shortcuts)

;; code adapted from hiccup.core

(def ^{:doc "Regular expression that parses a CSS-style id and class from a tag name."}
  re-tag
  #"([^\s\.#]+)(?:#([^\s\.#]+))?(?:\.([^\s#]+))?")


(defn parse-shortcuts
  "Parse #id and .classes in a keyword. Returns [tag, id, classes]"
  [tagname]
  (let [[_ tag id class] (re-matches re-tag (name tagname))]
    [(keyword tag) id (if class (.replace ^String class "." " "))]))
