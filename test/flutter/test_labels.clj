(ns flutter.test-labels
  (:use clojure.test
        flutter.core
        flutter.html4.input-fields
        flutter.labels))

(deftest labels
  (let [field (-> field
                  wrap-basic-input-fields
                  (wrap-labels {:my-name "Label"}))]
    (is (= (field :input {:type :text} :my-name nil "val")
           [:label "Label" [:input {:type :text :name :my-name :value "val"}]]))
    (is (= (field :input {:type :text} :other-name nil "val")
           [:input {:type :text :name :other-name :value "val"}]))))

