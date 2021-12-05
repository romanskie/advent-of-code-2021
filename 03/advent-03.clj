(require '[clojure.string :as str])

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn binary->int [binary-str]
  (Integer/parseInt binary-str 2))

(defn count-per-rate-item [rate-item]
  [(\0 rate-item) (\1 rate-item)])

(defn gamma-rate [rate]
  (-> (reduce #(let [[zeros ones] (count-per-rate-item %2)]
                 (if (> zeros ones) (str %1 \0) (str %1 \1)))
              ""
              rate)
      (binary->int)))

(defn epsilon-rate [rate]
  (-> (reduce #(let [[zeros ones] (count-per-rate-item %2)]
                 (if (< zeros ones) (str %1 \0) (str %1 \1)))
              ""
              rate)
      (binary->int)))

(defn calculate-rate [input n]
  (let [state (into [] (repeat n ""))]
    (->> input
         (reduce #(mapv str %1 %2) state)
         (into [] (map frequencies)))))

(defn part-1 [input]
  (let [n (count (first input))
        rate (calculate-rate input n)
        gamma (gamma-rate rate)
        epsilon (epsilon-rate rate)]
    (* epsilon gamma)))

(part-1 (read-file "resources/input_advent_03.txt"))

"================== part 2"

(defn filter-nth-ones [idx input]
  (filter #(= \1 (get % idx)) input))

(defn filter-nth-zeros [idx input]
  (filter #(= \0 (get % idx)) input))

(defn oxy-rate [input n]
  (->
   (loop [input input idx 0]
     (if (= (count input) 1)
       (apply str input)
       (let [rate (calculate-rate input n)
             rate-item (get rate idx)
             [zeros ones] (count-per-rate-item rate-item)]
         (if (> zeros ones)
           (recur (filter-nth-zeros idx input) (inc idx))
           (recur (filter-nth-ones idx input) (inc idx))))))
   (binary->int)))

(defn co2-rate [input n]
  (->
   (loop [input input idx 0]
     (if (= (count input) 1)
       (apply str input)
       (let [rate (calculate-rate input n)
             rate-item (get rate idx)
             [zeros ones] (count-per-rate-item rate-item)]
         (if (< ones zeros)
           (recur (filter-nth-ones idx input) (inc idx))
           (recur (filter-nth-zeros idx input) (inc idx))))))
   (binary->int)))

(defn part-2 [input]
  (let [n (count (first input))
        oxy (oxy-rate input n)
        co2 (co2-rate input n)]
    (* oxy co2)))

(part-2 (read-file "resources/input_advent_03.txt"))

