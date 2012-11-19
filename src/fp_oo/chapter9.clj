(ns fp_oo.chapter9)

; Exercise 9.4.1

(println (map (partial + 2) [1 2 3]))

; Exercise 9.4.2

(def separate-lame
  (fn [pred sequence]
    [(filter pred sequence) (remove pred sequence)]))

(def separate-uber
  (fn [pred sequence]
    ((juxt (partial filter pred) (partial remove pred)) sequence)))

(println (separate-uber odd? (range 10)))


; Exercise 9.4.3

(def myfun
  (let [x 3]
    (fn [] x)))

(def myfun'
  ((fn [x] (fn [] x)) 3)
    )

(println (myfun'))

; Exercise 9.4.5

(def my-atom (atom 0))
(swap! my-atom (fn [n] 33))
(println (deref my-atom))

; Exercise 9.4.6

(def always
  (fn [val]
    (fn [& args] val)))

(println ((always 8) :true))
(println ((always 8) 42))
(println ((always 8) 1 "two" 3))

; Exercise 9.4.7

(def isbn-checksum
  (fn [s]
    (reduce + (map * s (range 1 (inc (count s)))))))

(println (isbn-checksum [4 8 9 3 2]))

; Exercise 9.4.8

(def string-2-digits
  (fn [s]
    (map #(Character/getNumericValue %) s)))

(def isbn?
  (fn [s]
    (-> s
        reverse
        string-2-digits
        isbn-checksum
        (rem 11)
        zero?
      )))

(println (isbn? "0131774115"))
(println (isbn? "0977716614"))
(println (isbn? "1934356190"))

; Exercise 9.4.9

(def upc-checksum
  (fn [s]
    (reduce + (map * s (map #(if (zero? (rem % 2)) 1 3) (range (count s)))))))

(def upc?
  (fn [s]
    (-> s
        reverse
        string-2-digits
        upc-checksum
        (rem 10)
        zero?
      )))

(println (upc? "074182265830"))
(println (upc? "731124100023"))
(println (upc? "722252601404"))

; Exercise 9.4.10

(def number-checker
  (fn [checksum-fn divisor]
    (fn [s]
      (-> s
          reverse
          string-2-digits
          checksum-fn
          (rem divisor)
          zero?
        ))))

(def isbn?' (number-checker isbn-checksum 11))
(def upc?' (number-checker upc-checksum 10))

(println (isbn?' "0131774115"))
(println (isbn?' "0977716614"))
(println (isbn?' "1934356190"))

(println (upc?' "074182265830"))
(println (upc?' "731124100023"))
(println (upc?' "722252601404"))
