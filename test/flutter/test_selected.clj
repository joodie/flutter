(ns flutter.test-selected
  (:use flutter.selected
        clojure.test))

(deftest selected
  (is (selected? :val :val))
  (is (selected? :val [:val]))
  (is (selected? :val [:v1 :v2 :val]))
  (is (selected? :val #{:v1 :v2 :val}))
  (is (not (selected? :val :v2)))
  (is (not (selected? :val [])))
  (is (not (selected? :val [:v1 :v2])))
  (is (not (selected? :val #{:v2 :v1}))))

