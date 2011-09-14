(ns flutter.html4.select
  (:use flutter.selected))

;;;;
;;;; code for &lt;select&gt; elements and its descendents
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
