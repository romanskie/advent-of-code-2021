(require '[clojure.string :as str])

(defn parse-pair [str]
  (let [[command units] (str/split str #" ")]
    [command (Integer/parseInt units)]))

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn part-1 [input]
  (let [positions (reduce (fn [acc curr]
                            (let [[command units] curr
                                  depth (:depth acc)
                                  horizontal  (:horizontal acc)]
                              (cond
                                (= "forward" command) (assoc acc :horizontal (+ units horizontal))
                                (= "down" command) (assoc acc :depth (+ units depth))
                                :else (assoc acc :depth (- depth units)))))
                          {:horizontal 0 :depth 0}
                          input)]
    (* (:horizontal positions)
       (:depth positions))))

(let [input (read-file "resources/input_advent_02.txt")]
  (->
   (map #(parse-pair %) input)
   (part-1)))

"================== part 2"

(defn part-2 [input]
  (let [positions (reduce (fn [acc curr]
                            (let [[command units] curr
                                  depth (:depth acc)
                                  horizontal  (:horizontal acc)
                                  aim  (:aim acc)]
                              (cond
                                (= "forward" command) (-> acc
                                                          (assoc :horizontal (+ units horizontal))
                                                          (assoc :depth (+ depth (* aim units))))
                                (= "down" command) (assoc acc :aim (+ aim units))
                                :else (assoc acc :aim (- aim units)))))
                          {:horizontal 0 :depth 0 :aim 0}
                          input)]
    (* (:horizontal positions)
       (:depth positions))))

(let [input (read-file "resources/input_advent_02.txt")]
  (->
   (map #(parse-pair %) input)
   (part-2)))
