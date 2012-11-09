(ns fp_oo.chapter6)

; Exercise 1

(def factorial
  (fn [n]
    (if (or (= 0 n) (= 1 n))
      1
      (* n (factorial (dec n))))
    )
  )

(println (factorial 4))

; Exercise 2

(def factorial-iter
  (fn [n iv]
    (if (or (= 0 n) (= 1 n))
      iv
      (recur (dec n) (* n iv)))
    )
  )

(def factorial'
  (fn [n]
    (factorial-iter n 1))
  )

(println (factorial' 4))

; Exercise 3

(def sum
  (fn [l iv]
    (if (empty? l)
      iv
      (recur (rest l) (+ iv (first l)))))
  )

(println (sum [1 2 3 4] 0))

; Exercise 4

(def product
  (fn [l iv]
    (if (empty? l)
      iv
      (recur (rest l) (* iv (first l)))))
  )

(println (product [1 2 3 4] 1))

(def accumulate
  (fn [f l iv]
    (if (empty? l)
      iv
      (recur f (rest l) (f iv (first l)))))
  )

(def sum'
  (fn [l iv]
    (accumulate + l iv))
  )

(def product'
  (fn [l iv]
    (accumulate * l iv))
  )

(println (sum' [1 2 3 4] 0))
(println (product' [1 2 3 4] 1))

; Exercise 5

(println (accumulate (fn [iv e] (assoc iv e 0)) [:a :b :c] {}))
