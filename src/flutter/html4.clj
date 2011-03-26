(ns flutter.html4
  (:use [hiccup.core :only [escape-html]]))

(defn no-field
  "throw an exception when called. can be used as the end point of
the following wrappers."
  [& args]
  (throw (Exception. (str "Cannot process field with spec " args))))

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
;;;; text-area
;;;;

(defn wrap-text-area
  "provides the :text-area element type, value is placed as the content
  and opts is ignored"
  [f]
  (fn [type attrs name opts value]
    (if (= type :text-area)
      [:textarea (assoc attrs :name name) (escape-html value)]
      (f type attrs name opts value))))

;;;;
;;;; elements that can be used to select one or more values for a name.
;;;; 

(defn selected?
  "if values is sequential or a set, check if values contains value
otherwise, check if value is equal to values"
  [value values]
  (if (sequential? values)
    (contains? (set values) value)
    (if (set? values)
      (contains? values value)
      (= values value))))

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

;;;;
;;;; code for <select> elements and its descendents
;;;;

(defn- make-option*
  ([values attrs my-value label]
     [:option (apply assoc attrs
                :value my-value
                :label label
                :value my-value
                (if (selected? my-value values) [:selected :selected]))])
  ([values my-value label]
     (make-option* values {} my-value label))
  ([values my-value]
     (make-option* values {} my-value my-value)))

(declare make-option)

(defn- make-optgroup
  ([value attrs label options]
     (into [:optgroup (assoc attrs :label label)]
           (map #(make-option value %) options)))
  ([value label options]
     (make-optgroup value {} label options)))

;; option examples:
;; [:option attrs value label ]
;; [:option value label ]
;; [:option value ]
;; :value
;; [:optgroup attrs label options ]
;; [:optgroup label options ]

(defn- make-option
  [value spec]
  (if (sequential? spec)
    (let [type (first spec)]
      (case type
            :optgroup (apply make-optgroup value (rest spec))
            :option   (apply make-option*  value (rest spec))))
    (make-option* value spec)))

(defn wrap-select
  "procide :select field. opts is a sequence of option specs:

option spec can be:

  [:option attrs value label]
  [:option value label]
  [:option value]
  value
  [:optgroup attrs label option-specs]
  [:optgroup label option-specs]"
  [f]
  (fn [type attrs name opts value]
    (if (= :select type)
      (into [:select (assoc attrs :name name)]
            (map #(make-option value %) opts))
      (f type attrs name opts value))))

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
