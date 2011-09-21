(ns flutter.html4.test-select
  (:use flutter.html4.select
        flutter.core
        clojure.test))

(deftest select
  (let [field (-> field
                  wrap-select)]
    (is (= (field :select
                  {}
                  :my-name
                  [:v1 :v2 :v3]
                  :v2)
           [:select {:name :my-name}
            [:option {:value :v1} :v1]
            [:option {:value :v2 :selected :selected} :v2]
            [:option {:value :v3} :v3]]))
    (is (= (field :select
                  {}
                  :my-name
                  [:v0 [:optgroup :label [:v1 :v2 :v3]]]
                  :v2)
           [:select {:name :my-name}
            [:option {:value :v0} :v0]
            [:optgroup {:label :label}
             [:option {:value :v1} :v1]
             [:option {:value :v2 :selected :selected} :v2]
             [:option {:value :v3 } :v3]]]))
    
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
            [:option {:value :v1} :v1]
            [:option {:value :v2 :selected :selected} :v2]
            [:option {:value :v3 :class :my-class} :my-label]
            [:optgroup {:label :group-label}
             [:option {:value :v4} :v4]
             [:option {:value :v5} :v5]
             [:option {:value :v6 :class :my-class
                       :selected :selected} :my-label]]]))))