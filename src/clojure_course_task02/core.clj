(ns clojure-course-task02.core
  (:gen-class)
  (:import java.io.File))

(defn isMtch [pattern name]
  (if(re-matches pattern name)
      true
      false))

(defn get-files-list [dir]
  (let[files (.listFiles dir)
       dirs (filter #(.isDirectory %) files)]
  (def  fl(future (map #(.getPath %) (filter #(and (.isFile %) (isMtch (re-pattern "^core.+") (.getName %))) files))))
  (flatten (concat @fl (pmap #(get-files-list %) dirs)))))


(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (get-files-list (File. path)))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (dorun(map println (find-files "^core.+" "./")))
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
