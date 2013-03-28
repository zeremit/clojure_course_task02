(ns clojure-course-task02.core
  (:gen-class)
  (:import java.io.File))


(defn get-files-list [dir]
  (let[files (.listFiles dir)
       dirs (filter #(.isDirectory %) files)]
  (map #(.getName %) (filter #(.isFile %) files))))


(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (get-files-list (java.io.File. path)))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (dorun(find-files "^core.+" "\\"))
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
