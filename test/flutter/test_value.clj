(ns flutter.test-value
  (:use flutter.value
        flutter.core
        clojure.test))

(deftest value
  (let [f (wrap-value field)]
    (is (= "foo<bar>" (f :value nil nil nil "foo<bar>")))))


(deftest html
  (let [f (wrap-value-as-html field)]
    (is (= "foo&lt;bar&gt;" (f :h nil nil nil "foo<bar>")))))

