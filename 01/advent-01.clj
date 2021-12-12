(require '[clojure.string :as str])

(defn parse-int [str]
  (Integer/parseInt str))

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn part-1 [input]
  (let [[x & xs] input]
    (-> (reduce (fn [acc curr]
                  (let [prev (:prev acc)
                        count (:count acc)
                        acc (assoc acc :prev curr)]
                    (if (< prev curr)
                      (assoc acc :count (inc count))
                      acc)))
                {:prev x :count 0}
                xs)
        (:count))))

(let [input2 (read-file "resources/input_advent_01.txt")]
  (->
   (map #(parse-int %) input2)
   (part-1)))

"================== part 2"

(defn sum-windows [windows]
  (map #(reduce + %) windows))

(defn part-2 [input]
  (let [windows (partition 3 1 input)]
    (-> windows
        (sum-windows)
        (part-1))))

(let [input (->>
             (read-file "resources/input_advent_01.txt")
             (map #(parse-int %)))]
  (part-2 input))


