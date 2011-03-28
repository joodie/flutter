(ns flutter.test-html4
  (:use flutter.html4
        clojure.test))

(deftest html4
  (let [field html4-fields]
    (is (= (field :text-area nil :my-name nil "<content>")
           [:textarea {:name :my-name} "&lt;content&gt;"]))
    (is (thrown? Exception (field :no-such-type nil :name nil nil)))))


