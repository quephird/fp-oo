(ns fp_oo.chapter7)

; Exercise 7.5.1
(println (-> [1]
             first
             inc
             list))

; Exercise 7.5.2
(println (-> [1]
             first
             inc
             (* 3)
             list))

; Exercise 7.5.3
(println (-> 3
             ((fn [n] (* n 2)))))

; Exercise 7.5.4
(println (+ (* (+ 1 2) 3) 4))
(println (-> 1
            (+ 2)
            (* 3)
            (+ 4)))

; Exercise 7.9.1

(def answer-annotations
     (fn [courses registrant]
       (let [checking-set (set (:courses registrant))]
         (map (fn [course]
                (assoc course
                       :spaces-left (- (:limit course)
                                       (:registered course))
                       :already-in? (contains? checking-set
                                               (:course-name course))))
              courses))))

(def domain-annotations
     (fn [courses]
       (map (fn [course]
              (assoc course
                :empty? (zero? (:registered course))
                :full? (zero? (:spaces-left course))))
            courses)))

(def note-unavailability
     (fn [courses instructor-count registrant]
       (let [out-of-instructors?
             (= instructor-count
                (count (filter (fn [course] (not (:empty? course)))
                               courses)))]
         (map (fn [course]
                (assoc course
                       :unavailable? (or (and (:manager? registrant)
                                              (not (:morning? course)))
                                         (:full? course)
                                         (and out-of-instructors?
                                              (:empty? course)))))
              courses))))

(def annotate
     (fn [courses registrant instructor-count]
       (-> courses
           (answer-annotations registrant)
           domain-annotations
           (note-unavailability instructor-count registrant))))

(def separate
     (fn [pred sequence]
       [(filter pred sequence) (remove pred sequence)]))

(def visible-courses
     (fn [courses]
       (let [[guaranteed possibles] (separate :already-in? courses)]
         (concat guaranteed (remove :unavailable? possibles)))))

(def final-shape
     (fn [courses]
       (let [desired-keys [:course-name :morning? :registered :spaces-left :already-in?]]
         (map (fn [course]
                (select-keys course desired-keys))
              courses))))

(def half-day-solution
     (fn [courses registrant instructor-count]
       (-> courses
           (annotate registrant instructor-count)
           visible-courses
           ((fn [courses] (sort-by :course-name courses)))
           final-shape)))

(def solution
     (fn [courses registrant instructor-count]
       (map (fn [courses]
              (half-day-solution courses registrant instructor-count))
            (separate :morning? courses))))

(def phb
  {:manager? true
   :taking-now ["zigging" "zagging"]})

(def available-courses
  [{:course-name "zigging" :limit 4 :registered 3 :morning? true}
   {:course-name "zagging" :limit 1 :registered 1 :morning? true}
   {:course-name "zogging" :limit 2 :registered 2 :morning? false}])

(println (solution available-courses phb 2))

