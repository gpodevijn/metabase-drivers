(ns metabase.driver.redshiftspectrum
  "Amazon Redshift Driver (Spectrum enabled 2)."
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.string :as str]
            [honeysql.core :as hsql]
            [metabase.driver :as driver]
            [clojure.set :as set]
            [clojure.tools.logging :as log]
            [metabase.driver.common :as driver.common]
            [metabase.driver.sql-jdbc
             [sync :as sql-jdbc.sync]
             [connection :as sql-jdbc.conn]
             [execute :as sql-jdbc.execute]]
            [metabase
             [util :as u]]
            [metabase.driver.sql-jdbc.execute.legacy-impl :as legacy]
            [metabase.driver.sql.query-processor :as sql.qp]
            [metabase.util
             [honeysql-extensions :as hx]
             [i18n :refer [trs]]])
  (:import [java.sql ResultSet Types]
           java.time.OffsetTime))

(driver/register! :redshiftspectrum, :parent :redshift)

;;; +----------------------------------------------------------------------------------------------------------------+
;;; |                                             metabase.driver impls                                              |
;;; +----------------------------------------------------------------------------------------------------------------+

(defn- describe-external-databases [database]
  "Fetch the external tables used by Redshift Spectrum"
  (set (jdbc/query (sql-jdbc.conn/db->pooled-connection-spec database)
                   ["SELECT schemaname as \"schema\",
                                     tablename as \"name\"
                             FROM svv_external_tables"
                    ])))
                    
(defmethod driver/describe-database :redshiftspectrum
  [driver database]
  (update (sql-jdbc.sync/describe-database driver database) :tables (u/rpartial set/union (describe-external-databases database))))
