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
(deftest safari
  (org.apache.log4j.BasicConfigurator/configure)
  (with-open [safari (SafariDriver.)]
    (.get safari "https://space.bilibili.com/1988296011")
    (Thread/sleep 10000)))
