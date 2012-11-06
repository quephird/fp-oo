(ns fp_oo.chapter5)

; Exercise 1

(def apply-message-to
  (fn [clazz instance msg args]
    (let [method (msg (:__instance_methods__ clazz))]
      (apply method instance args))
    )
  )

(def make
  (fn [clazz & args]
    (let [seeded {:__class_symbol__ (:__own_symbol__ clazz)}]
      (apply-message-to clazz seeded :add-instance-values args)))
  )

(def send-to
  (fn [instance msg & args]
    (let [clazz (eval (:__class_symbol__ instance))]
      (apply-message-to clazz instance msg args)))
 )

(def Point
  {:__own_symbol__ 'Point
   :__instance_methods__
    {:add-instance-values
       (fn [this x y]
         (assoc this :x x :y y))
     :class
       (fn [this]
         (eval (:__class_symbol__ this)))
     :class-name :__class_symbol__
     :shift
       (fn [this xinc yinc]
         (make Point (+ (:x this) xinc)
                     (+ (:y this) yinc)))
      }
    }
  )

(def a-point (make Point 1 2))
(println a-point)
(println (send-to a-point :shift -2 -3))

; Exercise 2

(println (send-to a-point :class-name))
(println (send-to a-point :class))

; Exercise 3

(def a-point-created-before-redefinition (make Point 1 2))
(def Point
  {:__own_symbol__ 'Point
   :__instance_methods__
    {:add-instance-values
       (fn [this x y]
         (assoc this :x x :y y))
     :class
       (fn [this]
         (eval (:__class_symbol__ this)))
     :class-name :__class_symbol__
     :origin
       (fn [this]
         (make Point 0 0))
     :shift
       (fn [this xinc yinc]
         (make Point (+ (:x this) xinc)
                     (+ (:y this) yinc)))
      }
    }
  )

(def a-point-created-after-redefinition (make Point 3 4))
(println (send-to a-point-created-after-redefinition :origin))
(println (send-to a-point-created-before-redefinition :origin))

; Both calls above succeed and return a new Point; this is because send-to
; evals the symbol 'Point which will now be bound to the new definition.

; Exercise 4

(def apply-message-to
  (fn [clazz instance msg args]
    (let [method (msg (:__instance_methods__ clazz))]
      (if (not (nil? method))
        (apply method instance args)
        (msg instance))
      )
    )
  )

(println (send-to a-point :x))
(println (send-to a-point :y))
(println (send-to a-point :origin))

; Exercise 5

(println (send-to a-point :some-unknown-message))

; This is pretty undesirable; we should be checking for the existence
; of the instance variable as well and raise an error if none exists.
