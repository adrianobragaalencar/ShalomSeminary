--
-- Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
-- YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--
CREATE TABLE street (
    "cd_street"   INTEGER PRIMARY KEY NOT NULL,
    "cd_neighbor" INTEGER NOT NULL,
    "street_name" TEXT    NOT NULL,
    "zipcode"     TEXT    NOT NULL
)
