(require '[clojure.string :as str])

(defn parse-pair [str]
  (let [[command units] (str/split str #" ")]
    [command (Integer/parseInt units)]))

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn dive [input]
  (let [positions (reduce (fn [acc curr]
                            (let [[command units] curr
                                  depth (get acc :depth)
                                  horizontal  (get acc :horizontal)]
                              (cond
                                (= "forward" command) (assoc acc :horizontal (+ units horizontal))
                                (= "down" command) (assoc acc :depth (+ units depth))
                                :else (assoc acc :depth (- depth units)))))
                          {:horizontal 0 :depth 0}
                          input)]
    (* (get positions :horizontal)
       (get positions :depth))))

(let [input (read-file "resources/input_advent_02.txt")
      measurements (map #(parse-pair %) input)
      result (dive measurements)] result)
