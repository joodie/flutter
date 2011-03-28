(ns flutter.shortcuts
  (:use flutter.parse-shortcuts))

(defn wrap-id-and-class-shortcuts
  "parse css-style #id and .class from the field type, like hiccup."
  [f]
  (fn [type attrs & args]
    (let [[tag id class] (parse-shortcuts type)
          attr-with-id (if id (assoc attrs :id id) attrs)
          attr-with-classes (if class (assoc attr-with-id :class class) attr-with-id)]
      (apply f tag attr-with-classes args))))

(defn wrap-optionals
  "make attrs and opts optional

 (f type name value) -> (f type nil name nil value)

 if attrs is a map:
 (f type attrs name value) -> (f type attr name nil value)
 else:
 (f type name opts value) -> (f type nil name opts value)"
  [f]
  (fn
    ([type attr name opts value]
       (f type attr name opts value))
    ([type name value]
       (f type nil name nil value))
    ([type a2 a3 value]
       (if (map? a2)
         (f type a2 a3 nil value)
         (f type nil a2 a3 value)))))

(defn wrap-shortcuts
  "make attrs and opts optional and allows hiccup-style #id and .class
shortcuts for type.
See wrap-optionals and wrap-id-and-class-shortcuts"
  [f]
  (-> f
      wrap-id-and-class-shortcuts
      wrap-optionals))
