(ns color-ral.core-test
  (:import (org.openqa.selenium By
                                Capabilities
                                Dimension
                                Keys
                                NoSuchElementException
                                OutputType
                                Point
                                TakesScreenshot
                                WebDriver
                                WebElement
                                NoSuchElementException)
           (org.openqa.selenium.support.ui WebDriverWait
                                           ExpectedConditions)
           org.openqa.selenium.edge.EdgeDriver
           org.openqa.selenium.safari.SafariDriver
           org.openqa.selenium.chrome.ChromeDriver
           org.openqa.selenium.ie.InternetExplorerDriver
           (org.openqa.selenium.firefox FirefoxDriver
                                        FirefoxProfile)
           (org.openqa.selenium.interactions Actions
                                             CompositeAction))
  (:require [clojure.test :refer :all]
            [color-ral.core :refer :all])
  (:gen-class))
(deftest safari-driver
  ;;(org.apache.log4j.BasicConfigurator/configure)
  (with-open [safari (SafariDriver.)]
    (.get safari "http://www.ralcolor.com")
    (testing "detect-patterns"
      (let [ral-1000 "/html/body/div/div/table/tbody/tr[14]/td[2]/span"
            ral-1001 "/html/body/div/div/table/tbody/tr[16]/td[3]/span"
            ral-1002 "/html/body/div/div/table/tbody/tr[18]/td[3]/p/span"
            ral-1003 "/html/body/div/div/table/tbody/tr[20]/td[3]/p/span"
            ral-1004 "/html/body/div/div/table/tbody/tr[22]/td[3]/p/span"
            ral-1005 "/html/body/div/div/table/tbody/tr[24]/td[3]/p/span"
            ral-1018 "/html/body/div/div/table/tbody/tr[44]/td[3]/p/span"
            ral-1037 "/html/body/div/div/table/tbody/tr[72]/td[3]/p/span"
            ral-2000 "/html/body/div/div/table/tbody/tr[74]/td[4]/p/span"
            ral-3000 "/html/body/div/div/table/tbody/tr[100]/td[4]/p/span"
            ral-6007 "/html/body/div/div/table/tbody/tr[238]/td[3]/p/span"
            ral-8028 "/html/body/div/div/table/tbody/tr[408]/td[3]/p/span"
            ral-8029 "/html/body/div/div/table/tbody/tr[410]/td[3]/p/span"
            ral-9001-0 "/html/body/div/div/table/tbody/tr[412]/td[1]"
            ral-9001-1 "/html/body/div/div/table/tbody/tr[412]/td[2]/p/span"
            ral-9001-3 "/html/body/div/div/table/tbody/tr[412]/td[4]/p/span"
            ral-9001-2 "/html/body/div/div/table/tbody/tr[412]/td[3]/p/span"
            ral-9023 "/html/body/div/div/table/tbody/tr[438]/td[1]/p/span"]
        (-> safari
            (WebDriverWait. (java.time.Duration/ofSeconds 5))
            (.until (ExpectedConditions/presenceOfElementLocated (By/xpath ral-9001-0))))
        (println "-------------")
        (println (.getText (.findElement safari (By/xpath ral-9001-0))))
        (println (.getText (.findElement safari (By/xpath ral-9001-1))))
        (println (.getText (.findElement safari (By/xpath ral-9001-2))))
        (println (.getText (.findElement safari (By/xpath ral-9001-3))))
        (println (.getText (.findElement safari (By/xpath ral-1001))))
        (println (.getText (.findElement safari (By/xpath ral-1002))))
        (println "-------------")
        )
      "td[1] needs empty-detection to choose td[3] or td[4], td[2] needs trim")
    (let [prefix "/html/body/div/div/table/tbody/tr["
          mid "]/td["
          suffix "]"
          f #(.getText (.findElement safari (By/xpath %)))
          path #(str prefix %1 mid %2 suffix)
          r #(clojure.string/replace % #"[^\d\w]+|[_]+" "")
          padding-paths (map #(path % 1) (range 14 440 2))
          padding-values (map (comp r f) padding-paths)
          values (map #(if (empty? %) 1 0) padding-values)
          xpaths (map #(path %1 (+ 3 %2)) (range 14 440 2) values)]
      (println (map (comp r f) xpaths)))
    (println (let [path #(str "/html/body/div/div/table/tbody/tr[" %1 "]/td[" %2 "]")
          get-text #(.getText (.findElement safari (By/xpath %)))
          trim #(clojure.string/replace % #"[^\d\w]+|[_]+" "")]
          (for [tab [1 3]]
            (map get-text (map #(path %1 (+ tab %2))
                                  (range 14 440 2)
                                  (map (comp #(if (empty? %) 1 0) trim get-text #(path % 1)) (range 14 440 2)))))))))
