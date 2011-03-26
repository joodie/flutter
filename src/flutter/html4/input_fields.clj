(ns flutter.html4.input-fields
  (:use flutter.selected))

;;;;
;;;; minimal input fields that provide a given name => value pair
;;;;

(defn wrap-basic-input-fields
  "accept type :input and translate straight to a hiccup [:input] element,
adding name and value in attributes (which probably should include
  a :type). ignores opts."
  [f]
  (fn [type attrs name _ value]
    (if (= type :input)
      [:input (assoc attrs :name name :value value)])))

(defn wrap-html4-input-fields
  "provides the standard HTML 4 input types: translates types
 :text :password :checkbox :radio :submit :reset :file :hidden
 :image and :button to type :input.

If you want the full set of HTML 4 form fields, use `wrap-html4-fields'
instead."
  [f]
  (fn [type attrs name opts value]
    (if (#{:text :password :checkbox :radio :submit :reset
           :file :hidden :image :button} type)
      (f :input (assoc attrs :type type) name nil value)
      (f type attrs name opts value))))

;;;;
;;;; elements that can be used to select one or more values for a name.
;;;; 

(defn wrap-radio-and-checkbox
  "provides semantics for radio and check boxes: opts
 is the value given for this particular input, sets the
 :checked attribute if that value is (in) the current value(s)"
  [f]
  (fn [type attrs name opts values]
    (if (#{:radio :checkbox} type)
      (f type (if (selected? opts values)
                (assoc attrs :checked :checked)
                attrs) name nil opts)
      (f type attrs name opts values))))
