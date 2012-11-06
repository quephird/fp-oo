(ns fp_oo.chapter3)

(def Point
  (fn [x y]
    {:x x
     :y y
     :__class_symbol__ 'Point})
  )

; Accessors
(def x
  (fn [this] (:x this)))
(def y
  (fn [this] (:y this)))

; "Mutators"
(def shift
  (fn [this xinc yinc]
    (Point (+ (x this) xinc)
           (+ (y this) yinc)))
  )

; Exercise 1
(def add
   (fn [p1 p2]
     (Point (+ (x p1) (x p2))
            (+ (y p1) (y p2))))
  )

(def add'
   (fn [p1 p2]
     (shift p1 (x p2) (y p2)))
  )

(def a-point (Point 1 2))
(println a-point)
(println (shift a-point 3 4))
(def another-point (Point 5 6))
(println (add a-point another-point))
(println (add' a-point another-point))

; Exercise 2

(def make
  (fn [ctor & args]
    (apply ctor args))
  )

(def yet-another-point (make Point 7 8))
(println yet-another-point)

(def Triangle
  (fn [p1 p2 p3]
    {:p1 p1
     :p2 p2
     :p3 p3
     :__class_symbol__ 'Triangle})
  )

(def a-triangle (make Triangle a-point another-point yet-another-point))
(println a-triangle)

; Exercise 3

(def right-triangle
  (Triangle
    (Point 0 0)
    (Point 0 1)
    (Point 1 0)))

(def equal-right-triangle
  (Triangle
    (Point 0 0)
    (Point 0 1)
    (Point 1 0)))

(def different-triangle
  (Triangle
    (Point 0 0)
    (Point 0 10)
    (Point 10 0)))

(def equal-triangles?
  (fn [t1 t2]
    (= t1 t2))
  )

(println (equal-triangles? right-triangle right-triangle))
(println (equal-triangles? right-triangle equal-right-triangle))
(println (equal-triangles? right-triangle different-triangle))

; Exercise 4

(def equal-triangles?
  (fn [& ts]
    (apply = ts))
  )

(println (equal-triangles? right-triangle right-triangle))
(println (equal-triangles? right-triangle equal-right-triangle))
(println (equal-triangles? right-triangle different-triangle))

(println (equal-triangles? right-triangle equal-right-triangle different-triangle))

; Exercise 5
(def valid-triangle?
  (fn [t]
    (= 3 (count (set [(:p1 t) (:p2 t) (:p3 t)]))))
  )

(println (valid-triangle? right-triangle))
(def invalid-triangle
  (Triangle
    (Point 0 0)
    (Point 0 0)
    (Point 10 0)))
(println (valid-triangle? invalid-triangle))

