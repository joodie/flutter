# Flutter: Hiccup based HTML form fields for clojure.

Read the MOTIVATION file for why.

## API CONCEPTS

There are two important concepts in the design of this library:
field functions and wrappers.

### FIELD FUNCTION

A field function is a function that takes 5 arguments, including a
name and a value and returns a snippet of html in [hiccup](https://github.com/weavejester/hiccup) format.

    (field type attributes name opts value) -> snippet

  * `type' is the type of field. If you're using
  `flutter.html4/html4-fields', the recognized types are:

     :input, :text, :radio, :checkbox, :text-area, :select
     :hidden, :submit, :button and :image 

  * `attributes' is a map of html element attributes, that are
generally passed on to the form element that implements the field
type. most field functions modify the attributes based on the other
arguments too. For instance, field type :text will result in an
element-name of :input with additional attributes {:type :text :name
name :value value}

  * `name' is the name of the input element for this field.

  * `opts' is type-specific. for :radio and :checkbox types, this is
the value of the value attribute.

  * `value' is the value (or set, or sequence of values) associated
with `name'. for :radio and :checkbox, this means that if `opts' is in
`value', the input element is checked.

  * For a single element, the returned hiccup snippet looks like

    [element-name attributes content*]

  but fields may return something more complex.

### WRAPPER FUNCTIONS

Like the [ring API](https://github.com/mmcgrana/ring/blob/master/SPEC), almost all functionality in flutter is implemented
in wrapper functions. In fact, the flutter.core/field function just
throws an exception when called to indicate that the requested field
is not implemented.

Wrapper functions can add field types, implement a more logical
interface to some existing types, change the user-visible API etc.

Wrapper functions can make the user-visible API a bit nicer to
look at:

    (ns flutter.test-shortcuts
      (:use [flutter shortcuts html4]))

    (let [field (-> html4-fields
                    wrap-shortcuts)]

       ;; generate a text input field with name "my-name", value
       ;; and id "my-id"
       (field :text#my-id "my-name" "some text value"))

See flutter.html4/html4-fields and flutter.shortcuts/wrap-shortcuts.

There's also a wrapper that can be used to provide values for field
names:

    (ns flutter.test-shortcuts
      (:use [flutter shortcuts html4 params]))

    (let [field (-> html4-fields
                    wrap-shortcuts
                    (wrap-params {:myname "some value")]

       ;; generate a text input field with name myname,
       ;; class "my-class" and value from params
       (field :text.my-class :myname))

If you create your wrapped field function in the route/controller code,
you can then pass it on to the view to provide values for the generated
form. See flutter.params/wrap-params.

## DISCLAIMER

This is a work in progress. The API may change, read the CHANGES file
when upgrading. This code almost certainly contains bugs. If you find
one, check the latest release or the master branch on 
http://github.com/joodie/flutter and if it's not been fixed, please
report any issues - preferably using a pull request on github.

## COPYRIGHT AND LICENSE

(c) 2011 Joost Diepenmaat, Zeekat Softwareontwikkeling

http://joost.zeekat.nl/  - joost@zeekat.nl

Distributed under the Eclipse Public License, the same as Clojure.


