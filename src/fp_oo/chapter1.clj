(ns fp-oo.chapter1)

; Exercise 1
(def second (fn [l] (first (rest l))))

(println (second [1 2 3 4]))

; Exercise 2
(def third
  (fn [l] (first (rest (rest l))))
  )

(def third'
  (fn [l] (nth l 2))
  )

(println (third [1 2 3 4]))
(println (third' [1 2 3 4]))

; Exercise 3
(def add-squares
  (fn [l]
    (reduce + (map #(* % %) l)))
  )

(println (add-squares [1 2 5]))

; Exercise 4
(def factorial-bizarre
  (fn [n] (apply * (range 1 (inc n))))
  )

(factorial-bizarre 5)

; Exercise 6
(def prefix-of?
  (fn [c s]
    (if (empty? c)
      true
      (if (not= (first c) (first s))
        false
        (prefix-of? (rest c) (rest s))
        )
      )
    )
  )

(println (prefix-of? [] [1 2 3 4]))
(println (prefix-of? [2 3] [1 2 3 4]))
(println (prefix-of? [1 2] [1 2 3 4]))
(println (prefix-of? [1 2 5] [1 2 3 4]))

; Exercise 7
(def tails
  (fn [s]
    (map #(% s)
      (map #(fn [l] (drop % l)) (range (inc (count s))))
      )
    )
  )

(println (tails [1 2 3 4]))

; Exercise 8
(def puzzle
  (fn [list] (list list))
  )

; The following results in a ClassCastException because the 'list' variable
; is being used as both a function and an argument to that function inside 'puzzle',
; and in the invocation below the type of 'list' being passed in is a PersistentList,
; which cannot be cast to a function.
(println (puzzle '(1 2 3)))


