(ns fp_oo.chapter4)

(def make
  (fn [ctor & args]
    (apply ctor args))
  )

(def send-to
  (fn [obj msg & args]
    (apply (msg (:__methods__ obj)) obj args)
    )
  )

; Exercise 1

(def Point
  (fn [x y]
    {:x x
     :y y

     :__class_symbol__ 'Point
     :__methods__ {
       :class :__class_symbol__
       :x
         (fn [this]
           (:x this))
       :y
         (fn [this]
           (:y this))
       :shift
         (fn [this xinc yinc]
           (make Point (+ (send-to this :x) xinc)
                       (+ (send-to this :y) yinc)))
       :add
         (fn [this that]
           (send-to this :shift (send-to that :x) (send-to that :y)))
       }}))

(def a-point (make Point 1 2))
(println (send-to a-point :shift -2 -3))
(println (send-to a-point :x))
(println (send-to a-point :y))
(def another-point (make Point 3 4))
(println (send-to a-point :add another-point))

