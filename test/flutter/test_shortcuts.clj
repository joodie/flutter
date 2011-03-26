(ns flutter.test-shortcuts
  (:use [flutter shortcuts no-field]
        flutter.html4.input-fields
        clojure.test))

(deftest shortcuts
  (let [field (-> no-field
                  wrap-basic-input-fields
                  wrap-shortcuts)]
    (is (= (field :input#my-id.my-class {:type :text} :my-name nil :my-value))
        [:input {:class "my-class"
                 :id "my-id"
                 :type :text
                 :name :my-name
                 :value :my-value}])
    (is (= (field :input {:type :text} :my-name nil :my-value))
        [:input {:type :text
                 :name :my-name
                 :value :my-value}])))

