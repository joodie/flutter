(ns flutter.test-shortcuts
  (:use [flutter shortcuts no-field]
        flutter.html4
        flutter.params
        clojure.test))

(deftest shortcuts
  (let [field (-> html4-fields
                  wrap-shortcuts)]
    (is (= (field :text#my-id.my-class {:onclick "click"} :my-name nil :my-value)
           [:input {:class "my-class"
                 :id "my-id"
                 :type :text
                 :onclick "click"
                 :name :my-name
                 :value :my-value}]))
    (is (= (field :input {:type :text} :my-name nil :my-value)
           [:input {:type :text
                 :name :my-name
                 :value :my-value}]))

    (is (= (field :text-area#my-id nil :my-name nil "content")
           [:textarea {:id "my-id" :name :my-name} "content"]))

    (is (= (field :submit.special :my-name :val)
           [:input {:type :submit :name :my-name
                    :class "special" :value :val}]))))


(deftest with-params
  (let [field (-> html4-fields
                  wrap-shortcuts
                  (wrap-params {:my-name :my-val
                                :other-name 2}))]
    (is (= (field :text :my-name)
           [:input {:type :text :name :my-name :value :my-val}]))
    (is (= (field :text.cls {:onclick "click"} :other-name)
           [:input {:type :text :class "cls" :onclick "click"
                    :name :other-name :value 2}]))
    (is (= (field :radio :other-name 2)
           [:input {:type :radio :name :other-name
                    :value 2 :checked :checked}]))))

