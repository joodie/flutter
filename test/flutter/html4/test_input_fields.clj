(ns flutter.html4.test-input-fields
  (:use flutter.core
        flutter.html4.input-fields
        clojure.test))

(deftest basic
  (let [field (-> field
                  wrap-basic-input-fields)]
    (is (= (field :input {:type :text} :my-name nil :my-value)
           [:input {:type :text :name :my-name :value :my-value}]))

    (is (thrown? Exception (field :no-such-type {} :name :value)))))

(deftest input-types
  (let [field (-> field
                  wrap-basic-input-fields
                  wrap-html4-input-fields
                  wrap-radio-and-checkbox)]
    (is (= (field :radio {} :my-name :val :val)
           [:input {:type :radio :name :my-name
                    :value :val :checked :checked}]))
    (is (= (field :checkbox {} :my-name :val [:val1 :val2])
           [:input {:type :checkbox :name :my-name
                    :value :val}]))
    (is (= (field :checkbox {} :my-name :val [:val1 :val2 :val])
           [:input {:type :checkbox :name :my-name
                    :value :val :checked :checked}]))))
