(ns fp_oo.chapter10)

; Exercise 10.3.1
(println
  (let [a (concat '(a b c) '(d e f))
        b (count a)]
    (odd? b))
  )

(println
  (-> (concat '(a b c) '(d e f))
    ((fn [l]
      (-> (count l)
        ((fn [n]
          (odd? n)))))))
  )

; Exercise 10.3.2
(println
  (odd? (count (concat '(a b c) '(d e f))))
  )

(println
  (-> '(a b c)
    ((fn [l]
       (-> (concat l '(d e f))
         ((fn [l]
           (-> (count l)
             ((fn [n]
               (odd? n))))))))))
  )

; Exercise 10.3.3
(println
  (-> 3
    (+ 2)
    inc)
  )

(println
  (-> 3
    ((fn [n]
       (-> (+ n 2)
         ((fn [n]
            (inc n)))))))
  )


;       *       *      *
(defn nil-patch [v f]
  (if (nil? v) nil (f v))
  )

(println
  (-> 3
    (nil-patch (fn [n] (+ n 2)))
    (nil-patch inc)
    )
  )

(println
  (-> 3
    (nil-patch inc)
    (nil-patch inc)
    (nil-patch (fn [n] nil))
    (nil-patch inc)
    (nil-patch inc)
    (nil-patch inc)
    )
  )


; Exercise 10.9

(use 'clojure.algo.monads)

(def oops!
  (fn [reason & args]
    (with-meta (merge {:reason reason}
      (apply hash-map args))
      {:type :error}))
  )

(def oopsie?
  (fn [value]
    (= (type value) :error)))

(def error-monad
  (monad [m-bind (fn [mv f]
                   (if (oopsie? mv)
                     nil
                    (f mv)))
          m-result identity]))

(def factorial
  (fn [n]
    (cond
      (< n 0) (oops! "Factorial can never be less than zero." :number n)
      (< n 2) 1
      :else (* n (factorial (dec n)))))
  )

(def result
  (with-monad error-monad
    (domonad [big-number (factorial -1)
              even-bigger (* 2 big-number)]
      (repeat :a even-bigger))))

(println error-monad)
(println (factorial -1))
(println (factorial 1))
(println (factorial 6))


(def +?
  (with-monad maybe-m
    (m-lift 2 +)))

(println (+? 3 nil))
(println (+? 3 4))

; Exercise 10.12.1

(def multiples
  (fn [m n]
    (range (* m 2) (inc n) m))
  )

; Exercise 10.12.2

(def non-primes
  (fn [n]
    (set
      (with-monad sequence-m
        (domonad [a (range 2 (int (Math/sqrt n)))
                  b (multiples a n)]
          b))))
  )

; Exercise 10.12.3

(use 'clojure.set)

(def primes
  (fn [n]
    (difference (set (range 2 n)) (non-primes n)))
  )

(println (non-primes 100))
(println (primes 100))
