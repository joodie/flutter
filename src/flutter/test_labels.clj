(ns flutter.test-labels
  (:use clojure.test
        flutter.html4
        flutter.labels))

(deftest labels
  (let [field (-> no-field
                  (wrap-labels {:my-name "Label"})
                  wrap-basic-input-fields)]
    (is (= (field :input {:type :text} :my-name nil "val")
           [:label "Label" [:input {:type :text :name :my-name :value "val"}]]))
    (is (= (field :input {:type :text} :other-name nil "val")
           [:input {:type :text :name :other-name :value "val"}]))))

