(ns flutter.test-html4
  (:use clojure.test
        flutter.html4))

(deftest selected
  (is (selected? :val :val))
  (is (selected? :val [:val]))
  (is (selected? :val [:v1 :v2 :val]))
  (is (selected? :val #{:v1 :v2 :val}))
  (is (not (selected? :val :v2)))
  (is (not (selected? :val [])))
  (is (not (selected? :val [:v1 :v2])))
  (is (not (selected? :val #{:v2 :v1}))))

(deftest basic
  (let [field (wrap-basic-input-fields no-field)]

    (is (= (field :input {:type :text} :my-name nil :my-value)
           [:input {:type :text :name :my-name :value :my-value}]))

    (is (thrown? Exception (field :no-such-type {} :name :value)))))

(deftest input-types
  (let [field (-> no-field
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

(deftest select
  (let [field (-> no-field
                  wrap-select)]
    (is (= (field :select
                  {}
                  :my-name
                  [:v1 :v2 :v3]
                  :v2)
           [:select {:name :my-name}
            [:option {:value :v1 :label :v1}]
            [:option {:value :v2 :label :v2 :selected :selected}]
            [:option {:value :v3 :label :v3}]]))
    (is (= (field :select
                  {}
                  :my-name
                  [:v0 [:optgroup :label [:v1 :v2 :v3]]]
                  :v2)
           [:select {:name :my-name}
            [:option {:value :v0 :label :v0}]
            [:optgroup {:label :label}
             [:option {:value :v1 :label :v1}]
             [:option {:value :v2 :label :v2 :selected :selected}]
             [:option {:value :v3 :label :v3}]]]))
    
    (is (= (field :select {:multiple :multiple}
                  :my-name
                  [:v1
                   [:option :v2]
                   [:option {:class :my-class} :v3 :my-label]
                   [:optgroup :group-label
                    [:v4
                     [:option :v5]
                     [:option {:class :my-class} :v6 :my-label]]]]
                  [:v2 :v6])
           [:select {:name :my-name :multiple :multiple}
            [:option {:value :v1 :label :v1}]
            [:option {:value :v2 :label :v2 :selected :selected}]
            [:option {:value :v3 :label :my-label :class :my-class}]
            [:optgroup {:label :group-label}
             [:option {:value :v4 :label :v4}]
             [:option {:value :v5 :label :v5}]
             [:option {:value :v6 :label :my-label :class :my-class
                       :selected :selected}]]]))))
