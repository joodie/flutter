(ns flutter.html4
  (:use flutter.no-field
        flutter.html4.input-fields
        flutter.html4.select
        flutter.html4.text-area))

;;;;
;;;; convenience functions, providing all the standard HTML fields
;;;;

(defn wrap-html4-fields
  "Provides all the HTML 4 form fields in a sane way. Includes

wrap-basic-input-fields, wrap-html4-input-fields,
wrap-radio-and-checkbox, wrap-select and wrap-text-area"
  [f]
  (-> f
      wrap-basic-input-fields
      wrap-html4-input-fields
      wrap-radio-and-checkbox
      wrap-select
      wrap-text-area))

(def html4-fields
  ^{:doc "wrap-html4-fields, pre-wrapped in `no-field'"}
  (wrap-html4-fields no-field))
