(ns clojure-course-task02.core
  (:gen-class)
  (:import java.io.File))

(defn isMtch [pat name]
  (let[pattern (re-pattern pat)]
  (re-matches pattern name))
  )

(defn get-files-list [fil dir predicate]
  (let[files (.listFiles dir)
       dirs (filter #(.isDirectory %) files)]
  (def  fl(future (map #(.getName %) (filter #(and (.isFile %) (predicate fil (.getName %))) files))))
  (flatten (concat @fl (pmap #(get-files-list fil % predicate) dirs)))))


(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (get-files-list file-name (File. path) isMtch))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (dorun(map println (find-files "^core_.+" "./")))
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
